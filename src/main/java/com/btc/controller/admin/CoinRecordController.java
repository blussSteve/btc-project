package com.btc.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.CoinRecord;
import com.btc.service.AssetService;

@Controller
@RequestMapping("/admin/coinRecord")
public class CoinRecordController {

	
	@Autowired
	private AssetService assetService;
	
	@RequestMapping("/index")
	public String toCoinRecord(){
		return "coinRecord";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<CoinRecord> queryCoinRecord(HttpServletRequest req,String userName,
			String coinCode,Integer tradeType,Integer status,
			String beginDate,String endDate,Long userId){
		
		Page<CoinRecord> page=PageUtil.getPage(req);
		CoinRecord record=new CoinRecord();
		record.setUserName(userName);
		record.setCoinCode(coinCode);
		record.setTradeType(tradeType);
		record.setStatus(status);
		record.setBeginDate(beginDate);
		record.setEndDate(endDate);
		record.setUserId(userId);
		
		return assetService.queryCoinRecord(page, record);
	}
}
