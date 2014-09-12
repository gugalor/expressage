package org.lmw.tools.kdserch.entity;

import java.io.Serializable;

public class Historical implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyCode;
	private String number;
	private String time;
	private String companyName;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
