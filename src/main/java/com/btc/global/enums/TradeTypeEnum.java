package com.btc.global.enums;

public enum TradeTypeEnum {

	IN(1,"支入"),
	OUT(2,"支出");

	
	private TradeTypeEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	private int type;
	
	private String desc;

	public int getType() {
		return type;
	}
	public String getStringType() {
		return type+"";
	}

	public String getDesc() {
		return desc;
	}
	


	
}
