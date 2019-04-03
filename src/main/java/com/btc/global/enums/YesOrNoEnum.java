package com.btc.global.enums;

public enum YesOrNoEnum {

	YES(1),
	NO(0);
	private int code;
	
	private YesOrNoEnum(int code) {
		this.code = code;
	}

	public static boolean checkIsTrue(int code){
		
		for(YesOrNoEnum enums:YesOrNoEnum.values()){
			
			if(enums.getCode()==code){
				return true;
			}
		}
		return false;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
