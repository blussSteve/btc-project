package com.btc.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.CoinRecord;
import com.btc.model.UserIncomeRecord;
import com.btc.service.AssetService;

@Controller
@RequestMapping("/admin/userIncomeRecord")
public class UserIncomeRecordController {

	
	@Autowired
	private AssetService assetService;
	
	@RequestMapping("/index")
	public String toCoinRecord(){
		return "userIncomeRecord";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<UserIncomeRecord> queryCoinRecord(HttpServletRequest req,String userName,
			String coinCode,Long userId,String beginDate,String endDate){
		
		Page<UserIncomeRecord> page=PageUtil.getPage(req);
		UserIncomeRecord record=new UserIncomeRecord();
		record.setUserName(userName);
		record.setCoinCode(coinCode);
		record.setBeginDate(beginDate);
		record.setEndDate(endDate);
		record.setUserId(userId);
		
		return assetService.queryCoinIncomeRecord(page, record);
	}
	
	@RequestMapping("/updateIsAdd")
	@ResponseBody
	public JsonResult updateUserIncomeIsAdd(String date) {
		
		return assetService.updateUserIncomeIsAdd(1, date);
	}
	
}
