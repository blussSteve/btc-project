package com.btc.util;

public class SysConstants {
	
	/**1天*/
	public final static long DYA_TIME=86400L;
	
	/**3天*/
	public final static long THREE_DAY_TIME=86400L*3;

	/**月*/
	public final static long MONTH_TIME=86400L*30;
	/**积分缓存*/
	public final static String SCORE_DIC_CACHE="score_dic";
	
	/**比赛信息*/
	public final static String BK_MATCH_CACHE="bk_match:";
	
	/**session缓存信息*/
	public final static String BK_SESSION_CACHE="bk_session:";
	
	/**用户缓存信息*/
	public final static String BK_USER_CACHE="bk_user";
	
	/**用户比赛缓存*/
	public final static String BK_USER_MATCH_CACHE="bk_user_match:";
	
	/**答题时间*/
	public final static int BK_ANSWER_TIME=10;
	
	/**获取答案时间*/
	public final static int BK_GET_ANSWER_TIME=7;

	/**答题分数汇率*/
	public final static int BK_ANSWER_SCORE_MULTIPLE=10;
	
	/**用户一些缓存字典*/
	public final static String USER_DIC_CACHE="user_dic_cache";
	
	/**系统用户*/
	public final static String SYS_USER_CACHE="sys_user_cache:";
	
	public final static long BK_REDIS_CACHE_TIME=600L;
	
	/*****************************股票**********************************/
	
	/**股票委托时间*/
	public final static long STOCK_ENTRUST_TIME=86400*7;
	
	/**股票账户委托时间*/
	public final static long STOCK_ENTRUST_ACCOUNT_TIME=86400*7;
	
	/**股票委托流水号时间*/
	public final static long STOCK_ENTRUST_DEAL_ORDER_NO_TIME=86400*7;
	
	/**股票撤单失败操作数据*/
	public final static String STOCK_CANCEL_FAIL_CACHE="stock_cancle_fail:";
	
	/**股票交易失败操作统计*/
	public final static String STOCK_TRAN_FAIL_CACHE="stock_tran_fail:";
	
	/**股票交易委托失败操作统计*/
	public final static String STOCK_ENTRUST_DEAL_FAIL_CACHE="stock_entrust_deal_fail:";
	
	/**记录跑批失败的收益账户*/
	public final static String STOCK_COUNT_TODAY_INCOME_FAIL_CACHE="stock_count_today_income_fail:";
	
	/**统计股票总资产失败缓存*/
	public final static String STOCK_COUNT_USER_TOTAL_ASSESTS_FAIL_CACHE="stock_count_user_total_fail_assests:";
	
	/**统计股票总资产缓存*/
	public final static String STOCK_COUNT_USER_TOTAL_ASSESTS_CACHE="stock_count_user_total_assests:";
	
	/**股票最近一天的收益*/
	public final static String STOCK_LAST_DAY_INCOME_CACHE="stock_last_day_income";
	
	
	/**股票委托账户*/
	public final static String STOCK_ENTRUST_ACCOUNT_CACHE="stock_entrust_account:";
	
	/**股票委托缓存*/
	public final static String STOCK_ENTRUST_CACHE="stock_entrust:";
	
	/**股票委托已经处理的流水缓存缓存*/
	public final static String STOCK_ENTRUST_DEAL_ORDER_NO_CACHE="stock_entrust_deal_order_no:";
	
	/**股票取消委托失败缓存*/
	public final static String STOCK_CANCLE_ENTRUST_FAIL_CACHE="stock_cancle_entrust_fail:";
	
	/**委托废单处理*/
	public final static String STOCK_ENTRUST_WAST_CACHE="stock_entrust_wast:";
	
	/**邀请key*/
	public final static String INVITE_KEY="INVITE_KEY";
	
	/**机器人*/
	public final static String ROBOT_KEY="ROBOT_KEY";
	
	/**可用资金*/
	public final static String CAN_USE_CAHCE_KEY="CAN_USE_CAHCE_KEY";
	
	/**计算用户总资产*/
	public final static String COUNT_USER_ASSETS_KEY="COUNT_USER_ASSETS_KEY";
	
	/**计算收益*/
	public final static String COUNT_TODAY_INCOME_KEY="COUNT_TODAY_INCOME_KEY";
	
	/**疯狂pk*/
	public final static String COUNT_CRAZY_KEY="COUNT_CRAZY_KEY";
	
	/**统计所有*/
	public final static String COUNT_ALL_KEY="COUNT_ALL_KEY";
	
	/**股票取消委托缓存*/
	public final static String COUNT_CANCLE_ENTRUST_KEY="COUNT_CANCLE_ENTRUST_KEY";
	
	/**废单key*/
	public final static String COUNT_WASTE_KEY="COUNT_WASTE";
	
	/**热门股票*/
	public final static String COUNT_HOT_KEY="COUNT_HOT_KEY";
	
	/**计算收益*/
	public final static String COUNT_INCOME_KEY="COUNT_INCOME_KEY";
	
	/**计算股票市值*/
	public final static String COUNT_MARKET_KEY="COUNT_MARKET_KEY";
	
	/**统计恒生总收益*/
	public final static String COUNT_HS_INCOME_KEY="COUNT_HS_INCOME_KEY";
	
	/***/
	public final static String COUNT_ENTRUST_INCOME_KEY="COUNT_ENTRUST_INCOME_KEY"; 
	
	/**计算邀请人气排行*/
	public final static String COUNT_INVITE_USER_POPLUR_RANK_KEY="COUNT_INVITE_USER_POPLUR_RANK_KEY"; 
	
	
	/*******************************************************************************************/
	
	/**恒生文件*/
	public final static String HS_FILE_CACHE="hs_file_cache:";
	
	/**恒生文件*/
	public final static String COUNT_HS_FILE_CACHE="count_hs_file_cache:";
	
	/******************************************游戏**********************************************/
	
	/**统计游戏超时缓存*/
	public final static String COUNT_BK_TIME_OUT_KEY="COUNT_BK_TIME_OUT_KEY";
	
	/**统计游戏超时缓存*/
	
	
}
