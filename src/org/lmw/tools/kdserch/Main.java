package org.lmw.tools.kdserch;
import org.lmw.tools.kdserch.conn.ConnHelper;
import org.lmw.tools.kdserch.entity.Tab_History;
import org.lmw.tools.kdserch.tools.AppManager;
import org.lmw.tools.kdserch.tools.JsonParser;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

@SuppressLint("HandlerLeak")
public class Main extends BaseActivity implements OnClickListener{
	private final static String APPID="533656e7";
	private boolean isBack = false;		// 是否能够退出
	private long downTime;					// 上次按退出的时间
	private Button currCom;
	private Button serach;
	private ImageButton speech;
	private MenuDrawer mMenuDrawer;
	private int coms_req=99;
	private String comName="";
	private String comId="";
	private EditText expressId;
	private Button toHistory;
	private Handler hd;
	private RecognizerDialog iatDialog;			//识别窗口
	private SpeechRecognizer iatRecognizer;	//识别对象
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMenu();
		initView();
		initSpeech();
	}
	public void initView(){
		comName=companyNames[0];
		comId=companyCodes[0];
		
		expressId=(EditText)findViewById(R.id.expressId);
		currCom=(Button)findViewById(R.id.currCom);
		serach=(Button)findViewById(R.id.serach_btn);
		speech=(ImageButton)findViewById(R.id.speech);
		toHistory=(Button)findViewById(R.id.menu01);
		currCom.setText(comName);
		
		currCom.setOnClickListener(this);
		serach.setOnClickListener(this);
		speech.setOnClickListener(this);
		toHistory.setOnClickListener(this);
		hd=getHandler();
	}
	
	private void initMenu(){
		mMenuDrawer = MenuDrawer.attach(this, Position.BOTTOM);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		mMenuDrawer.setContentView(R.layout.act_main);
		mMenuDrawer.setMenuView(R.layout.main_menu);
		mMenuDrawer.setMenuSize(130);
	}
	
	//初始化消息处理器
	private Handler getHandler(){
		return new Handler(){
		@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==0){
					try {
					//存入历史记录
					if(db.findAll(Selector.from(Tab_History.class).where("expressId","=",expressId.getText().toString()))==null){
						Tab_History history = new Tab_History();
						history.setComName(comName);
						history.setComId(comId);
						history.setExpressId(expressId.getText().toString());
						db.save(history);
					}
					} catch (DbException e) {
						e.printStackTrace();
					}
					Intent it=new Intent(Main.this, Result.class);
					it.putExtra("jsonStr", msg.obj.toString());
					startActivity(it);
					overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
				}
				if(msg.what==-1){
					showToast("error");
				}
				dismissLoadingDialog();
			}	
		};
	}
	
	@Override
	public void onClick(View v) {
		if(v==currCom){
			startActivityForResult(new Intent(Main.this, Coms.class),coms_req);
		}
		if(v==serach){
			showLoadingDialog("");
			ConnHelper.Serach(comId,expressId.getText().toString(), hd);
		}
		if(v==speech){
			showIatDialog();
		}
		if(v==toHistory){
			mMenuDrawer.closeMenu();
			startActivity(new Intent(Main.this, History.class));
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_MENU){
			if(mMenuDrawer.isMenuVisible()){
			mMenuDrawer.closeMenu();	
			}else{
			mMenuDrawer.openMenu();
			}
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 如果当前menu没有显示
			if (!isBack) {
				showToast("再按一次退出");
				downTime = event.getDownTime();
				isBack = true;
				return true;
			} else {
				if (event.getDownTime() - downTime <= 2000) {
					AppManager.getAppManager().AppExit(Main.this);
				} else {
					showToast("再按一次退出");
					downTime = event.getDownTime();
					return true;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	//初始化讯飞语音控件
	public void initSpeech(){
		SpeechUser.getUser().login(this, null, null, "appid="+APPID,listener);
		//创建听写对象,如果只使用无UI听写功能,不需要创建RecognizerDialog
		iatRecognizer=SpeechRecognizer.createRecognizer(this);
		//初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
		iatDialog =new RecognizerDialog(this);
	}
	
	//语音识别对话框
	public void showIatDialog()
	{
		if(null == iatDialog) {
		//初始化听写Dialog	
		iatDialog =new RecognizerDialog(this);
		}
		String engine="iat_engine";
		//清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
		iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		//设置听写Dialog的引擎
		iatDialog.setParameter(SpeechConstant.DOMAIN, engine);
		iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		//清空结果显示框.
		expressId.setText(null);
		//显示听写对话框
		iatDialog.setListener(recognizerDialogListener);
		iatDialog.show();
		showToast("请开始说话...");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			if(requestCode==coms_req){
			//接收快递公司名称和编号
			currCom.setText(data.getExtras().getString("comName"));
			comId=data.getExtras().getString("comId");
			comName=data.getExtras().getString("comName");
			}
		}
	}
	
	//用户登录回调监听器.
	private SpeechListener listener = new SpeechListener()
	{
		@Override
		public void onData(byte[] arg0) {
		}
		@Override
		public void onCompleted(SpeechError error) {
			if(error != null) {
				showToast(error.getMessage());
			}			
		}
		@Override
		public void onEvent(int arg0, Bundle arg1) {
		}		
	};
	
	//识别回调监听器
	RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener()
	{
		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = JsonParser.parseIatResult(results.getResultString());
			expressId.append(text.replace("。", ""));
			expressId.setSelection(expressId.length());
		}
		public void onError(SpeechError error) {
			showToast(error.getMessage());
		}
	};
	
	
	@Override
	protected void onStop() {
		if (null != iatRecognizer) {
			iatRecognizer.cancel();
		}
		if (null != iatDialog) {
			iatDialog.cancel();
		}
		super.onStop();
	}
	
}
