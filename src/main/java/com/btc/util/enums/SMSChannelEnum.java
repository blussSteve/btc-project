package com.btc.util.enums;

public enum SMSChannelEnum {
	/**亿美*/
	ONE("1","亿美"),
	TOW("2","亿美运营短信");
	private String code;

    private String desc;

    private SMSChannelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static SMSChannelEnum getByCode(String code) {
        for (SMSChannelEnum enumObj : SMSChannelEnum.values()) {
            if (enumObj.getCode().equals(code)) {
                return enumObj;
            }
        }
        return null;
    }

    public static SMSChannelEnum getByDesc(String desc) {
        for (SMSChannelEnum enumObj : SMSChannelEnum.values()) {
            if (enumObj.getDesc().equals(desc)) {
                return enumObj;
            }
        }
        return null;
    }
}
