package com.btc.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.btc.global.enums.JobTypeEnum;
import com.btc.global.enums.TemplateEnum;
import com.btc.global.enums.TradeStatusEnum;
import com.btc.global.enums.TradeTypeEnum;
import com.btc.global.enums.YesOrNoEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.lbank.bean.LbankToken;
import com.btc.lbank.bean.LbankTrade;
import com.btc.lbank.bean.LbankUserInfo;
import com.btc.lbank.util.LbankResult;
import com.btc.lbank.util.LbankUtil;
import com.btc.mapper.AccountMapper;
import com.btc.mapper.CoinRecordMapper;
import com.btc.mapper.SysTaskJobMapper;
import com.btc.mapper.UserDayTotalCoinRecordMapper;
import com.btc.mapper.UserIncomeRecordMapper;
import com.btc.mapper.UserInfoMapper;
import com.btc.mapper.admin.SysCoinsDicMapper;
import com.btc.model.Account;
import com.btc.model.CoinRecord;
import com.btc.model.UserDayTotalCoinRecord;
import com.btc.model.UserIncomeRecord;
import com.btc.model.UserInfo;
import com.btc.model.admin.SysCoinsDic;
import com.btc.service.RedisService;
import com.btc.service.UserService;
import com.btc.util.Constants;
import com.btc.util.DataCacheUtil;
import com.btc.util.HolidayUtil;
import com.btc.util.ObjectUtil;
import com.btc.util.RedisCacheConstant;
import com.btc.util.StringUtil;
import com.btc.util.USDTUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class UserServiceImpl implements UserService{
	private final static Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private LbankUtil lbankUtil;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SysCoinsDicMapper sysCoinsDicMapper;
	
	@Autowired
	private UserIncomeRecordMapper userIncomeRecordMapper;
	
	@Autowired
	private CoinRecordMapper coinRecordMapper;
	
	@Autowired
	private UserDayTotalCoinRecordMapper userDayTotalCoinRecordMapper;
	
	@Autowired
	private SysTaskJobMapper sysTaskJobMapper;
	
	/**
	 * 用户信息
	 */
	@Override
	public Page<UserInfo> queryUserInfo(Page<UserInfo> page,UserInfo user){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(user);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<UserInfo> list=userInfoMapper.queryUserInfo(params);
		 	int count=userInfoMapper.getUserInfoCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	/**
	 * 账户信息
	 */
	@Override
	public Page<Account> queryAccountInfo(Page<Account> page,Account obj){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(obj);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<Account> list=accountMapper.queryAccount(params);
		 	int count=accountMapper.getAccountCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	
	/**
	 * 授权登录
	 * @param accessCode
	 * @return
	 */
	@Override
	public JsonResult userLogin(String accessCode){
		
		LbankResult<LbankToken> result=lbankUtil.auth(accessCode);
		
		if(!result.isFullSuccess()){
			
			return JsonResultHelp.buildFail(RspCodeEnum.$2600);
		}
		//如果成功
		LbankToken lbankToken=result.getObj();
		
		redisService.set(Constants.LBANK_TOKEN_CACHE+lbankToken.getOpen_id(), JSON.toJSONString(lbankToken),Constants.LBANK_TOKEN_TIME_CACHE);
		
		String token=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		
		
		//获取用户信息
		LbankResult<LbankUserInfo> lUserR=lbankUtil.getUserInfo(lbankToken.getOpen_id(),lbankToken.getAccess_token());
		String userName="";
		if(null!=lUserR){
			userName=lUserR.getObj().getUser_name();
		}
		
		//判断用户是否注册
		UserInfo userInfo=userInfoMapper.getUserInfo(lbankToken.getOpen_id());
		
		
		if(null==userInfo){
			userInfo=new UserInfo();
			userInfo.setOpenId(lbankToken.getOpen_id());
			userInfo.setToken(token);
			userInfo.setUserName(userName);
			userInfo.setLastLoginTime(new Date());
			userInfo.setGmtCreate(new Date());
			userInfo.setGmtModify(new Date());
			userInfoMapper.insert(userInfo);
			
		}else{
			userInfo.setUserName(userName);
			userInfo.setToken(token);
			userInfo.setLastLoginTime(new Date());
			userInfoMapper.updateByPrimaryKeySelective(userInfo);
		}
		//设置token;
		redisService.set(Constants.USER_TOKEN_CACHE+userInfo.getToken(), JSON.toJSONString(userInfo),Constants.USER_TOKEN_TIME);
		
		return JsonResultHelp.buildSucc(token);
	}
	
//	@Transactional
	@Override
	public JsonResult trade(String openId,long userId,String amount,String assetCode,int tradeType){
		
		
		//判断是否允许交易，资产计算期间禁止交易
		if(!HolidayUtil.isCanTran()){
			//判断是否以及计算今日收益
			String dateStr=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			boolean flag=sysTaskJobMapper.checkJobIsRun(JobTypeEnum.COUNT_INCOME.getCode(), dateStr);
			if(!flag){
				return JsonResultHelp.buildFail(RspCodeEnum.$2604);
			}
			
		}
		
		String lbankTrade="";
		
		if(TradeTypeEnum.IN.getType()==tradeType){//划入
			lbankTrade="0";
		}else if(TradeTypeEnum.OUT.getType()==tradeType){//划出
			lbankTrade="1";
		}else{
			return JsonResultHelp.buildFail(RspCodeEnum.$2602);
		}
		Account account=accountMapper.getAccountByUser2Code(userId, assetCode);
		
		if(null==account){
			account=new Account();
			account.setUserId(userId);
			account.setCoins(BigDecimal.ZERO);
			account.setCanUseCoins(BigDecimal.ZERO);
			account.setCoinCode(assetCode);
			account.setTodayIncome(BigDecimal.ZERO);
			account.setTotalIncome(BigDecimal.ZERO);
			account.setStatus(YesOrNoEnum.YES.getCode());//资产冻结中
			account.setGmtCreate(new Date());
			account.setGmtModify(new Date());
			account.setTodayCoins(BigDecimal.ZERO);
			accountMapper.insert(account);
		}else{
			if(YesOrNoEnum.NO.getCode()== account.getStatus()){
				return JsonResultHelp.buildFail(RspCodeEnum.$2304);
			}
			account.setStatus(YesOrNoEnum.NO.getCode());//资产冻结中
			
		}
		
		
		//如果是划入操作
		if(tradeType==TradeTypeEnum.IN.getType()){
			SysCoinsDic coinDic=sysCoinsDicMapper.getCoinsCodeDicByCode(assetCode);
			if(coinDic.getMinTurnInCount().compareTo(new BigDecimal(amount))==1){
				return JsonResultHelp.buildFail(RspCodeEnum.$2601,"资产划入失败,最小划入数量为:"+coinDic.getMinTurnInCount());
			}
		}
		//如果是划出操作
		if(tradeType==TradeTypeEnum.OUT.getType()){
			SysCoinsDic coinDic=sysCoinsDicMapper.getCoinsCodeDicByCode(assetCode);
			if(coinDic.getMinTurnOutCount().compareTo(new BigDecimal(amount))==1){
				return JsonResultHelp.buildFail(RspCodeEnum.$2601,"资产划入失败,最小出数量为:"+coinDic.getMinTurnOutCount());
			}
			//判断资产是否充足
			if(account.getCoins().compareTo(new BigDecimal(amount))==-1){
				return JsonResultHelp.buildFail(RspCodeEnum.$2603,"当前可用资产为:"+account.getCoins().doubleValue());
			}
			
		}
		accountMapper.updateAccountStatus(account.getId(), YesOrNoEnum.NO.getCode());
		String orderNo=UUID.randomUUID().toString().replace("-", "").toLowerCase();
		
		//增加交易流水
		
		CoinRecord record=new CoinRecord();
		record.setUserId(userId);
		record.setAccountId(account.getId());
		record.setCoins(new BigDecimal(amount));
		record.setCoinCode(assetCode);
		record.setTradeType(tradeType);
		record.setStatus(TradeStatusEnum.init.getStatus());
		record.setIsSystemOperate(YesOrNoEnum.YES.getCode());
		record.setTotalCoins(account.getCoins());
		record.setOperateUserId(-1l);
		record.setOrderNo(orderNo);
		record.setGmtCreate(new Date());
		record.setGmtModify(new Date());
		coinRecordMapper.insert(record);
		
		
		//调用交易接口
		LbankToken token=DataCacheUtil.getLbankToken(openId);
		
		if(!lbankUtil.checkTokenIsValid(token.getAccess_token(), openId).isSuccess()){
			//刷新token
			LbankResult<LbankToken> refresh=lbankUtil.refreshToken(token.getRefresh_token());
			if(!refresh.isFullSuccess()){
				return JsonResultHelp.buildFail(RspCodeEnum.$2601,refresh.getMsg());
			}
			
			token.setAccess_token(refresh.getObj().getAccess_token());
			token.setRefresh_token(refresh.getObj().getRefresh_token());
			
			redisService.set(Constants.LBANK_TOKEN_CACHE+token.getOpen_id(), JSON.toJSONString(token),Constants.LBANK_TOKEN_TIME_CACHE);

		}
		
		LbankResult<LbankTrade> ltrad=lbankUtil.trade(token.getAccess_token(), orderNo, lbankTrade, assetCode, amount, openId);
		
		if(ltrad.isFullSuccess()){
			record.setStatus(TradeStatusEnum.SUCCESS.getStatus());
			record.setBindNo(ltrad.getObj().getBiz_number());
			record.setGmtModify(new Date());
			
		}else{
			//如果失败再次查询
			
			
			if("9999".equals(ltrad.getCode())){
				
				LbankResult<LbankTrade> lb=lbankUtil.queryOrder(orderNo);
				
				if(lb.isFullSuccess()){
					if(lb.getObj().getStatus()==1){
						record.setBindNo(lb.getObj().getBiz_number());
						record.setStatus(TradeStatusEnum.SUCCESS.getStatus());
						record.setGmtModify(new Date());
					}else{
						record.setStatus(TradeStatusEnum.FAIL.getStatus());
						record.setBindNo(lb.getObj().getBiz_number());
						record.setGmtModify(new Date());
					}
					
				}else{
					record.setStatus(TradeStatusEnum.FAIL.getStatus());
					record.setGmtModify(new Date());
				}
			}else{
				record.setStatus(TradeStatusEnum.FAIL.getStatus());
				record.setGmtModify(new Date());
			}
			
		
			
			
		}
		coinRecordMapper.updateByPrimaryKeySelective(record);
		
		
		if(tradeType==TradeTypeEnum.IN.getType()){
			
			if(record.getStatus()==TradeStatusEnum.SUCCESS.getStatus()){
				//如果交易成功,增加今日的成交数据，同时增加账户的总资产
				
				UserDayTotalCoinRecord dayCoin=userDayTotalCoinRecordMapper.getUserDayTotalCoinRecord(userId, account.getId(), assetCode, new Date());
				
				if(null==dayCoin){
					dayCoin=new UserDayTotalCoinRecord();
					dayCoin.setUserId(userId);
					dayCoin.setAccountId(account.getId());
					dayCoin.setCoinCode(assetCode);
					dayCoin.setCoins(new BigDecimal(amount));
					dayCoin.setCountDate(new Date());
					dayCoin.setGmtCreate(new Date());
					dayCoin.setGmtModify(new Date());
					dayCoin.setCostAmount(BigDecimal.ZERO);
					dayCoin.setCostAmountDesc("");
					userDayTotalCoinRecordMapper.insert(dayCoin);
				}else{
					userDayTotalCoinRecordMapper.updateUserDayTotalCoinRecord(amount,userId, account.getId(), assetCode, new Date());
				}
				
				account.setCoins(account.getCoins().add(new BigDecimal(amount)));
				accountMapper.updateAccount(amount, account.getId());
			}
		}
		
		if(tradeType==TradeTypeEnum.OUT.getType()){
			
			//如果交易成功
			if(record.getStatus()==TradeStatusEnum.SUCCESS.getStatus()){
				
				//扣除流程
				/**
				 * 1.先从今日转入的扣除(btc_user_day_total_coin_record)
				 * 2.如果今天暂未处理计息收益，从昨日的扣除(btc_user_day_total_coin_record)
				 * 3.最后从计息资产里面扣除(btc_account)
				 * */
				BigDecimal totalCostAmount=new BigDecimal(amount);
				BigDecimal superlusAmount=totalCostAmount;
				BigDecimal costAmount=BigDecimal.ZERO;
				
				//直接扣除资产
				accountMapper.updateAccountSub(totalCostAmount.toString(), account.getId());
				
				UserDayTotalCoinRecord today=userDayTotalCoinRecordMapper.getUserDayTotalCoinRecord(userId, account.getId(), assetCode, new Date());
				if(null!=today){
					superlusAmount=superlusAmount.subtract(today.getCoins().subtract(today.getCostAmount()));
					//如果够扣
					if(superlusAmount.compareTo(BigDecimal.ZERO)==-1){
						costAmount=totalCostAmount;
					}else{
						costAmount=totalCostAmount;
						totalCostAmount=superlusAmount;
						
					}
					today.setCostAmount(today.getCostAmount().add(costAmount));
					
					Map<String, String> jstlMap=Maps.newHashMap();
					
					jstlMap.put("amount", costAmount.toString());
					
					String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					jstlMap.put("dateTime", dateTime);
					
					String jstl=StringUtil.replaceJstlMap(TemplateEnum.COST_AMOUNT.getModel(), jstlMap);
					
					today.setCostAmountDesc(today.getCostAmountDesc()+jstl);
					
					userDayTotalCoinRecordMapper.updateByPrimaryKeySelective(today);
					
				}
				
				if(superlusAmount.compareTo(BigDecimal.ZERO)==1){
					
					//判断是否以及计算今日收益
					String dateStr=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					
					boolean flag=sysTaskJobMapper.checkJobIsRun(JobTypeEnum.COUNT_INCOME.getCode(), dateStr);
					
					if(!flag){
						
						UserDayTotalCoinRecord yesterday=userDayTotalCoinRecordMapper.getUserDayTotalCoinRecord(userId, account.getId(), assetCode, DateUtils.addDays(new Date(),-1));
						if(null!=yesterday){
							
							superlusAmount=superlusAmount.subtract(yesterday.getCoins().subtract(yesterday.getCostAmount()));
							
							//如果够扣
							if(superlusAmount.compareTo(BigDecimal.ZERO)==-1){
								costAmount=totalCostAmount;
							}else{
								costAmount=totalCostAmount;
								totalCostAmount=superlusAmount;
								
							}
							yesterday.setCostAmount(yesterday.getCostAmount().add(costAmount));
							
							Map<String, String> jstlMap=Maps.newHashMap();
							
							jstlMap.put("amount", costAmount.toString());
							
							String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
							jstlMap.put("dateTime", dateTime);
							
							String jstl=StringUtil.replaceJstlMap(TemplateEnum.COST_AMOUNT.getModel(), jstlMap);
							
							yesterday.setCostAmountDesc(yesterday.getCostAmountDesc()+jstl);
							
							userDayTotalCoinRecordMapper.updateByPrimaryKeySelective(yesterday);
						}
					}
					
					
				}
				
				if(superlusAmount.compareTo(BigDecimal.ZERO)==1){
					superlusAmount=account.getCanUseCoins().subtract(superlusAmount);
					//如果扣除后剩余金额大于0
					if(superlusAmount.compareTo(BigDecimal.ZERO)==-1){
						logger.error("用户资产划出错误,划出资产超过用户可提资产openId:{},userId:{},amount:{},assetCode:{},tradeType:{}",openId,userId,amount,assetCode,tradeType);
						//总资产记为0
						//计息资产计为0
						accountMapper.updateAccountAssetZero(account.getId());
					}else{
						//修改计息资产
						accountMapper.updateAccountCanUseAmount(totalCostAmount, account.getId());
						
					}
					
				}
				
				
			}
			
		}
		//解锁账户
		accountMapper.updateAccountStatus(account.getId(), YesOrNoEnum.YES.getCode());
		
		return JsonResultHelp.buildSucc();
		
	}
	
	
	/**
	 * 获取资产信息
	 * @param userId
	 * @return
	 */
	@Override
    @Cacheable(value =RedisCacheConstant.REDIS_CACHE_GROUP_S30,keyGenerator=RedisCacheConstant.REDIS_CACHE_GENERATOR_WISELY) 
	public JsonResult getAssetInfo(long userId){
		
		Map<String,Object> map=Maps.newHashMap();
		
		BigDecimal yesterdayIncome=BigDecimal.ZERO;
		BigDecimal totalIncome=BigDecimal.ZERO;
		BigDecimal yearRate=BigDecimal.ZERO;
		BigDecimal totalAsset=BigDecimal.ZERO;
		BigDecimal allAsset=BigDecimal.ZERO;
		StringBuffer assetCodes=new StringBuffer();
		
		List<Account> listAccount=accountMapper.queryUserAccount(userId);
		for(Account account:listAccount){
			assetCodes.append(account.getCoinCode().toLowerCase()+",");
		}
		UserInfo user=userInfoMapper.selectByPrimaryKey(userId);
		
		LbankToken ltoken=DataCacheUtil.getLbankToken(user.getOpenId());
	
		
		if(!lbankUtil.checkTokenIsValid(ltoken.getAccess_token(), ltoken.getOpen_id()).isSuccess()){
			//刷新token
			LbankResult<LbankToken> refresh=lbankUtil.refreshToken(ltoken.getRefresh_token());
			if(!refresh.isFullSuccess()){
				return JsonResultHelp.buildFail(RspCodeEnum.$2601,refresh.getMsg());
			}
			
			ltoken.setAccess_token(refresh.getObj().getAccess_token());
			ltoken.setRefresh_token(refresh.getObj().getRefresh_token());
			
			redisService.set(Constants.LBANK_TOKEN_CACHE+ltoken.getOpen_id(), JSON.toJSONString(ltoken),Constants.LBANK_TOKEN_TIME_CACHE);

		}
		
		//批量获取账户余额
		LbankResult<JSONObject> lr=lbankUtil.batchQueryBalance(user.getOpenId(), ltoken.getAccess_token(), assetCodes.toString());
		
		JSONObject jb=lr.getObj();
		for(Account account:listAccount){
			String curAmount="";
			if(null!=jb){
				curAmount=jb.getString(account.getCoinCode().toLowerCase());
			}
			
			BigDecimal usdtUnit=USDTUtil.getUSDT(account.getCoinCode());
			yesterdayIncome=yesterdayIncome.add(account.getTodayIncome().multiply(usdtUnit));
			totalIncome=totalIncome.add(account.getTotalIncome().multiply(usdtUnit));
			totalAsset=totalAsset.add(account.getCoins().multiply(usdtUnit));
			
			if(null!=jb){
				allAsset=allAsset.add(new BigDecimal(curAmount).multiply(usdtUnit)).add(totalAsset);
			}
			
			
		}
		if(totalAsset.compareTo(BigDecimal.ZERO)==1){
			yearRate=yesterdayIncome.divide(totalAsset).multiply(new BigDecimal(365));
		}
		
	
		
		//最近7天收益k线
		List<Map<String,String>> kline=userIncomeRecordMapper.queryUserCoinUsdtIncomeLine(userId, -7);
		
		map.put("yesterdayIncome", yesterdayIncome.setScale(4, BigDecimal.ROUND_DOWN));
		map.put("totalIncome", totalIncome.setScale(4, BigDecimal.ROUND_DOWN));
		map.put("yearRate", yearRate.multiply(new BigDecimal(365)).setScale(4, BigDecimal.ROUND_DOWN));
		map.put("totalAsset",totalAsset.setScale(4, BigDecimal.ROUND_DOWN));
		map.put("allAsset",allAsset.setScale(4, BigDecimal.ROUND_DOWN));
		map.put("kline", kline);
		
		return JsonResultHelp.buildSucc(map);
		
	}
	
	/**
	 * 获取资产列表信息
	 * @param userId
	 * @return
	 */
	@Override
	public JsonResult getAssetList(long userId){
		
		List<Map<String,Object>> listAsset=Lists.newArrayList();
		
		List<SysCoinsDic> listCoinDic=sysCoinsDicMapper.queryAllCoins();
		
		List<Account> listAccount=accountMapper.queryUserAccount(userId);
		
		Map<String,Account> accountMap=Maps.newHashMap();
		for(Account act:listAccount){
			accountMap.put(act.getCoinCode(), act);
		}
		
		UserInfo user=userInfoMapper.selectByPrimaryKey(userId);
		
		LbankToken ltoken=DataCacheUtil.getLbankToken(user.getOpenId());
		
	
		
		for(SysCoinsDic dic:listCoinDic){
			
			Account act=accountMap.get(dic.getCoinCode());
			Map<String,Object> map=Maps.newHashMap();
			map.put("coinCode", dic.getCoinCode());
			map.put("totalCoin", "0");
			map.put("countCoin", "0");
			map.put("todayIncome", "0");
			map.put("totalIncome", "0");
			map.put("kline", "");
			
			LbankResult<String> lresult=lbankUtil.queryBalance(user.getOpenId(), ltoken.getAccess_token(),dic.getCoinCode());
			if(lresult.isFullSuccess()){
				map.put("totalCoin",lresult.getObj());
			}
			if(null!=act){
			
				map.put("countCoin",  act.getCoins().doubleValue());
				map.put("todayIncome", act.getTodayIncome().doubleValue());
				map.put("totalIncome", act.getTotalIncome().doubleValue());
				
			}
			
			//最近7天收益
			List<UserIncomeRecord> listUserIncomeRecord= userIncomeRecordMapper.queryUserCoinIncomeLine(userId, dic.getCoinCode(), -7);
		
			if(null!=listUserIncomeRecord&&listUserIncomeRecord.size()>0){
				
				List<Map<String,String>> listk=Lists.newArrayList();
				for(UserIncomeRecord income:listUserIncomeRecord){
					Map<String,String> kmap=Maps.newHashMap();
				
					String  date=new SimpleDateFormat("yyyy-MM-dd").format(income.getGmtCreate());
					kmap.put("date", date);
					kmap.put("data", income.getCoinIncome().doubleValue()+"");
					listk.add(kmap);
				}
			
				map.put("kline", listk);
			}
			
			listAsset.add(map);
		}
		
		return JsonResultHelp.buildSucc(listAsset);
		
	}
	
	/**
	 * 资金流水
	 */
	@Override
	public Page<Map<String,String>> queryCoinRecord(Page<Map<String,String>> page,CoinRecord coinRecord){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(coinRecord);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<CoinRecord> list=coinRecordMapper.queryCoinRecord(params);
		 	
		 	List<Map<String,String>> listMap=Lists.newArrayList();
		 	for(CoinRecord bean:list){
		 		Map<String,String> map=Maps.newHashMap();
		 		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bean.getGmtCreate());
		 		map.put("date", date);
		 		map.put("coinCode", bean.getCoinCode());
		 		map.put("tradeAmount", bean.getCoins().doubleValue()+"");
		 		map.put("curAsset", bean.getTotalCoins().doubleValue()+"");
		 		map.put("tradeType", bean.getTradeType()+"");
		 		listMap.add(map);
		 	}
		 	
		 	int count=coinRecordMapper.getCoinRecordCount(params);
		 	page.setRows(listMap);
		 	page.setTotal(count);
		 	return page;
	}
	
	
}
