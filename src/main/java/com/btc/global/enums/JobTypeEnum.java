package com.btc.global.enums;

public enum JobTypeEnum {

	COUNT_INCOME(1,"计算收益"),
	COUNT_USE_COINS(2,"计算计息资产");
	
	
	private JobTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private int code; 
	
	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
