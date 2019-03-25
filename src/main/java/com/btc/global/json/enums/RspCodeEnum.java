package com.btc.global.json.enums;


public enum RspCodeEnum {
	
	/**成功*/
	SUCCESS("0000","成功"),
	
	/**用户未登录*/
	$0001("0001","用户未登录"),
	
	/**服务器繁忙*/
	$0002("0002","服务器繁忙"),
	
	/**参数不正确*/
	$0003("0003","参数不正确"),
	
	/**非法操作*/
	$0004("0004","非法操作"),
	
	/**请勿重复操作*/
	$0005("0005","请勿重复操作"),
	
	/**系统正在维护中，请稍后重试*/
	$0006("0006","系统正在维护中，请稍后重试"),
	
	
	/**为设置响应码*/
	
	//################################################1100-1200文件上传相关####################################
	$1101("1101","图片类型不合法"),
	//################################################2100-3300用户信息###########################
	
	/**用户名已存在*/
	$2100("2100","用户名已存在"),
	
	/**权限不足*/
	$2101("2101","权限不足"),
	/**系统繁忙，请稍后重试*/ 
	FAIL("9999","系统繁忙，请稍后重试");


	
	 
	private String code;
	
	private String desc;

	private RspCodeEnum(String code, String desc) {
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
