package com.btc.controller.admin;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.AssetTotal;
import com.btc.model.CoinRateRecord;
import com.btc.model.admin.SysCoinsDic;
import com.btc.model.admin.SystemFirstItem;
import com.btc.service.SystemService;
import com.btc.util.StringUtil;

@Controller
@RequestMapping("/admin/system")
public class SystemController extends AdminBaseController{
	@Autowired
	private SystemService systemService;
	

	//获取菜单列表
	@RequestMapping(value = "/listItem")
	@ResponseBody
	public List<Map<String,Object>> listItem(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> list=systemService.listItem(request,response);
		return list;
	}
	//获取菜单列表
	@RequestMapping(value = "/mainlistItem")
	@ResponseBody
	public List<SystemFirstItem> mainlistItem(HttpServletRequest request, HttpServletResponse response){
		List<SystemFirstItem> list=systemService.mainlistItem(request,response);
		return list;
	}
	
	@RequestMapping(value = "/toSysCoinsDicHtml")
	public String toSysCoinsDicHtml(){
		return "sysCoinsDic";
	}
	
	@RequestMapping(value="/queryAllCoins")
	@ResponseBody
	public JsonResult queryAllCoins(){
		return JsonResultHelp.buildSucc(systemService.queryAllCoins());
	}
	
	@RequestMapping("/querySysCoins")
	@ResponseBody
	public Page<SysCoinsDic> querySysCoinsDic(HttpServletRequest req){
		
		SysCoinsDic dic=new SysCoinsDic();
		String coinCode=req.getParameter("coinCode");
		String status=req.getParameter("status");
		dic.setCoinCode(StringUtil.isEmpty(coinCode)?null:coinCode);
		dic.setStatus(StringUtil.isEmpty(status)?null:Integer.parseInt(status));
		Page<SysCoinsDic> page=PageUtil.getPage(req);
		
		return systemService.querySysCoinsDic(page, dic);
	}
	
	@RequestMapping("/savaOrUpdateSysCoinsDic")
	@ResponseBody
	public 	JsonResult saveOrUpdateSysCoinsDic(SysCoinsDic dic){
		return systemService.saveOrUpdateSysCoinsDic(dic);
	}
	
	@RequestMapping("/updateCoinsDicStatus")
	@ResponseBody
	public 	JsonResult updateCoinsDicStatus(long id,int status){
		return systemService.updateSysCoinsDicStatus(id, status);
	}


	@RequestMapping("/delSysCoinsDic")
	@ResponseBody
	public JsonResult delSysCoinsDic(long dicId){
		return systemService.delSysCoinsDic(dicId);
	}
	
	@RequestMapping("/checkCoinCodeIsExist")
	@ResponseBody
	public JsonResult checkCoinCodeIsExist(String coinCode){
		return systemService.checkCoinCodeIsExist(coinCode);
		
	}
	
	@RequestMapping("/coinRate")
	public String toCoinRate(){
		return "coinRate";
	}
	
	@RequestMapping("/queryCoinRate")
	@ResponseBody
	public Page<CoinRateRecord> queryCoinRate(HttpServletRequest req){
		String coinCode=req.getParameter("coinCode");
		Integer status=StringUtil.isEmpty(req.getParameter("status"))?null:Integer.parseInt(req.getParameter("status"));
		
		String beginDate=req.getParameter("beginDate");
		String endDate=req.getParameter("endDate");
		
	    Page<CoinRateRecord> page=PageUtil.getPage(req);
		 
		CoinRateRecord coinRateRecord=new CoinRateRecord();
		
		coinRateRecord.setCoinCode(StringUtil.isEmpty(coinCode)?null:coinCode);
		coinRateRecord.setStatus(status);
		coinRateRecord.setBeginDate(StringUtil.isEmpty(beginDate)?null:beginDate);
		coinRateRecord.setEndDate(StringUtil.isEmpty(endDate)?null:endDate);
		
		return systemService.queryCoinRate(page, coinRateRecord);
		
		
	}

	@RequestMapping("/saveOrUpdateCoinRate")
	@ResponseBody
	public JsonResult saveOrUpdateCoinRate(CoinRateRecord coinRateRecord){
		
		return systemService.saveOrUpdateCoinRate(coinRateRecord);
		
	}

	@RequestMapping("/checkCoinRateIsExist")
	@ResponseBody
	public JsonResult checkCoinRateIsExist(String code, String dateStr){
		return systemService.checkCoinRateIsExist(code, dateStr);
	}
	@RequestMapping("/delCoinRate")
	@ResponseBody
	public JsonResult delCoinRate(long id){
		
		return 	systemService.delCoinRate(id);
	}
	
	@RequestMapping("/assetTotal")
	public String toAssetTotal(){
		return "home";
	}
	
	@RequestMapping("/queryAssetTotal")
	@ResponseBody
	public Page<AssetTotal> queryAssetTotal(HttpServletRequest req){
		
		Page<AssetTotal> page=PageUtil.getPage(req);
		return systemService.queryAssetTotal(page);
	}
	
	@RequestMapping("/queryAssetLine")
	@ResponseBody
	public JsonResult getAssetLine(){
		
		return systemService.queryAssetTotalKline();
		
	}
}
