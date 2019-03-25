package com.btc.springConfig.cache.enums;

public enum RedisKeyGeneratorEnum {

	WISELY("wiselyKeyGenerator");
	
	private String method;

	private RedisKeyGeneratorEnum(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
