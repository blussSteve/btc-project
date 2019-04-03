package com.btc.global.enums;

public enum SysConfigEnum {

	COUNT_INCOME_BEGIN_DATE("COUNT_INCOME_BEGIN_DATE","16","计算收益开始时间"),
	COUNT_INCOME_END_DATE("COUNT_INCOME_END_DATE","17","计算收益结束时间"),
	IS_OPEN_ASSET_TURN_OUT("IS_OPEN_ASSET_TURN_OUT","1","是否开启资产转出");
	
	private String key;
	private String value;
	private String desc;
	
	private SysConfigEnum(String key, String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
