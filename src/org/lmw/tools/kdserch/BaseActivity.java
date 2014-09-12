package org.lmw.tools.kdserch;

import org.lmw.tools.kdserch.tools.AppManager;
import org.lmw.tools.kdserch.tools.LoadingDialog;
import com.lidroid.xutils.DbUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
  


/**
  * <h1>BaseActivity类:</h1>
  * <p><h5>TODO</h5>
  * @author qn-Administrator
  * <p>修改时间： 2014-2-12 上午11:59:04
  * @version V1.0
  */
public class BaseActivity extends Activity {
	public Toast mToast=null;
	public LoadingDialog dialog =null;
	public String[] companyLogos ;
	public String[] companyCodes ;
	public String[] companyNames ;
	public DbUtils db;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		 db= DbUtils.create(this);
		companyCodes = getResources().getStringArray(R.array.company_code);
		companyNames = getResources().getStringArray(R.array.company_name);
		companyLogos = getResources().getStringArray(R.array.company_logo);
	}

	public void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	public void showLoadingDialog(String dialogStr){
		dialog=new LoadingDialog(this,dialogStr);
		dialog.show();
	}
	

	public void dismissLoadingDialog(){
		if(dialog!=null&& dialog.isShowing()){
			dialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}
}
