package com.btc.util.enums;

/**
 * Created by Think on 2017/2/21.
 */
public enum BatchEnum {
	ACCOUNT_ASSETS(1,"账户资产计算"),
	COUNT_TODAY_INCOME(2,"统计今日收益"),
	COUNT_NETVALUE(3,"统计今日净值"),
	COUNT_INVITE_MONEY(4,"统计邀请金额"),
	COUNT_RANK(5,"排行统计"),
	COUNT_SCHOOL_RANK(6,"统计学校排行"),
	COUNT_NOTICE(7,"更新消息通知"),
	COUNT_TEAM_RANK(8,"更新战队榜"),
	COUNT_CACHE(9,"更新缓存"),
	COUNT_CANCLE_ENTRUST(10,"取消股票委托"),
	COUNT_WAST_ENTRUST(11,"废单处理"),
	COUNT_INCOME(12,"统计收益"),
	COUNT_MARKET(13,"计算股票市值"),
	COUNT_HS_INCOME(14,"计算恒生收益"),
	COUNT_USER_POPULAR(15,"人气榜"),
	DOWN_HS_FILE(16,"下载恒生账户文件"),
	COUNT_INCOME_HS_FILE(16,"根据恒生账户文件计算资产信息"),
	COUNT_HS_FILE_CACHE(17,"根据恒生账户文件计算资产信息"),
    OTHER(99,"其他");


    private int code;
    private String desc;
    BatchEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
