package org.lmw.tools.kdserch;

import org.lmw.tools.kdserch.adapter.ComsAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Coms extends BaseActivity implements OnItemClickListener{
private ListView lv;
private ComsAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE); 	
	 initView();
}
public void initView(){
		setContentView(R.layout.act_coms);
	   lv=(ListView)findViewById(R.id.coms_listView);
	   adapter=new ComsAdapter(this,companyLogos,companyCodes,companyNames);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
}

@Override
public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	Intent it=new Intent();
	it.putExtra("comName", companyNames[arg2]);
	it.putExtra("comId", companyCodes[arg2]);
	setResult(RESULT_OK, it);
	onBackPressed();
}
}
