package org.lmw.tools.kdserch.entity;

import com.lidroid.xutils.db.annotation.Table;

@Table(name="tab_history")
public class Tab_History {
	private int id;
	private String comName;	//快递公司名称
	private String comId;			//快递公司编号
	private String expressId;	//运单号
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getComId() {
		return comId;
	}
	public void setComId(String comId) {
		this.comId = comId;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
}
