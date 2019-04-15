package com.btc.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import com.btc.lbank.bean.LbankTrade;
import com.btc.lbank.util.LbankResult;
import com.btc.lbank.util.LbankUtil;
import com.btc.mapper.AccountBackMapper;
import com.btc.mapper.AccountMapper;
import com.btc.mapper.AssetIncomeRecordMapper;
import com.btc.mapper.AssetTotalMapper;
import com.btc.mapper.CoinRateRecordMapper;
import com.btc.mapper.CoinRecordMapper;
import com.btc.mapper.SysTaskJobMapper;
import com.btc.mapper.UserDayTotalCoinRecordMapper;
import com.btc.mapper.UserIncomeRecordMapper;
import com.btc.mapper.UserIncomeVerifyMapper;
import com.btc.mapper.UserIncomeVerifyTempMapper;
import com.btc.mapper.UserInfoMapper;
import com.btc.mapper.admin.SysCoinsDicMapper;
import com.btc.model.Account;
import com.btc.model.AccountBack;
import com.btc.model.AssetIncomeRecord;
import com.btc.model.AssetTotal;
import com.btc.model.CoinRateRecord;
import com.btc.model.CoinRecord;
import com.btc.model.SysTaskJob;
import com.btc.model.UserDayTotalCoinRecord;
import com.btc.model.UserIncomeRecord;
import com.btc.model.UserIncomeVerify;
import com.btc.model.UserIncomeVerifyTemp;
import com.btc.model.UserInfo;
import com.btc.model.admin.SysCoinsDic;
import com.btc.service.AssetService;
import com.btc.util.Constants;
import com.btc.util.ObjectUtil;
import com.btc.util.RedissonManager;
import com.btc.util.SpringUtil;
import com.btc.util.StringUtil;
import com.btc.util.USDTUtil;
import com.google.common.collect.Maps;

@Service
public class AssetServiceImpl implements AssetService {

	private final static Logger logger=LoggerFactory.getLogger(AssetServiceImpl.class);
	
	
	@Value("${lbank.app_id}")
	private String lbankOpenId;
	 
	@Value("${lbank.secret}")
	private String lbankSecret;
	
	@Autowired
	private CoinRateRecordMapper coinRateRecordMapper;
	
	@Autowired
	private CoinRecordMapper coinRecordMapper;
	
	@Autowired
	private UserIncomeRecordMapper userIncomeRecordMapper;
	
	@Autowired
	private UserIncomeVerifyMapper userIncomeVerifyMapper;
	
	@Autowired
	private UserIncomeVerifyTempMapper  userIncomeVerifyTempMapper;
	
	@Autowired
	private SysTaskJobMapper sysTaskJobMapper;
	
	@Autowired
	private UserDayTotalCoinRecordMapper userDayTotalCoinRecordMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountBackMapper accountBackMapper;
	
	@Autowired
	private SysCoinsDicMapper sysCoinsDicMapper;
	
	@Autowired
	private AssetTotalMapper assetTotalMapper;
	
	@Autowired
	private AssetIncomeRecordMapper assetIncomeRecordMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private LbankUtil lbankUtil;
	
	@Autowired
	private AssetService assetService;
	
	
	/**
	 * 资金流水
	 */
	@Override
	public Page<CoinRecord> queryCoinRecord(Page<CoinRecord> page,CoinRecord coinRecord){
		
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
		 	int count=coinRecordMapper.getCoinRecordCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	
	/**
	 * 收益流水
	 */
	@Override
	public Page<UserIncomeRecord> queryCoinIncomeRecord(Page<UserIncomeRecord> page,UserIncomeRecord coinRecord){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(coinRecord);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<UserIncomeRecord> list=userIncomeRecordMapper.queryCoinIncomeRecord(params);
		 	int count=userIncomeRecordMapper.getCoinRecordIncomeCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	
	
	
	/**
	 * 审核流水
	 */
	@Override
	public Page<UserIncomeVerify> queryUserIncomeVerifyRecord(Page<UserIncomeVerify> page,UserIncomeVerify coinRecord){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(coinRecord);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<UserIncomeVerify> list=userIncomeVerifyMapper.queryUserIncomeVerifyRecord(params);
		 	int count=userIncomeVerifyMapper.getUserIncomeVerifyRecordCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	/**
	 * 审核缓存流水
	 */
	@Override
	public Page<UserIncomeVerifyTemp> queryUserIncomeVerifyTempRecord(Page<UserIncomeVerifyTemp> page,UserIncomeVerifyTemp coinRecord){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(coinRecord);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<UserIncomeVerifyTemp> list=userIncomeVerifyTempMapper.queryUserIncomeVerifyTempRecord(params);
		 	int count=userIncomeVerifyTempMapper.getUserIncomeVerifyRecordTempCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	/**
	 * 生成资金收益流水信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Override
	@Transactional
	public JsonResult createUserIncomeVerifyTemp(int isSystemOperate,long operateId) {
		
		try {
			 if(RedissonManager.lock(Constants.COUNT_INCOME_KEY, 30, TimeUnit.SECONDS)){
				Date date=new Date();
				String dateStr=new SimpleDateFormat("yyyy-MM-dd").format(date);
				//判断是否以及计算今日收益
				boolean flag=sysTaskJobMapper.checkJobIsRun(JobTypeEnum.COUNT_INCOME.getCode(), dateStr);
				
				if(flag){
					return JsonResultHelp.buildFail(RspCodeEnum.$2400);
				}
				
				
				//获取总资产记录
				
				
				List<AssetTotal> listAssetTotal=assetTotalMapper.queryAll();
				
				Map<String,AssetTotal> assetTotalDestMap=Maps.newHashMap();
				
				Map<String,AssetIncomeRecord> assetIncomeRecordMap=Maps.newHashMap();
				
				Map<String,AssetTotal> assetTotalMap=Maps.newHashMap();
				for(AssetTotal bean:listAssetTotal){
					assetTotalMap.put(bean.getCoinCode(), bean);
				}
				
				
				//获取货币字典配置配置
				
				List<SysCoinsDic> listCoinDic=sysCoinsDicMapper.queryAllCoins();
				 for(SysCoinsDic bean:listCoinDic){
					 sysCoinsDicMapper.updateCoinUSDT(USDTUtil.getUSDT(bean.getCoinCode()), bean.getCoinCode(),date);
				 }
				 listCoinDic=sysCoinsDicMapper.queryAllCoins();
				
				Map<String,SysCoinsDic> coinDicMap=Maps.newHashMap();
				 
				 for(SysCoinsDic bean:listCoinDic){
					 coinDicMap.put(bean.getCoinCode(), bean);
				 }
				
				
				//获取当天配置的收益信息表
				 List<CoinRateRecord> listCoinRateRecord=coinRateRecordMapper.queryCoinRateByDate(dateStr);
				 
				 if(listCoinRateRecord==null||listCoinRateRecord.size()<=0){
					 sysTaskJobMapper.insert(new SysTaskJob(date,new Date(),JobTypeEnum.COUNT_INCOME.getCode(),YesOrNoEnum.NO.getCode(), "暂未配置今天收益率信息"));
					 return JsonResultHelp.buildFail(RspCodeEnum.$2303);
					 
				 }
				 
				 
				 Map<String,CoinRateRecord> coinRateRecordMap=Maps.newHashMap();
				 
				 for(CoinRateRecord bean:listCoinRateRecord){
					 coinRateRecordMap.put(bean.getCoinCode(), bean);
				 }
				
				
				//获取所有用户数据
				 
				 List<Account> listAccount=accountMapper.queryAll();
				 
				 Map<String,Account> accountMap=Maps.newHashMap();
				 for(Account bean:listAccount){
					 accountMap.put(bean.getUserId()+":"+bean.getId()+":"+bean.getCoinCode(), bean);
				 }
				 
				 //计算收益
				List<UserIncomeVerifyTemp> listTemp=Lists.newArrayList();
				 
				 for(String key:accountMap.keySet()){
					 
					 
					 Account bean=accountMap.get(key);
					 
					 if(bean.getCanUseCoins().compareTo(BigDecimal.ZERO)<1){
						 continue;
					 }
					 
					 CoinRateRecord config= coinRateRecordMap.get(bean.getCoinCode());
					 
					 if(null==config){
						 config=new CoinRateRecord();
						 config.setCoinDayRate(BigDecimal.ZERO);
						 config.setCoinYearRate(BigDecimal.ZERO);
						 config.setUsdtUnit(BigDecimal.ZERO);
						 
					 }
					 
					 UserIncomeVerifyTemp temp=new UserIncomeVerifyTemp();
					 temp.setUserId(bean.getUserId());
					 temp.setAccountId(bean.getId());
					 temp.setCoins(bean.getCanUseCoins());
					 //计算收益=计息资产*利率
					 
					 BigDecimal coinIncome=BigDecimal.ZERO;
					 if(null!=config){
						 SysCoinsDic dic= coinDicMap.get(bean.getCoinCode());
						 if(dic.getMinIncomeCount().compareTo(bean.getCanUseCoins())==-1){
							 coinIncome=bean.getCanUseCoins().multiply(config.getCoinDayRate());
						 }
					 }
					 
					 temp.setCoinIncome(coinIncome);
					 
					 temp.setCoinDayRate(config.getCoinDayRate());
					 temp.setCoinYearRate(config.getCoinYearRate());
					 temp.setCoinCode(bean.getCoinCode());
					 temp.setCountDate(new Date());
					 temp.setUsdtUnit(config.getUsdtUnit());
					
					 BigDecimal usdtIncome=BigDecimal.ZERO;
					 //计算换算成usdt的收益
					 if(null!=config){
						 usdtIncome=coinIncome.multiply(config.getUsdtUnit());
					 }
					 temp.setUsdtIncome(usdtIncome);
					 
					 temp.setIsSystemOperate(isSystemOperate);
					 temp.setOperateId(operateId);
					 temp.setOrderNo(UUID.randomUUID().toString().replace("-", "").toLowerCase());
					 temp.setGmtCreate(new Date());
					 listTemp.add(temp);
					 
					 //更新账户收益
					 
					 bean.setTodayIncome(coinIncome);
					 bean.setTotalIncome(bean.getTotalIncome().add(coinIncome));
					 
					 //计算总收益
					 
					 AssetTotal assetTotal= assetTotalDestMap.get(bean.getCoinCode());
					 
					 if(null==assetTotal){
						 assetTotal=new AssetTotal();
					 }
					 assetTotal.setCoinCode(bean.getCoinCode());
					 assetTotal.setCoins(assetTotal.getCoins().add(bean.getCoins()));
					 assetTotal.setTodayIncome(assetTotal.getTodayIncome().add(bean.getTodayIncome()));
					 assetTotal.setTotalIncome(assetTotal.getTotalIncome().add(bean.getTotalIncome()));
					 assetTotal.setGmtCreate(new Date());
					 assetTotal.setGmtModify(new Date());
					 
					 AssetTotal orgAssetTotal=assetTotalMap.get(bean.getCoinCode());
					 if(null!=orgAssetTotal){
						 assetTotal.setYesterdayCoins(orgAssetTotal.getYesterdayCoins());
					 }
					 assetTotalDestMap.put(bean.getCoinCode(), assetTotal);
					
					 
					 //计算每天总收益
					 
					 AssetIncomeRecord assetIncomeRecord=assetIncomeRecordMap.get(bean.getCoinCode());
					 
					 if(null==assetIncomeRecord){
						 assetIncomeRecord=new AssetIncomeRecord();
					 }
					 
					 assetIncomeRecord.setCoinCode(bean.getCoinCode());
					 assetIncomeRecord.setCoinIncome(assetIncomeRecord.getCoinIncome().add(bean.getTodayIncome()));
					 assetIncomeRecord.setUsdtInit(config.getUsdtUnit());
					 assetIncomeRecord.setUsdtIncome(assetIncomeRecord.getUsdtIncome().add(usdtIncome));
					 assetIncomeRecord.setCountDate(new Date());
					 assetIncomeRecord.setGmtCreate(new Date());
					 
					 assetIncomeRecordMap.put(bean.getCoinCode(), assetIncomeRecord);
					 
				 }
				 
				 
				 
				 
				 
				 
				 
				 //封装收益审核记录表
				 List<UserIncomeVerify> listUserIncomeVerify=Lists.newArrayList();
				 
				 for(UserIncomeVerifyTemp bean:listTemp){
					 
					 UserIncomeVerify userIncomeVerify=new UserIncomeVerify();
					 
					 userIncomeVerify.setUserId(bean.getUserId());
					 userIncomeVerify.setAccountId(bean.getAccountId());
					 userIncomeVerify.setCoins(bean.getCoins());
					 userIncomeVerify.setCoinIncome(bean.getCoinIncome());
					 
					 userIncomeVerify.setCoinDayRate(bean.getCoinDayRate());
					 userIncomeVerify.setCoinYearRate(bean.getCoinYearRate());
					 userIncomeVerify.setCoinCode(bean.getCoinCode());
					 userIncomeVerify.setCountDate(new Date());
					 userIncomeVerify.setUsdtUnit(bean.getUsdtUnit());
					
					 userIncomeVerify.setUsdtIncome(bean.getUsdtIncome());
					 
					 userIncomeVerify.setIsSystemOperate(isSystemOperate);
					 userIncomeVerify.setOperateId(operateId);
					 userIncomeVerify.setOrderNo(bean.getOrderNo());
					 userIncomeVerify.setGmtCreate(new Date());
					 listUserIncomeVerify.add(userIncomeVerify);
				 }
				 
				 //封装收益表
				 List<UserIncomeRecord> listUserIncomeRecord=Lists.newArrayList();
				 
				 for(UserIncomeVerify bean:listUserIncomeVerify){
					 
					 UserIncomeRecord userIncomeRecord=new UserIncomeRecord();
					 userIncomeRecord.setUserId(bean.getUserId());
					 userIncomeRecord.setAccountId(bean.getAccountId());
					 userIncomeRecord.setCoins(bean.getCoins());
					 userIncomeRecord.setCoinIncome(bean.getCoinIncome());
					 userIncomeRecord.setCoinDayRate(bean.getCoinDayRate());
					 userIncomeRecord.setCoinYearRate(bean.getCoinYearRate());
					 userIncomeRecord.setCoinCode(bean.getCoinCode());
					 userIncomeRecord.setUsdtUnit(bean.getUsdtUnit());
					 userIncomeRecord.setUsdtIncome(bean.getUsdtIncome());
					 userIncomeRecord.setBindNo(bean.getOrderNo());
					 userIncomeRecord.setGmtCreate(new Date());
					 listUserIncomeRecord.add(userIncomeRecord);			 
				 }
				 
				 //增加资产总收益
				 
				 assetTotalMapper.delAll();
				 
				 
				 listAssetTotal=Lists.newArrayList();
				 
				 for(String key:assetTotalDestMap.keySet()){
					 AssetTotal assetTotal= assetTotalDestMap.get(key);
					 listAssetTotal.add(assetTotal);
					 
				 }
				 assetTotalMapper.batchInsert(listAssetTotal);

				 //增加资产收益记录
				 assetIncomeRecordMapper.deleAssetIncomeRecord(date);
				 
				 List<AssetIncomeRecord> listAssetIncomeRecord=Lists.newArrayList();
				 
				 for(String key:assetIncomeRecordMap.keySet()){
					 AssetIncomeRecord assetIncomeRecord= assetIncomeRecordMap.get(key);
					 listAssetIncomeRecord.add(assetIncomeRecord);
					 
				 }
				 assetIncomeRecordMapper.batchInsert(listAssetIncomeRecord);
				 
				 //备份资产账户表
				 
				 //删除今日收益表
				 //删除今日审核记录表
				 //删除审核缓存表
				 
				 userIncomeRecordMapper.delUserIncomeRecordByDate(date);
				 userIncomeVerifyMapper.delUserIncomeVerifyByDate(date);
				 userIncomeVerifyTempMapper.truncateUserIncomeVerifyTemp();
				 
				 
				 //增加收益表
				 
				 int index=0;
				 
				 List<UserIncomeRecord> listUserIncomeRecordTemp=Lists.newArrayList();
				 for(UserIncomeRecord bean:listUserIncomeRecord){
					 index++;
					 listUserIncomeRecordTemp.add(bean);
					 if(index%1000==0){
						 userIncomeRecordMapper.batchInsert(listUserIncomeRecordTemp);
						 listUserIncomeRecordTemp.clear();
					 }
					 
				 }
				 
				 if(listUserIncomeRecordTemp.size()>0){
					 userIncomeRecordMapper.batchInsert(listUserIncomeRecordTemp);
					 listUserIncomeRecordTemp.clear();
				 }
				
				 
				 
				 //增加审核记录表
				 index=0;
				 List<UserIncomeVerify> listUserIncomeVerifyTemp=Lists.newArrayList();
				 
				 for(UserIncomeVerify bean:listUserIncomeVerify){
					 index++;
					 listUserIncomeVerifyTemp.add(bean);
					 if(index%1000==0){
						 userIncomeVerifyMapper.batchInsert(listUserIncomeVerifyTemp);
						 listUserIncomeVerifyTemp.clear();
					 }
					 
				 }
				 
				 if(listUserIncomeVerifyTemp.size()>0){
					 userIncomeVerifyMapper.batchInsert(listUserIncomeVerifyTemp);
					 listUserIncomeVerifyTemp.clear();
				 }
				 
				 //增加审核缓存表
				 index=0;
				List<UserIncomeVerifyTemp> listUserIncomeVerifyTemp2=Lists.newArrayList();
					
				 
				 for(UserIncomeVerifyTemp bean:listTemp){
					 index++;
					 listUserIncomeVerifyTemp2.add(bean);
					 
					 if(index%1000==0){
						 userIncomeVerifyTempMapper.batchInsert(listUserIncomeVerifyTemp2);
						 listUserIncomeVerifyTemp2.clear();
					 }
					 
				 }
				 
				 if(listUserIncomeVerifyTemp2.size()>0){
					 userIncomeVerifyTempMapper.batchInsert(listUserIncomeVerifyTemp2);
					 listUserIncomeVerifyTemp2.clear();
				 }
				 
				 //修改计息资产和计息收益
				 index=0;
				 List<Account> listAccountTemp=Lists.newArrayList();
				 for(String key:accountMap.keySet()){
					 index++;
					 listAccountTemp.add(accountMap.get(key));
					
					 if(index%1000==0){
						 accountMapper.batchUpdateAccountIncome(listAccountTemp);
						 listAccountTemp.clear();
					 }
					 
				 }
				 if(listAccountTemp.size()>0){
					 accountMapper.batchUpdateAccountIncome(listAccountTemp);
					 listAccountTemp.clear();
					 
				 }
				 //备份账户数据
				 //删除7天以前的历史数据
				 accountBackMapper.delAccountLastDay(-7);
				 
				 accountBackMapper.delAccountByDay(date);
				 
				 List<AccountBack> listAccountBack=Lists.newArrayList();
				 index=0;
				 for(Account bean:listAccount){
					 AccountBack dest=new AccountBack();
					 
					 BeanUtils.copyProperties(dest, bean);
					 dest.setCountDate(new Date());
					 dest.setAccountId(bean.getId());
					 listAccountBack.add(dest);
					 
					 if(index%1000==0){
						 accountBackMapper.batchInsert(listAccountBack);
						 listAccountBack.clear();
					 }
					 
				 }
				 if(listAccountBack.size()>0){
					 accountBackMapper.batchInsert(listAccountBack);
					 listAccountBack.clear();
				 }
				
				 return JsonResultHelp.buildSucc();
			}
			 	return JsonResultHelp.buildFail(RspCodeEnum.$0005);
			} catch (Exception e) {
				
				e.printStackTrace();
				logger.error(""+e);
				throw new RuntimeException("系统异常>>>"+e);
			}finally{
				RedissonManager.unlock(Constants.COUNT_INCOME_KEY);
			}
		
		
	}
	
	@Override
	@Transactional
	public JsonResult commitUserIncomeVerifyTemp(long userId){
		Date beginDate=new Date();
		try {
			String dateStr=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			
			//判断是否以及计算今日收益
			boolean flag=sysTaskJobMapper.checkJobIsRun(JobTypeEnum.COUNT_INCOME.getCode(), dateStr);
			
			if(flag){
				return JsonResultHelp.buildFail(RspCodeEnum.$2400);
			}
			
			//检测是否有数据
			int count=userIncomeVerifyTempMapper.getUserIncomeVerifyTempCount(new Date());
			
			if(count<=0){
				userIncomeVerifyTempMapper.truncateUserIncomeVerifyTemp();
				return JsonResultHelp.buildFail(RspCodeEnum.$2302);
			}
			
			
			Date date=new Date();
			
			 List<AccountBack> listAccount =accountBackMapper.queryAll(date);
			//获取昨日充值数据
			
			 Map<String,AccountBack> accountMap=Maps.newHashMap();
			 for(AccountBack bean:listAccount){
				 accountMap.put(bean.getUserId()+":"+bean.getAccountId()+":"+bean.getCoinCode(), bean);
			 }
			 
			 //计算收益
			List<UserIncomeVerifyTemp> listTemp=userIncomeVerifyTempMapper.getAll();
			 
			 Map<String,UserIncomeVerifyTemp> listTempMap=Maps.newHashMap();
			 for(UserIncomeVerifyTemp bean:listTemp){
				 listTempMap.put(bean.getUserId()+":"+bean.getAccountId()+":"+bean.getCoinCode(), bean);
			 }
			
			
			
			 for(String key:accountMap.keySet()){
				 
				 AccountBack bean=accountMap.get(key);
				 
				 UserIncomeVerifyTemp temp= listTempMap.get(key);
				 if(null==temp){
					 
					 bean.setTodayIncome(BigDecimal.ZERO);
				 }else{
					 bean.setTodayIncome(temp.getCoinIncome());
					 bean.setTotalIncome(bean.getTotalIncome().add(temp.getCoinIncome()));
				 }
				 
				 //更新账户收益
				
				 
			 
			 }
			 
			 //封装收益审核记录表
			 List<UserIncomeVerify> listUserIncomeVerify=Lists.newArrayList();
			 
			 for(UserIncomeVerifyTemp bean:listTemp){
				 
				 UserIncomeVerify userIncomeVerify=new UserIncomeVerify();
				 
				 userIncomeVerify.setUserId(bean.getUserId());
				 userIncomeVerify.setAccountId(bean.getAccountId());
				 userIncomeVerify.setCoins(bean.getCoins());
				 userIncomeVerify.setCoinIncome(bean.getCoinIncome());
				 
				 userIncomeVerify.setCoinDayRate(bean.getCoinDayRate());
				 userIncomeVerify.setCoinYearRate(bean.getCoinYearRate());
				 userIncomeVerify.setCoinCode(bean.getCoinCode());
				 userIncomeVerify.setCountDate(new Date());
				 userIncomeVerify.setUsdtUnit(bean.getUsdtUnit());
				
				 userIncomeVerify.setUsdtIncome(bean.getUsdtIncome());
				 
				 userIncomeVerify.setIsSystemOperate(YesOrNoEnum.NO.getCode());
				 userIncomeVerify.setOperateId(userId);
				 userIncomeVerify.setOrderNo(bean.getOrderNo());
				 userIncomeVerify.setGmtCreate(new Date());
				 listUserIncomeVerify.add(userIncomeVerify);
			 }
			 
			 //封装收益表
			 List<UserIncomeRecord> listUserIncomeRecord=Lists.newArrayList();
			 
			 for(UserIncomeVerify bean:listUserIncomeVerify){
				 
				 UserIncomeRecord userIncomeRecord=new UserIncomeRecord();
				 userIncomeRecord.setUserId(bean.getUserId());
				 userIncomeRecord.setAccountId(bean.getAccountId());
				 userIncomeRecord.setCoins(bean.getCoins());
				 userIncomeRecord.setCoinIncome(bean.getCoinIncome());
				 userIncomeRecord.setCoinDayRate(bean.getCoinDayRate());
				 userIncomeRecord.setCoinYearRate(bean.getCoinYearRate());
				 userIncomeRecord.setCoinCode(bean.getCoinCode());
				 userIncomeRecord.setUsdtUnit(bean.getUsdtUnit());
				 userIncomeRecord.setUsdtIncome(bean.getUsdtIncome());
				 userIncomeRecord.setBindNo(bean.getOrderNo());
				 userIncomeRecord.setGmtCreate(new Date());
				 listUserIncomeRecord.add(userIncomeRecord);			 
			 }
			 //备份资产账户表
			 
			 //删除今日收益表
			 //删除今日审核记录表
			 //删除审核缓存表
			 
			 userIncomeRecordMapper.delUserIncomeRecordByDate(date);
			 userIncomeVerifyMapper.delUserIncomeVerifyByDate(date);
			 userIncomeVerifyTempMapper.truncateUserIncomeVerifyTemp();
			 
			 //增加收益表
			 int index=0;
			 
			 List<UserIncomeRecord> listUserIncomeRecordTemp=Lists.newArrayList();
			 for(UserIncomeRecord bean:listUserIncomeRecord){
				 index++;
				 listUserIncomeRecordTemp.add(bean);
				 
				 if(index%1000==0){
					 userIncomeRecordMapper.batchInsert(listUserIncomeRecordTemp);
					 listUserIncomeRecordTemp.clear();
				 }
				 
			 }
			 
			 if(listUserIncomeRecordTemp.size()>0){
				 userIncomeRecordMapper.batchInsert(listUserIncomeRecordTemp);
				 listUserIncomeRecordTemp.clear();
			 }
			 
			 
			 //增加审核记录表
			 List<UserIncomeVerify> listUserIncomeVerifyTemp=Lists.newArrayList();
			 index=0;
			 for(UserIncomeVerify bean:listUserIncomeVerify){
				 index++;
				 listUserIncomeVerifyTemp.add(bean);
				 
				 if(index%1000==0){
					 userIncomeVerifyMapper.batchInsert(listUserIncomeVerifyTemp);
					 listUserIncomeVerifyTemp.clear();
				 }
				 
			 }
			 
			 if(listUserIncomeVerifyTemp.size()>0){
				 userIncomeVerifyMapper.batchInsert(listUserIncomeVerifyTemp);
				 listUserIncomeVerifyTemp.clear();
			 }
			 
			 
			 List<Account> listAllAccount= accountMapper.queryAll();
			 
			 Map<String,Account> listAllAccountMap=Maps.newHashMap();
			 
			 for(Account bean:listAllAccount){
				 listAllAccountMap.put(bean.getUserId()+":"+bean.getId()+":"+bean.getCoinCode(), bean);
			 }
			 
			 
			 
			 Map<String,AssetTotal> assetTotalDestMap=Maps.newHashMap();
		     List<AssetTotal> listAssetTotal=assetTotalMapper.queryAll();
		     Map<String,AssetTotal> assetTotalMap=Maps.newHashMap();
				for(AssetTotal bean:listAssetTotal){
					assetTotalMap.put(bean.getCoinCode(), bean);
				}
				
			 
			 //修改计息资产和计息收益
			 List<Account> listAccountTemp=Lists.newArrayList();
			 index =0;
			 for(String key:listAllAccountMap.keySet()){
				 index++;
				 
				 Account account=listAllAccountMap.get(key);
				 
				 AccountBack actBack= accountMap.get(key);
				 
				 if(null!=actBack){
					 
					 account.setTodayIncome(actBack.getTodayIncome());
					 account.setTotalIncome(actBack.getTotalIncome());
					 account.setCanUseCoins(actBack.getTodayIncome().add(actBack.getCanUseCoins()));
					 account.setTodayCoins(account.getCanUseCoins());
					 account.setCoins(account.getCoins().add(actBack.getTodayIncome()));
					 
				 }else{
					 account.setTodayIncome(BigDecimal.ZERO);
				 }
				 
				 listAccountTemp.add(account);
				
				 if(index%1000==0){
					 accountMapper.batchUpdateAccountAssetIncome(listAccountTemp);
					 listAccountTemp.clear();
				 }
				 
				 AssetTotal assetTotal=assetTotalDestMap.get(account.getCoinCode());
				 
				 if(null==assetTotal){
					 assetTotal=new AssetTotal();
				 }
				 
				 assetTotal.setCoinCode(account.getCoinCode());
				 assetTotal.setCoins(assetTotal.getCoins().add(account.getCoins()));
				 assetTotal.setTodayIncome(assetTotal.getTodayIncome().add(account.getTodayIncome()));
				 assetTotal.setTotalIncome(assetTotal.getTotalIncome().add(account.getTotalIncome()));
				 assetTotal.setGmtCreate(new Date());
				 assetTotal.setGmtModify(new Date());
				 
				 AssetTotal orgAssetTotal=assetTotalMap.get(account.getCoinCode());
				 if(null!=orgAssetTotal){
					 assetTotal.setYesterdayCoins(orgAssetTotal.getYesterdayCoins());
				 }
				 assetTotalDestMap.put(account.getCoinCode(), assetTotal);
				 
			 }
			 if(listAccountTemp.size()>0){
				 accountMapper.batchUpdateAccountAssetIncome(listAccountTemp);
				 listAccountTemp.clear();
			 }
			 
			 //增加资产总收益
			 
			 assetTotalMapper.delAll();
			 
			 
			 listAssetTotal=Lists.newArrayList();
			 
			 for(String key:assetTotalDestMap.keySet()){
				 AssetTotal assetTotal= assetTotalDestMap.get(key);
				 listAssetTotal.add(assetTotal);
				 
			 }
			 assetTotalMapper.batchInsert(listAssetTotal);
			 sysTaskJobMapper.insert(new SysTaskJob(beginDate,new Date(),JobTypeEnum.COUNT_INCOME.getCode(),YesOrNoEnum.YES.getCode(),""));
			 
			return JsonResultHelp.buildSucc();
		} catch (Exception e) {
			logger.error("{}",e);
			sysTaskJobMapper.insert(new SysTaskJob(beginDate,new Date(),JobTypeEnum.COUNT_INCOME.getCode(),YesOrNoEnum.NO.getCode(), e.getMessage()));
			return JsonResultHelp.buildFail();
		}
	}
	
	@Override
	public JsonResult clearUserAsset(long userId){
		
		 List<Account> list=accountMapper.queryUserAccount(userId);
		for(Account bean:list){
			assetService.clearAccountAsset(userId, bean.getId());
		}
		return JsonResultHelp.buildSucc();
	}
	
	@Transactional
	@Override
	public JsonResult clearAccountAsset(long userId,long accountId){
		
			Account account=accountMapper.selectByPrimaryKey(accountId);
			
			if(YesOrNoEnum.NO.getCode()== account.getStatus()){
				return JsonResultHelp.buildFail(RspCodeEnum.$2304);
			}
			
//			if(account.getCoins().compareTo(BigDecimal.ZERO)<1){
//				return JsonResultHelp.buildFail(RspCodeEnum.$2603);
//			}
			
			accountMapper.updateAccountStatus(accountId, YesOrNoEnum.NO.getCode());
			
			UserInfo user=userInfoMapper.selectByPrimaryKey(userId);
			
			String orderNo=UUID.randomUUID().toString().replace("-", "").toLowerCase();
			//增加交易流水
			
			CoinRecord record=new CoinRecord();
			record.setUserId(userId);
			record.setAccountId(account.getId());
			record.setCoins(account.getCoins());
			record.setCoinCode(account.getCoinCode());
			record.setTradeType(TradeTypeEnum.OUT.getType());
			record.setStatus(TradeStatusEnum.init.getStatus());
			record.setIsSystemOperate(YesOrNoEnum.YES.getCode());
			record.setTotalCoins(account.getCoins());
			record.setOperateUserId(-1l);
			record.setOrderNo(orderNo);
			record.setGmtCreate(new Date());
			record.setGmtModify(new Date());
			coinRecordMapper.insert(record);
					
			
			LbankResult<LbankTrade> ltrad=lbankUtil.sysTrade(user.getOpenId(), orderNo, orderNo, account.getCoinCode(), account.getCoins().toString());
			
			if(ltrad.isFullSuccess()){
				record.setStatus(TradeStatusEnum.SUCCESS.getStatus());
				record.setBindNo(ltrad.getObj().getBiz_number());
				record.setGmtModify(new Date());
				
			}else{
				
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
			
			
			//如果交易成功
			if(record.getStatus()==TradeStatusEnum.SUCCESS.getStatus()){
				
				//扣除流程
				/**
				 * 1.先从今日转入的扣除(btc_user_day_total_coin_record)
				 * 2.如果今天暂未处理计息收益，从昨日的扣除(btc_user_day_total_coin_record)
				 * 3.最后从计息资产里面扣除(btc_account)
				 * */
				UserDayTotalCoinRecord today=userDayTotalCoinRecordMapper.getUserDayTotalCoinRecord(userId, account.getId(), account.getCoinCode(), new Date());
				if(null!=today){
					
					Map<String, String> jstlMap=Maps.newHashMap();
					jstlMap.put("amount", today.getCoins().subtract(today.getCostAmount()).doubleValue()+"");
					String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					jstlMap.put("dateTime", dateTime);
					String jstl=StringUtil.replaceJstlMap(TemplateEnum.COST_AMOUNT.getModel(), jstlMap);
					today.setCostAmountDesc(today.getCostAmountDesc()+jstl);
					today.setCostAmount(today.getCostAmount().add(today.getCoins().subtract(today.getCostAmount())));
					userDayTotalCoinRecordMapper.updateByPrimaryKeySelective(today);
					
				}
				
					
					//判断是否以及计算可用资产
					String dateStr=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					
					boolean flag=sysTaskJobMapper.checkJobIsRun(JobTypeEnum.COUNT_USE_COINS.getCode(), dateStr);
					
					if(!flag){
						
						UserDayTotalCoinRecord yesterday=userDayTotalCoinRecordMapper.getUserDayTotalCoinRecord(userId, account.getId(), account.getCoinCode(), DateUtils.addDays(new Date(),-1));
						if(null!=yesterday){
							
							Map<String, String> jstlMap=Maps.newHashMap();
							jstlMap.put("amount", yesterday.getCoins().subtract(yesterday.getCostAmount()).doubleValue()+"");
							String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
							jstlMap.put("dateTime", dateTime);
							String jstl=StringUtil.replaceJstlMap(TemplateEnum.COST_AMOUNT.getModel(), jstlMap);
							yesterday.setCostAmountDesc(yesterday.getCostAmountDesc()+jstl);
							yesterday.setCostAmount(yesterday.getCostAmount().add(yesterday.getCoins().subtract(yesterday.getCostAmount())));
							userDayTotalCoinRecordMapper.updateByPrimaryKeySelective(yesterday);
						}
					}
					
					accountMapper.clearAccountAsset(account.getId());
				
			}
			//解锁账户
			accountMapper.updateAccountStatus(accountId, YesOrNoEnum.YES.getCode());
			return JsonResultHelp.buildSucc();
		
			
	}
	
	
	/**
	 * 计算可用资产
	 * @return
	 */
	@Override
	public JsonResult countCanUseAsset(){
		Date beginDate=new Date();
		String dateStr=new SimpleDateFormat("yyyy-MM-dd").format(beginDate);
		
		boolean flag=sysTaskJobMapper.checkJobIsRun(JobTypeEnum.COUNT_USE_COINS.getCode(), dateStr);
		if(flag){
			return JsonResultHelp.buildFail(RspCodeEnum.$2401);
		}
		
		try {
			//获取昨日充值数据
			
			 List<UserDayTotalCoinRecord> listUserDayTotalCoinRecord= userDayTotalCoinRecordMapper.queryUserDayTotalCoinRecord(DateUtils.addDays(new Date(), -1));
			
			//获取所有用户数据
			 
			 List<Account> listAccount=accountMapper.queryAll();
			 
			 Map<String,Account> accountMap=Maps.newHashMap();
			 for(Account bean:listAccount){
				 accountMap.put(bean.getUserId()+":"+bean.getId()+":"+bean.getCoinCode(), bean);
			 }
			 
			 //更新用户计息资产=(昨日投资资产+历史计息资产)
			 
			 for(UserDayTotalCoinRecord bean:listUserDayTotalCoinRecord){
				 String key=bean.getUserId()+":"+bean.getAccountId()+":"+bean.getCoinCode();
				 Account account= accountMap.get(key);
				 if(null!=account){
					 //计算新的计息资产
					 account.setCanUseCoins(bean.getCoins().subtract(bean.getCostAmount()).add(account.getCanUseCoins()));
					 account.setTodayCoins(account.getCanUseCoins());
					 accountMap.put(key, account);
				 }
			 }
			 
			//修改计息资产
			 int index=0;
			 List<Account> listAccountTemp=Lists.newArrayList();
			 for(String key:accountMap.keySet()){
				 index++;
				 listAccountTemp.add(accountMap.get(key));
				
				 if(index%1000==0){
					 accountMapper.batchUpdateAccountIncome(listAccountTemp);
					 listAccountTemp.clear();
				 }
				 
			 }
			 if(listAccountTemp.size()>0){
				 accountMapper.batchUpdateAccountIncome(listAccountTemp);
				 listAccountTemp.clear();
				 
			 }
			 
			 sysTaskJobMapper.insert(new SysTaskJob(beginDate,new Date(),JobTypeEnum.COUNT_USE_COINS.getCode(),YesOrNoEnum.YES.getCode(), ""));
		} catch (Exception e) {
			 logger.error(""+e);
			 sysTaskJobMapper.insert(new SysTaskJob(beginDate,new Date(),JobTypeEnum.COUNT_USE_COINS.getCode(),YesOrNoEnum.NO.getCode(), e.getMessage()));
		}
		
		 return JsonResultHelp.buildSucc();
		 
	}
	
	
	
}
