package com.btc.util.enums;

public enum TeamMsgEnum {
	MSG_1("001","邀请你加入战队:${content}"),
	MSG_2("002","申请加入战队:${content}"),
	MSG_3("003","您已被队长剔除${content}");
	
	private String code;
	
	private String desc;

	private TeamMsgEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	

}
