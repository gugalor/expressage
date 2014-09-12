package org.lmw.tools.kdserch.adapter;
import java.util.List;

import org.lmw.tools.kdserch.R;
import org.lmw.tools.kdserch.entity.Tab_History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//查询历史列表适配器
public class HistoryListAdapter extends BaseAdapter {
       private Context context;
       private List<Tab_History> dataList;  
	   public HistoryListAdapter(Context context,List<Tab_History> list)
       {
    	   this.context = context;
    	   dataList = list;
       }

        public View getView(int index, View arg1, ViewGroup arg2) {
 		        View itemView = LayoutInflater.from(context).inflate(R.layout.item_history, null);
 		        TextView company_name = (TextView) itemView.findViewById(R.id.company_name);
 		        TextView number = (TextView) itemView.findViewById(R.id.item_number);
 		        TextView company_code = (TextView) itemView.findViewById(R.id.company_code);
 		        number.setText(dataList.get(index).getExpressId());
 		        company_code.setText(dataList.get(index).getComId());
 		        company_name.setText(dataList.get(index).getComName());
                return itemView;
        }

		@Override
		public int getCount() {
			if(dataList == null)
			{
				return 0;
			}
			return dataList.size();
		}
		 public Object getItem(int arg0) {
             return dataList.get(arg0);
     }

     public long getItemId(int arg0) {
             return 0;
     }

	public void setDataList(List<Tab_History> dataList) {
		this.dataList = dataList;
	}
     
	 

}