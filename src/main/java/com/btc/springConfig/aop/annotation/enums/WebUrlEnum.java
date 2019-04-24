package com.btc.springConfig.aop.annotation.enums;

/**
 * 请求访问地址设置
 * 
 * @author admin
 * 
 */
public enum WebUrlEnum {
	
	
	/**兴业用户注册*/
	URL1000("","",1000);
	private String url;

	private String name;

	private int code;

	private WebUrlEnum(String url, String name, int code) {
		this.url = url;
		this.name = name;
		this.code = code;
	}

	public static WebUrlEnum getByCode(int code) {
		for (WebUrlEnum enums : WebUrlEnum.values()) {
			if (enums.code == code) {
				return enums;
			}
		}
		return null;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}

}
