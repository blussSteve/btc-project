package com.btc.controller.admin;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.AssetAddRecord;
import com.btc.model.AssetAddRecordTemp;
import com.btc.service.AssetAddService;

@RequestMapping("/admin/asset")
@Controller
public class ShareAssetController {

	
	@Autowired
	private AssetAddService assetAddService;
	
	@RequestMapping(value="/shareAsset")
	public String toShareAsset(){
		
		return "shareAssetRecord";
	}
	
	@RequestMapping(value="/shareAssetList")
	@ResponseBody
	public Page<AssetAddRecord> shareAssetList(HttpServletRequest req,String userName,String openId,String coinCode){
		Page<AssetAddRecord> page=PageUtil.getPage(req);
		AssetAddRecord record=new AssetAddRecord();
		record.setUserName(userName);
		record.setOpenId(openId);
		record.setCoinCode(coinCode);
		return assetAddService.queryAssetAddRecord(page, record);
	}
	
	
	@RequestMapping(value="/shareAssetTemp")
	public String toShareAssetTemp(){
		
		return "shareAssetTemp";
	}
	
	@RequestMapping(value="/shareAssetTempList")
	@ResponseBody
	public Page<AssetAddRecordTemp> shareAssetTempList(HttpServletRequest req,String userName,String openId,String coinCode,Integer isExist){
		Page<AssetAddRecordTemp> page=PageUtil.getPage(req);
		AssetAddRecordTemp record=new AssetAddRecordTemp();
		record.setIsExist(isExist);
		record.setOpenId(openId);
		record.setCoinCode(coinCode);
		return assetAddService.queryAssetAddRecordTemp(page, record);
	}
	
	@RequestMapping(value="/importAssetFile")
	@ResponseBody
	public JsonResult uploadAssetFile(HttpServletRequest req) throws FileNotFoundException{
		
		return assetAddService.importAsset(req);
	}
	
	@RequestMapping(value="/commitAsset")
	@ResponseBody
	public JsonResult commitAsset(){
		
		return assetAddService.shareAsset();
		
	}
}
