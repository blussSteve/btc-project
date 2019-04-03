package com.btc.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.UserIncomeVerify;
import com.btc.service.AssetService;

@RequestMapping("/admin/userIncomeVerify")
@Controller
public class UserIncomeVerifyController {

	@Autowired
	private AssetService assetService;
	
	@RequestMapping("/index")
	public String toCoinRecord(){
		return "userIncomeVerify";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<UserIncomeVerify> queryCoinRecord(HttpServletRequest req,String userName,
			String coinCode,String beginDate,String endDate){
		
		Page<UserIncomeVerify> page=PageUtil.getPage(req);
		UserIncomeVerify record=new UserIncomeVerify();
		record.setUserName(userName);
		record.setCoinCode(coinCode);
		record.setBeginDate(beginDate);
		record.setEndDate(endDate);
		
		return assetService.queryUserIncomeVerifyRecord(page, record);
	}
}
