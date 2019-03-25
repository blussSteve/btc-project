package com.btc.springConfig.cache.enums;

public enum RedisExpireEnum {

	/** 10秒 */
	S20("S_10", 10, "10秒"),
	/** 30秒 */
	S30("S_30", 30, "30秒"),
	/** 60秒 */
	S60("S_60", 60, "60秒"),
	/** 2分钟 */
	M2("M_2", 120, "2分钟"),
	/** 10分钟 */
	M5("M_5", 300, "5分钟"),
	/** 10分钟 */
	M10("M_10", 600, "10分钟"),
	/** 30分钟 */
	M30("M_30", 1800, "30分钟"),
	/** 1小时 */
	H1("H_1", 3600, "1小时"),
	/** 2小时 */
	H2("H_2", 7200, "2小时");

	private String group;

	private long time;

	private String desc;

	private RedisExpireEnum(String group, long time, String desc) {
		this.group = group;
		this.time = time;
		this.desc = desc;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
