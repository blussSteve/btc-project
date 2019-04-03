package com.btc.lbank.enums;

public enum LbankRspCodeEnum {
	/**成功*/
	SUCCESS("0000","成功"),
	/**系统繁忙，请稍后重试*/ 
	FAIL("9999","系统繁忙，请稍后重试"),
	/**token失效*/
	$1000("1000","token失效"),
	/**交易失败*/
	$1001("1001","交易失败");
	
	private String code;
	
	private String desc;

	private LbankRspCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
