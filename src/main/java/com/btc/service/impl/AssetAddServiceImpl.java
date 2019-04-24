package com.btc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.btc.global.enums.SysConfigEnum;
import com.btc.global.enums.YesOrNoEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.mapper.AccountMapper;
import com.btc.mapper.AssetAddRecordMapper;
import com.btc.mapper.AssetAddRecordTempMapper;
import com.btc.mapper.UserDayTotalCoinRecordMapper;
import com.btc.mapper.UserInfoMapper;
import com.btc.mapper.admin.SysCoinsDicMapper;
import com.btc.model.Account;
import com.btc.model.AssetAddRecord;
import com.btc.model.AssetAddRecordTemp;
import com.btc.model.UserDayTotalCoinRecord;
import com.btc.model.UserInfo;
import com.btc.model.admin.SysCoinsDic;
import com.btc.pojo.excel.AssetModel;
import com.btc.service.AssetAddService;
import com.btc.service.RedisService;
import com.btc.util.Constants;
import com.btc.util.FileUtil;
import com.btc.util.ObjectUtil;
import com.btc.util.excel.AssetListener;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class AssetAddServiceImpl implements AssetAddService{
	
	private final static Logger logger=LoggerFactory.getLogger(AssetAddServiceImpl.class);

	@Autowired
	private FileUtil fileUtil;
	
	@Autowired
	private AssetAddRecordMapper assetAddRecordMapper;
	
	@Autowired
	private AssetAddRecordTempMapper assetAddRecordTempMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private UserDayTotalCoinRecordMapper userDayTotalCoinRecordMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private SysCoinsDicMapper sysCoinsDicMapper;
	
	 private static ReentrantLock lock = new ReentrantLock(true);
	/**
	 * 资产分发流水
	 * @param page
	 * @param record
	 * @return
	 */
	@Override
	public Page<AssetAddRecord> queryAssetAddRecord(Page<AssetAddRecord> page,AssetAddRecord record){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(record);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<AssetAddRecord> list=assetAddRecordMapper.queryAssetAddRecord(params);
		 	int count=assetAddRecordMapper.queryAssetAddRecordCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	
	 
	/**
	 * 资产分发缓存记录
	 * @param page
	 * @param record
	 * @return
	 */
	@Override
	public Page<AssetAddRecordTemp> queryAssetAddRecordTemp(Page<AssetAddRecordTemp> page,AssetAddRecordTemp record){
		
		 Map<String,Object> params= ObjectUtil.bean2Map(record);
		 
		 	if(!StringUtils.isEmpty(page.getOrderBy())){
				params.put("orderBy", page.getOrderBy());
				params.put("order", page.getOrder());
			}else{
				params.put("orderBy", "id");
				params.put("order", "desc");
			}
		 	params=PageUtil.parsePage(params, page);
			 
		 	List<AssetAddRecordTemp> list=assetAddRecordTempMapper.queryAssetAddRecordTemp(params);
		 	int count=assetAddRecordTempMapper.queryAssetAddRecordTempCount(params);
		 	page.setRows(list);
		 	page.setTotal(count);
		 	return page;
	}
	
	

	/**
	 * 资产上传
	 */
	@Override
	public JsonResult importAsset(HttpServletRequest req) throws FileNotFoundException{
		
		
		 MultipartFile file= ((MultipartHttpServletRequest) req).getFile("file");
		 if(file.isEmpty()){
			 return JsonResultHelp.buildFail(RspCodeEnum.$1102);
		 }
		 String originalFilename = file.getOriginalFilename();
		
		ExcelTypeEnum excelTypeEnum=null;
		if(originalFilename.contains(ExcelTypeEnum.XLS.getValue())){
			 excelTypeEnum=ExcelTypeEnum.XLS;
		}
		if(originalFilename.contains(ExcelTypeEnum.XLSX.getValue())){
			 excelTypeEnum=ExcelTypeEnum.XLSX;
		}
		if(null==excelTypeEnum){

			return JsonResultHelp.buildFail(RspCodeEnum.$1104);
		}
		 String newFileName="asset_"+System.currentTimeMillis();
		 String fileUrl=fileUtil.uploadFile(file,newFileName);
		 String path=fileUtil.getFilePathByUrl(fileUrl);
		
		InputStream inputStream=new FileInputStream(new File(path));
	        try {
	            // 解析每行结果在listener中处理
	        	AssetListener<AssetModel> listener = new AssetListener<AssetModel>();

	            ExcelReader excelReader = new ExcelReader(inputStream, excelTypeEnum, null, listener);
	            excelReader.read(new Sheet(1, 0, AssetModel.class));
	            
	            if(listener.getDatas().size()==0){
	            	return JsonResultHelp.buildFail(RspCodeEnum.$2305);
	            }
	           
	           if(saveAssetData(listener.getDatas())){
	        	   
	        	   return JsonResultHelp.buildSucc();
	           }
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } finally {
	            try {
	                inputStream.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return JsonResultHelp.buildFail(RspCodeEnum.$2305);
	
	}
	
	/**
	 * 保存资产缓存数据
	 * @param listAssetModel
	 * @return
	 */
	private boolean saveAssetData(List<AssetModel> listAssetModel){
		
		//1.获取openid不存在的数据
		List<String> openIds=Lists.newArrayList();
		List<String> bindNos=Lists.newArrayList();
		for(AssetModel bean:listAssetModel){
			openIds.add(bean.getOpenId());
			bindNos.add(bean.getOrderNo());
		}
		
		List<SysCoinsDic> listCoinCode=sysCoinsDicMapper.queryAllCoins();
		
		Map<String,SysCoinsDic> codeMap=Maps.newHashMap();
		for(SysCoinsDic bean:listCoinCode){
			codeMap.put(bean.getCoinCode(), bean);
		}
		
		List<String> listBindNo=assetAddRecordMapper.queryBindNos(bindNos);
		
		List<String> listOpenId=userInfoMapper.queryInOpenId(openIds);
		
		List<AssetAddRecordTemp> lists=Lists.newArrayList();
		
		for(AssetModel bean:listAssetModel){
			
			AssetAddRecordTemp temp=new AssetAddRecordTemp();
			
			temp.setCoinCode(bean.getCoinCode().toLowerCase());
			temp.setCoins(bean.getCoins());
			temp.setGmtCreate(new Date());
			temp.setGmtModify(new Date());
			temp.setOpenId(bean.getOpenId());
			temp.setOrgTotalCoins(BigDecimal.ZERO);
			temp.setBindNo(bean.getOrderNo());
			
			if(listOpenId.contains(bean.getOpenId())&&codeMap.containsKey(temp.getCoinCode())){
				if(listBindNo.contains(bean.getOrderNo())){
					temp.setIsExist(YesOrNoEnum.NO.getCode());
				}else{
					temp.setIsExist(YesOrNoEnum.YES.getCode());
				}
			}else{
				temp.setIsExist(YesOrNoEnum.NO.getCode());
			}
			lists.add(temp);
			
		}
		
		
		List<AssetAddRecordTemp> listTemp=Lists.newArrayList();
		
		assetAddRecordTempMapper.truncateAssetAddRecordTemp();//清空缓存表
	
		for(AssetAddRecordTemp bean:lists){
			listTemp.add(bean);
			
			if(listTemp.size()%1000==0){
				assetAddRecordTempMapper.batchInsert(listTemp);
				listTemp.clear();
				
			}
			
		}
		
		if(listTemp.size()>0){
			assetAddRecordTempMapper.batchInsert(listTemp);
			listTemp.clear();
		}
		
		return true;
		
	}
	
	/**
	 * 分发资产
	 * @return
	 */
	@Transactional
	@Override
	public JsonResult shareAsset(){
	 
		try {
			lock.lock();
			//锁住交易
			redisService.hset(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TRADE.getKey(),YesOrNoEnum.NO.getCode()+"");
			
			List<AssetAddRecordTemp> list= assetAddRecordTempMapper.queryAll();
			
			if(list.size()<=0){
				return JsonResultHelp.buildFail(RspCodeEnum.$2306);
			}
			
			List<String> openIds=Lists.newArrayList();
			for(AssetAddRecordTemp bean:list){
				openIds.add(bean.getOpenId());
			}
			
			
			List<Account> listAccount=accountMapper.queryAccountInOpenIds(openIds);
			
			//解析成map
			Map<String,Account> accountMap=Maps.newHashMap();
			for(Account bean:listAccount){
				accountMap.put(bean.getOpenId()+":"+bean.getCoinCode(), bean);
			}
			
			//存在的账户
			List<Account> listHasAccount=Lists.newArrayList();
			
			Map<String,Account> accountHasMap=Maps.newHashMap();
			
			//不存在的账户
			List<Account> listNoHasAccount=Lists.newArrayList();
			
			Map<String,Account> accountNoHasMap=Maps.newHashMap();
			
			List<AssetAddRecord> listAssetAddRecord=Lists.newArrayList();
			
			
			List<UserInfo> listUser=userInfoMapper.queryUserInOpenId(openIds);
			//解析成map
			Map<String,UserInfo> userMap=Maps.newHashMap();
			for(UserInfo bean:listUser){
				userMap.put(bean.getOpenId(), bean);
			}
			
			
			
			//封装资产分发数据、资产账户数据
			for(AssetAddRecordTemp bean:list){
				
				if(bean.getIsExist()==YesOrNoEnum.NO.getCode()){
					continue;
				}
				
				AssetAddRecord assetAddRecord=new AssetAddRecord();
				
				BeanUtils.copyProperties(assetAddRecord, bean);
				
				assetAddRecord.setGmtCreate(new Date());
				assetAddRecord.setGmtModify(new Date());
				
				Account act=accountMap.get(bean.getOpenId()+":"+bean.getCoinCode());
				
				if(null!=act){
					assetAddRecord.setOrgTotalCoins(act.getCoins());
				}else{
					assetAddRecord.setOrgTotalCoins(BigDecimal.ZERO);
				}
				assetAddRecord.setBindNo(bean.getBindNo());
				listAssetAddRecord.add(assetAddRecord);
				
				Account account=new Account();
				
				account.setCoins(bean.getCoins());
				account.setOpenId(bean.getOpenId());
				account.setCoinCode(bean.getCoinCode());
				
				if(null!=act){
					account.setId(act.getId());
					account.setUserId(act.getUserId());
					Account hasAccount=accountHasMap.get(bean.getOpenId()+":"+bean.getCoinCode());
					
					if(null!=hasAccount){
						hasAccount.setCoins(hasAccount.getCoins().add(account.getCoins()));
					}
					
					accountHasMap.put(bean.getOpenId()+":"+bean.getCoinCode(), account);
					
				}else{
					UserInfo user= userMap.get(bean.getOpenId());
					if(null!=user){
						account.setUserId(user.getId());
						account.setCanUseCoins(BigDecimal.ZERO);
						account.setTodayIncome(BigDecimal.ZERO);
						account.setTotalIncome(BigDecimal.ZERO);
						account.setStatus(YesOrNoEnum.NO.getCode());//资产冻结中
						account.setGmtCreate(new Date());
						account.setGmtModify(new Date());
						account.setTodayCoins(BigDecimal.ZERO);
						
						Account nohasAccount=accountNoHasMap.get(bean.getOpenId()+":"+bean.getCoinCode());
						if(null!=nohasAccount){
							nohasAccount.setCoins(nohasAccount.getCoins().add(account.getCoins()));
						}
						accountNoHasMap.put(bean.getOpenId()+":"+bean.getCoinCode(), account);
					}
					
					
				}
				
			}
			
			for(String key:accountHasMap.keySet()){
				listHasAccount.add(accountHasMap.get(key));
				
			}
			
			for(String key:accountNoHasMap.keySet()){
				listNoHasAccount.add(accountNoHasMap.get(key));
				
			}
			
			
			if(listHasAccount.size()>0){
				//更新账户总资产
				accountMapper.batchUpdateAccountAssetCoins(listHasAccount);
			}
		
			if(listNoHasAccount.size()>0){
				//新增资产
				accountMapper.batchInsert(listNoHasAccount);
			}
		
			//
			
			openIds.clear();
			for(Account bean:listNoHasAccount){
				openIds.add(bean.getOpenId());
			}
			
			List<Account> newListAccount=Lists.newArrayList();
			if(openIds.size()>0){
				 newListAccount=accountMapper.queryAccountInOpenIds(openIds);
			}
			
			
			
			List<Account> allAccountList=Lists.newArrayList();
			allAccountList.addAll(newListAccount);
			allAccountList.addAll(listHasAccount);
			
			List<String> accountIds=Lists.newArrayList();
			for(Account bean:listHasAccount){
				accountIds.add(bean.getId()+"");
			}
			
			
			Map<String,Account> allAccountMap=Maps.newHashMap();
			
			for(Account bean:allAccountList){
				allAccountMap.put(bean.getId()+"",bean);
			}
			
			if(accountIds.size()>0){
				  List<UserDayTotalCoinRecord>  listUserDayTotalCoinRecord=userDayTotalCoinRecordMapper.queryUserDayTotalCoinRecordByAccountIds(new Date(), accountIds);
			      for(UserDayTotalCoinRecord bean:listUserDayTotalCoinRecord){
				   
				   if(allAccountMap.containsKey(bean.getAccountId()+"")){
					   bean.setCoins(allAccountMap.get(bean.getAccountId()+"").getCoins());
					   
				   }
				   allAccountMap.remove(bean.getAccountId()+"");
			    }
			      if(listUserDayTotalCoinRecord.size()>0){
			    	  userDayTotalCoinRecordMapper.batchUpdateUserDayTotalCoin(listUserDayTotalCoinRecord);  
			      }
			    
			}
		 	
		   
		
		   
		   List<UserDayTotalCoinRecord> newListUserDayTotalCoinRecord=Lists.newArrayList();
		   for(String key:allAccountMap.keySet()){
			   Account act= allAccountMap.get(key);
			   UserDayTotalCoinRecord dayCoin=new UserDayTotalCoinRecord();
				dayCoin.setUserId(act.getUserId());
				dayCoin.setAccountId(act.getId());
				dayCoin.setCoinCode(act.getCoinCode());
				dayCoin.setCoins(act.getCoins());
				dayCoin.setCountDate(new Date());
				dayCoin.setGmtCreate(new Date());
				dayCoin.setGmtModify(new Date());
				dayCoin.setCostAmount(BigDecimal.ZERO);
				dayCoin.setCostAmountDesc("");
			   
			   newListUserDayTotalCoinRecord.add(dayCoin);
		   }
		 //将分发到资产数据加到今日新增资产里面
		   
		   if(newListUserDayTotalCoinRecord.size()>0){
			   userDayTotalCoinRecordMapper.batchInsert(newListUserDayTotalCoinRecord);
		   }
		   //增加流水记录
		   
		   List<AssetAddRecord> listAssetAddRecordTemp=Lists.newArrayList();
		   
		   for(AssetAddRecord bean:listAssetAddRecord){
			   listAssetAddRecordTemp.add(bean);
			   if(listAssetAddRecordTemp.size()%1000==0){
				   assetAddRecordMapper.batchInsert(listAssetAddRecordTemp);
				   listAssetAddRecordTemp.clear();
			   }
		   }
		   if(listAssetAddRecordTemp.size()>0){
			   assetAddRecordMapper.batchInsert(listAssetAddRecordTemp);
			   listAssetAddRecordTemp.clear();
		   }
		  
		   //清除记录
		   assetAddRecordTempMapper.truncateAssetAddRecordTemp();
		   return JsonResultHelp.buildSucc();
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error("{}",e);
			 throw new RuntimeException("资产分发失败......");
		}finally{
			redisService.hset(Constants.SYS_DIC_CACHE, SysConfigEnum.IS_OPEN_ASSET_TRADE.getKey(),YesOrNoEnum.YES.getCode()+"");
			lock.unlock();
		}
		
		
	}
}
