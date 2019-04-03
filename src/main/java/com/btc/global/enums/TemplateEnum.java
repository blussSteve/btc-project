package com.btc.global.enums;

public enum TemplateEnum {

	COST_AMOUNT("扣除金额{amount},扣除时间{dateTime};");
	
	private String model;
	
	private TemplateEnum(String model) {
		this.model = model;
	}

	

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	
}
