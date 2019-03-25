package com.btc.util.enums;

public enum SMSModalEnum {
	/**【领航员】${content}。回复TD退订*/
	ONE(1,"【领航员】您的验证码是${content}，领航员投资挑战赛报名验证码。回复TD退订","注册短信验证码"),
	TWO(2,"【领航员】您的验证码是${content}，领航员投资挑战赛报名验证码！","注册短信验证码"),
	THREE(3,"【领航员】${content}","注册短信验证码");
	private int code;
	private String desc;
	private String mark;
	SMSModalEnum(int code,String desc,String mark){
		this.code=code;
		this.desc=desc;
		this.mark=mark;
	}
	
	 public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public static SMSModalEnum getByCode(int code) {
	        for (SMSModalEnum enumObj : SMSModalEnum.values()) {
	            if (enumObj.getCode()==code) {
	                return enumObj;
	            }
	        }
	        return null;
	}
}
