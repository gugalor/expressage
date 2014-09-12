package org.lmw.tools.kdserch.adapter;

import java.util.ArrayList;
import java.util.List;

import org.lmw.tools.kdserch.R;
import org.lmw.tools.kdserch.entity.ResultEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
//查询结果适配器
public class ResultFollowAdapter extends BaseAdapter {
       private Context context;
       private List<ResultEntity> followList;  
       boolean sort_up = true;
       int state;
	  public ResultFollowAdapter(Context context,List<ResultEntity> list,boolean f,int s)
       {
    	   this.context = context;
    	   followList = list;
    	   sort_up = f;
    	   state = s;
       }

        public View getView(int index, View arg1, ViewGroup arg2) {
 		        View itemView = LayoutInflater.from(context).inflate(R.layout.item_result, null);
 		       ResultEntity map = followList.get(index);
                ((TextView)itemView.findViewById(R.id.item_time)).setText(map.getTime());
                ((TextView)itemView.findViewById(R.id.item_follow_info)).setText(map.getContext());
                return itemView;
        }
		@Override
		public int getCount() {
			return followList.size();
		}
		 public Object getItem(int arg0) {
             return followList.get(arg0);
     }
     public long getItemId(int arg0) {
             return 0;
     }
	public void setFollowList(ArrayList<ResultEntity> followList) {
		this.followList = followList;
	}
	 public void setSort_up(boolean sort_up) {
			this.sort_up = sort_up;
	}

}