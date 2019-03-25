package com.btc.global.enums.admin;

public enum AdminRoleEnum {
	SUP_ADMIN(3,"超级管理员"),
	ADMIN(2,"普通管理员"),
	GENERAL(1,"普通用户");
	
	
	private AdminRoleEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	private int type;
	private String desc;
	
	public int getType() {
		return type;
	}
	public String getDesc() {
		return desc;
	}
	
}
