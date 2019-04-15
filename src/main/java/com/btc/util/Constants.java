package com.btc.util;


/**
 * Created by Think on 2016/11/27.
 */
public class Constants {
    
    public static final String AUTH_TOKEN_NAME_DEFAULT = "token";

    public static final String AUTH_TOKEN_NAME = "_MCH_AT";
    public static final String AUTH_USER = "auth";
    
    public static final String USER_CACHE="user:";
    
    
    public static final int AUTH_TOKEN_AGE_MAX = 14 * 24 * 3600;
    
    /**获取短信用户*/
    
    /**系统通知*/
    public static final String SYS_NOTIFY_TIME_CACHE="sys_notify_time";
    
    
    public static final String COUNT_INCOME_KEY="count_income";
    
    /**系统字典缓存**/
    public static final String SYS_DIC_CACHE="sys_dic";
    
    public static final String USER_TOKEN_CACHE="USER_TOKEN:";
    
    public static final long USER_TOKEN_TIME=7200;
    
    /**lbank token 缓存key*/
    public static final String LBANK_TOKEN_CACHE="LBANK_TOKEN:";
    
    /**lbank token 缓存时间*/
    public static final long LBANK_TOKEN_TIME_CACHE=7000;
    
    /**计算收益**/
    public static final String ASSET_INCOME_TASK_KEY="ASSET_INCOME_TASK_KEY";
    
    /**计算可用资产*/
    public static final String ASSET_CAN_USE_COINSTASK_KEY="ASSET_CAN_USE_COINSTASK_KEY";
}
