package com.btc.util.enums;

public enum UserEnum {

	TASK_USER("admin","admin",1,"跑批用户");
	
	private String userName;
	private String userPwd;
	private int type;
	private String desc;
	
	
	private UserEnum(String userName, String userPwd, int type, String desc) {
		this.userName = userName;
		this.userPwd = userPwd;
		this.type = type;
		this.desc = desc;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public int getType() {
		return type;
	}
	public String getDesc() {
		return desc;
	}
	
	
	public static void main(String[] args) {
		System.out.println(UserEnum.TASK_USER);
	}
}
