package com.btc.global.auth;

public enum AuthRoleEnum {
	
	ANONYMOUS("ANONYMOUS",1,"匿名用户"),
	USER("USER",2,"普通用户"),
	ADMIN("ADMIN",3,"管理员用户");
	
	private String role;
	private int code;
	private String desc;
	
	private AuthRoleEnum(String role, int code, String desc) {
		this.role = role;
		this.code = code;
		this.desc = desc;
	}
	public String getRole() {
		return role;
	}
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
	
	
}
