package org.lmw.tools.kdserch;

import java.util.ArrayList;
import java.util.List;
import org.lmw.tools.kdserch.adapter.HistoryListAdapter;
import org.lmw.tools.kdserch.conn.ConnHelper;
import org.lmw.tools.kdserch.entity.Tab_History;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class History extends BaseActivity implements OnItemClickListener,OnItemLongClickListener{
	private TextView count;
	private ListView lv;
	private HistoryListAdapter adapter;
	private List<Tab_History> rs=new ArrayList<Tab_History>();
	private Handler hd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_history);
		initView();
	}

	public void initView(){
		try {
			rs=db.findAll(Selector.from(Tab_History.class));
			adapter=new HistoryListAdapter(this, rs);
			lv=(ListView)findViewById(R.id.historyListView);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(this);
			count=(TextView)findViewById(R.id.count);
			if(rs==null){
			count.setText("0");
			}else{
			count.setText(""+rs.size());
			}
			hd=getHandler();
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	private Handler getHandler(){
		return new Handler(){
		@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==0){
					Intent it=new Intent(History.this, Result.class);
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		showLoadingDialog("");
		ConnHelper.Serach(rs.get(arg2).getComId(),rs.get(arg2).getExpressId(), hd);
	}

	//长按删除
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		return false;
	}

} 
