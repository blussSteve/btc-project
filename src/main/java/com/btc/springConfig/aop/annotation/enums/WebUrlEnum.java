package com.btc.springConfig.aop.annotation.enums;

/**
 * 请求访问地址设置
 * 
 * @author admin
 * 
 */
public enum WebUrlEnum {
	
	//###############################(/ssm/noAuth/路径下面的)1000-1300############################################
	
	/**兴业用户注册*/
	URL1000("/ssm/noAuth/searchSchool","兴业用户注册",1000),
	
	/**登录接口*/
	URL1001("/ssm/noAuth/login","登录接口",1001),
	
	/**xuid登录*/
	URL1002("/ssm/noAuth/loginByXuid","xuid登录",1002),
	
	/**openId登录*/
	URL1003("/ssm/noAuth/loginByOpenId","openId登录",1003),
	
	/**发送手机验证码*/
	URL1004("/ssm/noAuth/sendSmsCode","发送手机验证码",1004),
	
	/**学校搜索*/
	URL1005("/ssm/noAuth/searchSchool","学校搜索",1005),
	
	/**学校列表*/
	URL1006("/ssm/noAuth/querySchool","学校列表",1006),
	
	/**分支机构列表*/
	URL1007("/ssm/noAuth/queryBranch","分支机构列表",1007),
	
	//#############################(/auth/user 路径下面)1300-1500##################################################
	
	/**个人中心*/
	URL1300("/auth/user/me","个人中心",1300),
	
	/**日志记录*/
	URL1301("/auth/user/accTrack","日志记录",1301),
	
	/**获取邀请信息*/
	URL1302("/auth/user/inviteUser","获取邀请信息",1302),
	
	/**修改用户昵称*/
	URL1303("/auth/user/updateNickName","修改用户昵称",1303),

	/**修改用户昵称*/
	URL1304("/auth/user/userFeedback","用户反馈",1304),
	
	
	//#############################(/auth/bk 路径下面)1500-1600##################################################
	
	/**获取比赛结果*/
	URL1500("/auth/bk/getMatchResult","获取比赛结果",1500),
	
	/**获取排行信息*/
	URL1501("/auth/bk/getBkRank","获取排行信息",1501),
	
	/**获取排行信息*/
	URL1502("/auth/bk/getBkRank","获取排行信息",1502),

	/**获取头脑王者个人信息*/
	URL1503("/auth/bk/getSelfPersonRank","获取头脑王者个人信息",1503),
	
	//#############################(/auth/crazypk 路径下面)1600-1700##################################################
	
	/**获取疯狂pk题目*/
	URL1600("/auth/crazypk/getCrazyPkQuestion","获取疯狂pk题目",1600),
	
	/**答题*/
	URL1601("/auth/crazypk/answerCrazyPkQuestion","答题",1601),
	
	/**查看战绩*/
	URL1602("/auth/crazypk/lookCarzyPkReward","查看战绩",1602),
	
	//#############################(/auth/crazypk 战队)1700-1800##################################################

	/**战队首页展示*/
	URL1700("/ssm/auth/team/getIndexTeamSchool","战队首页展示",1700),
	
	/**获取我的战队消息数*/
	URL1701("/ssm/auth/team/getTeamMessage","获取我的战队消息数",1701),
	
	/**获取战队详情*/
	URL1702("/ssm/auth/team/getTeamDetailInfo","获取战队详情",1702),
	
	/**搜索战队新队员*/
	URL1703("/ssm/auth/team/searchNewTeamMember","搜索战队新队员",1703),
	
	/**添加新队员*/
	URL1704("/ssm/auth/team/addNewTeamMember","添加新队员",1704),
	
	/**团队内部排行(按收益降序排序)*/
	URL1705("/ssm/auth/team/getTeamRank","团队内部排行(按收益降序排序)",1705),
	
	
	//#############################(/auth/crazypk 战队)1800-2000##################################################

	/**获取折线图数据*/
	URL1800("/ssm/auth/stock/getLineChartList","获取折线图数据",1800),
	
	/**查询当前持仓*/
	URL1801("/ssm/auth/stock/holderStock","查询当前持仓",1801),
	
	/**委托*/
	URL1802("/ssm/auth/stock/entrust","获取折线图数据",1802),
	
	/**撤销委托*/
	URL1803("/ssm/auth/stock/cancelEntrust","撤销委托",1803),
	
	/**今日委托*/
	URL1804("/ssm/auth/stock/queryTodayEntrust","今日委托",1804),
	
	/**历史委托*/
	URL1805("/ssm/auth/stock/queryHisEntrust","历史委托",1805),
	
	/**获取今日成交*/
	URL1806("/ssm/auth/stock/queryTodayTrans","获取今日成交",1806),
	
	/**获取历史成交*/
	URL1807("/ssm/auth/stock/queryHisTrans","获取历史成交",1807),
	
	/**获取前10个热门股票*/
	URL1808("/ssm/auth/stock/searhHotStock","获取前10个热门股票",1808),
	
	/**获取自选列表*/
	URL1809("/ssm/auth/stock/listOptionalStock","获取自选列表",1809),
	
	/**添加自选*/
	URL1810("/ssm/auth/stock/addOptionalStock","添加自选",1810),
	
	/**删除自选*/
	URL1811("/ssm/auth/stock/delOptionalStock","删除自选",1811),
	
	/**自选置顶*/
	URL1812("/ssm/auth/stock/upOptionalStock","自选置顶",1812),
	
	/**我的持仓界面*/
	URL1813("/ssm/auth/stock/getPersonHoldings","我的持仓界面",1813),
	
	/**我的收益*/
	URL1814("/ssm/auth/stock/getPeopleIncomeInfo","我的收益",1814),
	
	/**获取可卖数量*/
	URL1815("/ssm/auth/stock/getCanSellAmount","获取可卖数量",1815),
	
	/**增加热门股票*/
	URL1816("/ssm/auth/stock/addHotStock","增加热门股票",1816);
	
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
