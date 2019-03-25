package com.btc.util.enums;

public enum IsDealEnum {
	YES("Y",1),
	NO("N",0);
	
	private String code;
	
	private int num;

	private IsDealEnum(String code, int num) {
		this.code = code;
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public int getNum() {
		return num;
	}
	
	
}
