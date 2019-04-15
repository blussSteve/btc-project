package com.btc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.CoinRecord;
import com.btc.model.UserInfo;
import com.btc.service.UserService;

@RequestMapping(value="/front/auth/asset")
@RestController
public class AssetController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 获取资产信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/assetInfo")
	public JsonResult getAssetInfo(HttpServletRequest req){
		return userService.getAssetInfo(getUserId(req));
	}
	
	/**
	 * 获取资产列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/assetList")
	public JsonResult getAssetList(HttpServletRequest req){
		
		return userService.getAssetList(getUserId(req));
	}

	/**
	 * 获取交易记录
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/tradeRecord")
	public JsonResult queryCoinRecord(HttpServletRequest req){
		
		CoinRecord record=new CoinRecord();
		record.setUserId(getUserId(req));
		Page<Map<String, String>> page=PageUtil.getPage(req);
		return JsonResultHelp.buildSucc(userService.queryCoinRecord(page, record));
	}

	
	/**
	 * 资产交易
	 * @param req
	 * @param openId
	 * @param userId
	 * @param amount
	 * @param assetCode
	 * @param tradeType
	 * @return
	 */
	@RequestMapping(value="/trade")
	public JsonResult trade(HttpServletRequest req, String amount,	String assetCode, int tradeType){
		UserInfo user=getUserInfo(req);
		return userService.trade(user.getOpenId(), user.getId(), amount, assetCode, tradeType);
		
	}
}
