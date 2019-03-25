package com.btc.controller.admin;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.admin.SystemSecondItem;
import com.btc.service.SystemSecondItemService;

@Controller
@RequestMapping("/admin/systemSecondItem")
public class SystemSecondItemController {
	@Autowired
	private SystemSecondItemService systemSecondItemService;

	@RequestMapping(value="/systemSecondItemPage")
	public String systemSecondItem(long id,Model model){
		model.addAttribute("id",id);
		return "systemSecondItem";
	}
	//分页
	@RequestMapping(value = "/listSystemSecondItem")
	@ResponseBody
	public Page<SystemSecondItem> listSystemSecondItem(HttpServletRequest req,long fatherId){
	
		
		Page<SystemSecondItem> page=PageUtil.getPage(req);
		SystemSecondItem childItem=new SystemSecondItem(); 
		childItem.setFirstId(fatherId);
		List<SystemSecondItem> list = systemSecondItemService.listSystemSecondItem(childItem,page);
		int total= systemSecondItemService.pageCount(childItem);
		page.setTotal(total);
		page.setRows(list);
		return page;
		
	}
	//添加二级菜单
	@RequestMapping(value = "/addSecondItem")
	@ResponseBody
	public JsonResult addSecondItem(HttpServletResponse response,SystemSecondItem item){
		int i= systemSecondItemService.addSecondItem(item);
		return JsonResultHelp.buildSucc(i);
	}
	@RequestMapping(value="/delItem")
	@ResponseBody
	public JsonResult delItem(HttpServletRequest req,long itemId){
		return systemSecondItemService.delItem(req,itemId);
		
	}
}
