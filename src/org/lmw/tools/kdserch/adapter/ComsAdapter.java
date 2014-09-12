package org.lmw.tools.kdserch.adapter;
import org.lmw.tools.kdserch.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//快递公司列表适配器
public class ComsAdapter extends BaseAdapter {
       private Context context;
       String[] companyLogos ;
       String[] companyCodes ;
	   String[] companyNames ;
	   public ComsAdapter(Context context, String[] companyLogos, String[] companyCodes ,String[] companyNames)
       {
    	   this.context = context;
    	   this.companyCodes =companyCodes;
    	   this. companyNames =companyNames;
    	   this.companyLogos =companyLogos;
       }
	@Override
	public int getCount() {
		if (companyCodes == null) {
			return 0;
		}
		return companyCodes.length;
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int index, View arg1, ViewGroup arg2) {
		View itemView = LayoutInflater.from(context).inflate(R.layout.item_coms, null);
		TextView company_name = (TextView) itemView.findViewById(R.id.company_item_name);
		TextView company_code = (TextView) itemView.findViewById(R.id.company_item_code);
		ImageView company_logon = (ImageView) itemView.findViewById(R.id.company_logo);
		company_code.setText(companyCodes[index]);
		company_name.setText(companyNames[index]);
		company_logon.setImageBitmap(getBmp(companyLogos[index]));
		return itemView;
	}
	
	public Bitmap getBmp(String imgname){
		 int imgid =context.getResources().getIdentifier(imgname, "drawable","org.lmw.tools.kdserch");
		 return BitmapFactory.decodeResource(context.getResources(), imgid);
	}

}