package com.btc.global.enums;

public enum TaskTypeEnum {

	COUNT_INCOME(1,"计算收益"),
	COUNT_USE_CONINS(2,"计算计息资产");
	
	private int type;
	private String desc;
	
	private TaskTypeEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	public int getType() {
		return type;
	}
	public String getDesc() {
		return desc;
	}
	
	
	
	
}
