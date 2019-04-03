package com.btc.lbank.bean;

public class LbankTrade {

	private String biz_number;
	private String balance_amt;
	private String open_id;
	private String asset_code;
	private String timestemp;
	private int status;
	public String getBiz_number() {
		return biz_number;
	}
	public void setBiz_number(String biz_number) {
		this.biz_number = biz_number;
	}
	public String getBalance_amt() {
		return balance_amt;
	}
	public void setBalance_amt(String balance_amt) {
		this.balance_amt = balance_amt;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getAsset_code() {
		return asset_code;
	}
	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}
	public String getTimestemp() {
		return timestemp;
	}
	public void setTimestemp(String timestemp) {
		this.timestemp = timestemp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
