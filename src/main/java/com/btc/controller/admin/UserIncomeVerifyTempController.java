package com.btc.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.enums.YesOrNoEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.UserIncomeVerifyTemp;
import com.btc.service.AssetService;

@RequestMapping("/admin/userIncomeVerifyTemp")
@Controller
public class UserIncomeVerifyTempController extends AdminBaseController {

	@Autowired
	private AssetService assetService;
	
	@RequestMapping("/index")
	public String toCoinRecord(){
		return "userIncomeVerifyTemp";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<UserIncomeVerifyTemp> queryCoinRecord(HttpServletRequest req,String userName,
			String coinCode,String beginDate,String endDate){
		
		Page<UserIncomeVerifyTemp> page=PageUtil.getPage(req);
		UserIncomeVerifyTemp record=new UserIncomeVerifyTemp();
		record.setUserName(userName);
		record.setCoinCode(coinCode);
		record.setBeginDate(beginDate);
		record.setEndDate(endDate);
		
		return assetService.queryUserIncomeVerifyTempRecord(page, record);
	}
	@RequestMapping("/createUserIncomeMessage")
	@ResponseBody
	public JsonResult createUserIncomeVerifyTemp(HttpServletRequest req){

		return assetService.createUserIncomeVerifyTemp(YesOrNoEnum.NO.getCode(), getUserId(req));
		
	}
	@RequestMapping("/commitUserIncomeMessage")
	@ResponseBody
	public JsonResult commitUserIncomeMessage(HttpServletRequest req){
		return assetService.commitUserIncomeVerifyTemp(getUserId(req));
		
	}
}
