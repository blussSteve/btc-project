package com.btc.service.impl.admin;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btc.global.enums.YesOrNoEnum;
import com.btc.global.enums.admin.AdminRoleEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.mapper.AccountMapper;
import com.btc.mapper.AssetIncomeRecordMapper;
import com.btc.mapper.AssetTotalMapper;
import com.btc.mapper.CoinRateRecordMapper;
import com.btc.mapper.admin.SysCoinsDicMapper;
import com.btc.mapper.admin.SystemFirstItemMapper;
import com.btc.mapper.admin.SystemSecondItemMapper;
import com.btc.model.AssetIncomeRecord;
import com.btc.model.AssetTotal;
import com.btc.model.CoinRateRecord;
import com.btc.model.admin.SysCoinsDic;
import com.btc.model.admin.SystemFirstItem;
import com.btc.model.admin.SystemSecondItem;
import com.btc.model.admin.UUser;
import com.btc.service.SystemService;
import com.btc.util.Constants;
import com.btc.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class SystemServiceImpl implements SystemService {
	@Autowired
	private SystemSecondItemMapper systemSecondItemMapper;
	@Autowired
	private SystemFirstItemMapper systemFirstItemMapper;

	@Autowired
	private SysCoinsDicMapper  sysCoinsDicMapper;
	
	@Autowired
	private CoinRateRecordMapper coinRateRecordMapper;
	
	@Autowired
	private AssetTotalMapper assetTotalMapper;
	
	@Autowired
	private AssetIncomeRecordMapper assetIncomeRecordMapper;
	
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public List<Map<String, Object>> listItem(HttpServletRequest request, HttpServletResponse response) {
		//1.获取管理员级别
		HttpSession session=request.getSession();
	    UUser user=(UUser)session.getAttribute(Constants.AUTH_USER);	
		List<SystemFirstItem>  listFirstItem=null;
		//如果是管理员用户
		if(AdminRoleEnum.SUP_ADMIN.getType()==user.getLevel()){
			listFirstItem=systemFirstItemMapper.getListFirst();
		}else{
			listFirstItem=systemFirstItemMapper.getListFirstByUserId(user.getId());
		}
		
		
		//2.查找符合级别已开启的一级菜单
		List<Map<String,Object>> listMap=new ArrayList<>();
		for (SystemFirstItem list:listFirstItem) {
			Map<String,Object> map1=new HashMap<>();
			map1.put("itemName",list.getItemName());
			map1.put("url",list.getUrl());
			map1.put("itemId", list.getId());
			List<Map<String,Object>> listMap2=new ArrayList<>();
			//3.根据一级菜单查找符合级别已开启的二级菜单
			List<SystemSecondItem>  listSecondItem=systemSecondItemMapper.getListSecondItem(list.getId());
			List<Map<String,Object>> listSecond=new ArrayList<>();
			for (SystemSecondItem systemSecondItem:listSecondItem) {
				Map<String,Object> map=new HashMap<>();
				map.put("itemName",systemSecondItem.getItemName());
				map.put("url",systemSecondItem.getUrl());
				map.put("itemId",systemSecondItem.getId());
				listSecond.add(map);
			}
			map1.put("totalItem",listSecond);
			listMap.add(map1);
		}
		return listMap;
	}

	@Override
	public List<SystemFirstItem> mainlistItem(HttpServletRequest request, HttpServletResponse response) {
		//1.获取管理员级别
		HttpSession session=request.getSession();
		 UUser user=(UUser)session.getAttribute(Constants.AUTH_USER);
		List<SystemFirstItem>  listFirstItem=null;
		//如果是管理员用户
		if(AdminRoleEnum.SUP_ADMIN.getType()==user.getLevel()){
			listFirstItem=systemFirstItemMapper.getListFirst();
		}else{
			listFirstItem=systemFirstItemMapper.getListFirstByUserId(user.getId());
		}
		return listFirstItem;
	}
	
	@Override
	public List<SysCoinsDic> queryAllCoins(){
		return sysCoinsDicMapper.queryAllCoins();
	}
	
	@Override
	public Page<SysCoinsDic> querySysCoinsDic(Page<SysCoinsDic> page,SysCoinsDic dic){
		   Map<String,Object> params= ObjectUtil.bean2Map(dic);
		   params = PageUtil.parsePage(params, page);
		   List<SysCoinsDic> list=sysCoinsDicMapper.querySysCoinsDic(params);
		   int count=sysCoinsDicMapper.getSysCoinsCount(params);
		   page.setRows(list);
		   page.setTotal(count);
		   return page;
	}
	@Override
	public JsonResult saveOrUpdateSysCoinsDic(SysCoinsDic dic){
	
		if(dic.getId()!=null){
			SysCoinsDic org=sysCoinsDicMapper.getCoinsCodeDicByCode(dic.getCoinCode());
			if(org==null||org.getCoinCode().equals(dic.getCoinCode())){
				sysCoinsDicMapper.updateByPrimaryKeySelective(dic);
			}
		}else{
			boolean flag=sysCoinsDicMapper.checkCoinsCodeIsExist(dic.getCoinCode());
			if(flag){
				return JsonResultHelp.buildFail(RspCodeEnum.$2300);
			}
			dic.setGmtCreate(new Date());
			dic.setIsDel(YesOrNoEnum.NO.getCode());
			sysCoinsDicMapper.insert(dic);
		}
		return JsonResultHelp.buildSucc();
		
	}
	@Override
	public JsonResult updateSysCoinsDicStatus(long id,int status){
		SysCoinsDic dic=new SysCoinsDic();
		dic.setStatus(status);
		dic.setId(id);
		sysCoinsDicMapper.updateByPrimaryKeySelective(dic);
		return JsonResultHelp.buildSucc();
	}
	
	@Override
	public JsonResult delSysCoinsDic(long dicId){
		SysCoinsDic sysCoinsDic=sysCoinsDicMapper.selectByPrimaryKey(dicId);
		
		BigDecimal coins= accountMapper.getTotalCoins(sysCoinsDic.getCoinCode());
		
		if(coins.compareTo(BigDecimal.ZERO)==1){
			return JsonResultHelp.buildFail(RspCodeEnum.$2308);
		}
		
		sysCoinsDicMapper.setIsDel(dicId);
		return JsonResultHelp.buildSucc();
	}
	@Override
	public JsonResult checkCoinCodeIsExist(String coinCode){
		
		boolean flag=sysCoinsDicMapper.checkCoinsCodeIsExist(coinCode);
		if(flag){
			return JsonResultHelp.buildSucc(true);
		}
		return JsonResultHelp.buildSucc(false);
	}
	
	/**
	 * 获取利率分页信息
	 * @param page
	 * @param coinRateRecord
	 * @return
	 */
	@Override
	public Page<CoinRateRecord> queryCoinRate(Page<CoinRateRecord> page,CoinRateRecord coinRateRecord){
		 Map<String,Object> params= ObjectUtil.bean2Map(coinRateRecord);
		 
	 	if(!StringUtils.isEmpty(page.getOrderBy())){
			params.put("orderBy", page.getOrderBy());
			params.put("order", page.getOrder());
		}else{
			params.put("orderBy", "gmt_create");
			params.put("order", "desc");
		}
	 	params=PageUtil.parsePage(params, page);
		 
	 	List<CoinRateRecord> list=coinRateRecordMapper.queryCoinRate(params);
	 	int count=coinRateRecordMapper.getCoinRateCount(params);
	 	page.setRows(list);
	 	page.setTotal(count);
	 	return page;
	}
	@Override
	public JsonResult saveOrUpdateCoinRate(CoinRateRecord coinRateRecord){
		
		if(coinRateRecord.getId()!=null){
			
			
			CoinRateRecord org=coinRateRecordMapper.getCoinRate(coinRateRecord.getCoinCode(), coinRateRecord.getApplyDate());
			
			if(org!=null&&coinRateRecord.getId()!=org.getId()){
				
				return JsonResultHelp.buildFail(RspCodeEnum.$2301);
			}
			
			coinRateRecord.setGmtModify(new Date());
			coinRateRecordMapper.updateByPrimaryKeySelective(coinRateRecord);
			
		}else{
			
			coinRateRecord.setGmtCreate(new Date());
			coinRateRecord.setGmtModify(new Date());
			
			CoinRateRecord org=coinRateRecordMapper.getCoinRate(coinRateRecord.getCoinCode(), coinRateRecord.getApplyDate());
			if(org!=null){
				return JsonResultHelp.buildFail(RspCodeEnum.$2301);
				
			}
			coinRateRecordMapper.insert(coinRateRecord);
		}
		
		return JsonResultHelp.buildSucc();
	}
	@Override
	public JsonResult checkCoinRateIsExist(String code,String dateStr){
		
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		CoinRateRecord record=coinRateRecordMapper.getCoinRate(code, date);
		
		if(record!=null){
			return JsonResultHelp.buildSucc(true);
		}
		return JsonResultHelp.buildSucc(false);
	}
	@Override
	public JsonResult delCoinRate(long id){
		coinRateRecordMapper.deleteByPrimaryKey(id);
		return JsonResultHelp.buildSucc();
	}
	
	
	@Override
	public Page<AssetTotal> queryAssetTotal(Page<AssetTotal> page){
		 Map<String,Object> params= Maps.newHashMap();;
		 
	 	if(!StringUtils.isEmpty(page.getOrderBy())){
			params.put("orderBy", page.getOrderBy());
			params.put("order", page.getOrder());
		}else{
			params.put("orderBy", "id");
			params.put("order", "asc");
		}
	 	params=PageUtil.parsePage(params, page);
		 
	 	List<AssetTotal> list=assetTotalMapper.queryCoinAssetTotal(params);
	 	int count=assetTotalMapper.getCoinAssetTotalCount(params);
	 	page.setRows(list);
	 	page.setTotal(count);
	 	return page;
	}
	
	/**
	 * 获取收益k线图
	 * @return
	 */
	@Override
	public JsonResult queryAssetTotalKline(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		//最近30天收益
		List<AssetIncomeRecord> list=assetIncomeRecordMapper.queryAssetIncomeRecord(-30);
		
		List<String> listDate=Lists.newArrayList();
		for(int i=30;i>0;i--){
			listDate.add(format.format(DateUtils.addDays(date,-i )));
		}
		//按照coincode分组
		
		Map<String,List<AssetIncomeRecord>> mapCode=Maps.newHashMap();
		
		for(AssetIncomeRecord bean:list){
			
			List<AssetIncomeRecord> listCodeIncome=mapCode.get(bean.getCoinCode());
			
			if(null==listCodeIncome){
				listCodeIncome=Lists.newArrayList();
			}
			listCodeIncome.add(bean);
			
			mapCode.put(bean.getCoinCode(), listCodeIncome);
			
		}
		
		Map<String,List<Double>> allMapIncome=Maps.newTreeMap();
		for(String key:mapCode.keySet()){
			
			List<AssetIncomeRecord> listCodeIncome=mapCode.get(key);
			
			Map<String,AssetIncomeRecord> mapDate=Maps.newHashMap();
			//日期分组 
			for(AssetIncomeRecord bean:listCodeIncome){
				mapDate.put(format.format(bean.getCountDate()), bean);
			}
			
			List<Double> listIncome=Lists.newArrayList();
			for(String d:listDate){
				
				if(mapDate.containsKey(d)){
					listIncome.add(mapDate.get(d).getCoinIncome().doubleValue());
				}else{
					listIncome.add(0.0);
				}
			}
			
			allMapIncome.put(key, listIncome);
		}
		
		
		Map<String,Object> dataMap=Maps.newHashMap();
		
		dataMap.put("date", listDate);
		dataMap.put("data", allMapIncome);
		
		return JsonResultHelp.buildSucc(dataMap);
		
		
	}
	
}
