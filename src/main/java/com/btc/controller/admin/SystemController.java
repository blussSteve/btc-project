package com.btc.controller.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.model.admin.SystemFirstItem;
import com.btc.service.SystemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
}
