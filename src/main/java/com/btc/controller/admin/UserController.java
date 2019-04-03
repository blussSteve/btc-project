package com.btc.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.Account;
import com.btc.model.UserInfo;
import com.btc.service.AssetService;
import com.btc.service.UserService;

@RequestMapping("/admin/user")
@Controller
public class UserController {
	
	@Autowired
	private UserService userInfoService;
	
	@Autowired
	private AssetService assetService;
	
	@RequestMapping("/index")
	public String index(){
		return "userInfo";
	}
	@RequestMapping("/account/index")
	public String accountIndex(){
		return "accountInfo";
	}
	@RequestMapping("/list")
	@ResponseBody
	public Page<UserInfo> queryCoinRecord(HttpServletRequest req,String userName,
			Long userId){
		
		Page<UserInfo> page=PageUtil.getPage(req);
		UserInfo user=new UserInfo();
		user.setUserName(userName);
		user.setId(userId);
		
		return userInfoService.queryUserInfo(page, user);
	}
	
	@RequestMapping("/accountList")
	@ResponseBody
	public Page<Account> queryAccount(HttpServletRequest req,String coinCode,
			long userId){
		
		Page<Account> page=PageUtil.getPage(req);
		Account account=new Account();
		account.setUserId(userId);
		account.setCoinCode(coinCode);
		
		return userInfoService.queryAccountInfo(page, account);
	}
	
	@RequestMapping("/batchClearAsset")
	@ResponseBody
	public JsonResult batchClearAsset(HttpServletRequest req,long userId){
		
		return assetService.clearUserAsset(userId);
	}
	
	@RequestMapping("/clearAsset")
	@ResponseBody
	public JsonResult clearAsset(HttpServletRequest req,long userId,long accountId){
		
		return assetService.clearAccountAsset(userId, accountId);
	}

}
