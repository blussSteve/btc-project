package com.btc.global.auth;


public enum TokenEnum {

	LOGIN("40001","登录");
	
	private String code;
	
	private String desc;

	private TokenEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public static TokenEnum getByCode(String code) {
        for (TokenEnum enums : TokenEnum.values()) {
            if (enums.code==code) {
                return enums;
            }
        }
        return null;
    }
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
