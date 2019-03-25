package com.btc.controller.admin;
import java.util.List;

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
import com.btc.model.admin.SystemFirstItem;
import com.btc.service.SystemFirstItemService;

@Controller
@RequestMapping("/admin/systemFirstItem")
public class SystemFirstItemController {
	@Autowired
	private SystemFirstItemService systemFirstItemService;

	@RequestMapping(value="/systemFirstItemPage")
	public String systemFirstItemPage(){
		return "item";
	}
	//分页
	@RequestMapping(value = "/listSystemFirstItem")
	@ResponseBody
	public Page<SystemFirstItem> listSystemFirstItem(HttpServletRequest req){
		
		String itemName=req.getParameter("itemName");
		
		SystemFirstItem item=new SystemFirstItem();
		item.setItemName(itemName);
		
		Page<SystemFirstItem> page=PageUtil.getPage(req);
		List<SystemFirstItem> list = systemFirstItemService.listSystemFirstItem(page, item);
		int total= systemFirstItemService.pageCount(item);
		page.setTotal(total);
		page.setRows(list);
		return page;
		
	}
	//添加首页
	@RequestMapping(value = "/addFirstItem")
	@ResponseBody
	public JsonResult addFirstItem(HttpServletResponse response,SystemFirstItem system){
		int i= systemFirstItemService.addFirstItem(system);
		return JsonResultHelp.buildSucc(i);
	}
	
	@RequestMapping(value="/delItem")
	@ResponseBody
	public JsonResult delItem(HttpServletRequest req,long itemId){
		
		return systemFirstItemService.delItem(req,itemId);
		
	}

}
