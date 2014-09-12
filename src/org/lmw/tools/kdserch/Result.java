package org.lmw.tools.kdserch;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.lmw.tools.kdserch.adapter.ResultFollowAdapter;
import org.lmw.tools.kdserch.entity.ResultEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Result extends BaseActivity {
private ListView lv;
private ResultFollowAdapter adapter;
private List<ResultEntity> rs=new ArrayList<ResultEntity>();
private boolean sort_up = true;
private   int state=0;

public static final int STATE_RECEIVED = 3;
public static final int STATE_ON_PASSAGE = 0;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.act_result);
	initView();
}
private void initView(){
	lv=(ListView)findViewById(R.id.followListView);
	lv.setAdapter(adapter);
	parse(getIntent().getExtras().getString("jsonStr"));
}

public void parse(String jsonStr){
	try {
		Gson gson=new Gson();
		Type type = new TypeToken<ArrayList<ResultEntity>>() {}.getType();
		JSONObject obj=new JSONObject(jsonStr);
		if(obj.get("message").equals("ok")){
		rs=gson.fromJson(obj.getString("data"), type);
		Collections.reverse(rs);
		state=Integer.parseInt(obj.get("state").toString());
		adapter=new ResultFollowAdapter(Result.this, rs, sort_up, state);
		lv.setAdapter(adapter);
		
		if(state ==STATE_RECEIVED)
		{
			((TextView)findViewById(R.id.follow_textview)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_success, 0);
			findViewById(R.id.colorLine).setBackgroundResource(R.drawable.red_line_green);
			findViewById(R.id.dot_full).setVisibility(View.VISIBLE);
		} 
		if(state == STATE_ON_PASSAGE)
		{
			findViewById(R.id.colorLine).setBackgroundResource(R.drawable.red_line_blue);
		} 
		
		}else{
			showToast(obj.getString("message"));
		}
	} catch (JSONException e) {
		e.printStackTrace();
	}
}

@Override
	public void onBackPressed() {
		overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
		super.onBackPressed();
	}
}
