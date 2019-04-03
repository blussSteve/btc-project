package com.btc.global.enums;

public enum TradeStatusEnum {

	SUCCESS(1,"成功"),
	FAIL(2,"失败"),
	init(3,"处理中");
	
	
	private TradeStatusEnum(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}
	private int status;
	private String desc;
	
	public int getStatus() {
		return status;
	}
	public String getDesc() {
		return desc;
	}
	
	
	
}
