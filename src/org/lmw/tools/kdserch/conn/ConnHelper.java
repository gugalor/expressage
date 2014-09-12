package org.lmw.tools.kdserch.conn;

import android.os.Handler;
import android.os.Message;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class ConnHelper {
	//public static final String API_KEY="051747fb685e7d67"; 快递100上申请到的API_KEY
	public static final String  EXPRESS_API_HOST="http://www.hydig.com/get.php";
	static HttpUtils hu=new HttpUtils();
	public static void Serach(final String comId,final String expressNo,final Handler hd){
		StringBuffer sb=new StringBuffer();
		//这是快递100API请求格式(不知道是什么问题 总是出现快递公司网络异常，)
		//sb.append(EXPRESS_API_HOST).append("?id=").append(API_KEY).append("&com=").append(comId).append("&nu=").append(expressNo).append("&order=asc");
		//这时网上另外一哥们的接口（http://www.hydig.com/）
		sb.append(EXPRESS_API_HOST).append("?id=1").append("&type=").append(comId).append("&postid=").append(expressNo);
		String url=sb.toString();
		hu.send(HttpMethod.GET, url, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> res) {
				Message msg=new Message();
				msg.what=0;
				msg.obj=res.result;
				hd.sendMessage(msg);
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				hd.sendEmptyMessage(-1);
			}
		});
	} 
}
