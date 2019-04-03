package com.btc.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.page.Page;
import com.btc.global.page.PageUtil;
import com.btc.model.admin.UUser;
import com.btc.model.admin.UUserRole;
import com.btc.service.AdminService;
@Controller
@RequestMapping("/admin")
public class HomeController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(value="/adminPage")
	public String adminPage(){
		return "admin";
	}
	@RequestMapping(value="/index")
	public String index(){
		return "index";
	}
	@RequestMapping(value="/home")
	public String home(){
		return "home";
	}
	@RequestMapping(value="/addAdminPage")
	public String addAdminPage(){
		return "addAdmin";
	}
	
	//分页
	@RequestMapping(value = "/userList")
	@ResponseBody
	public Page<UUser> userList(HttpServletRequest req){
		String userName=req.getParameter("userName");
		String userId=req.getParameter("userId");
		String mobile=req.getParameter("mobile");
		String isLocked=req.getParameter("status");
		
		UUser user=new UUser();
		user.setUserName(StringUtils.isEmpty(userName)?null:userName );
		user.setId(StringUtils.isEmpty(userId)?null:Integer.parseInt(userId));
		user.setMobile(StringUtils.isEmpty(mobile)?null:mobile);
		
		if(StringUtils.isEmpty(isLocked)){
			
			user.setIsLocked(null);
		}else{
			user.setIsLocked(Integer.parseInt(isLocked));
		}
		
		Page<UUser> page=PageUtil.getPage(req);
		List<UUser> list = adminService.listAdmin(page, user);
		int total= adminService.pageCount(user);
		page.setTotal(total);
		page.setRows(list);
		return page;
		
	}
	
	@RequestMapping(value = "/addUser")
	@ResponseBody
	public JsonResult addAdmin(UUser user){
		return adminService.addAdmin(user);
		
	}
	@RequestMapping(value = "/editMessage")
	@ResponseBody
	public JsonResult editMessage(HttpServletRequest request,UUser user){
		return adminService.editMessage(request,user);
	}
	@RequestMapping(value = "/message")
	@ResponseBody
	public JsonResult message(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		UUser admin = adminService.message((String)session.getAttribute("userName"));
		return JsonResultHelp.buildSucc(admin); 
	}
	
	@RequestMapping(value="/userRole/{userId}",method=RequestMethod.GET)
	public ModelAndView userRole(ModelAndView modelAndView,@PathVariable int userId){
		
		modelAndView.addObject("userId", userId);
		modelAndView.setViewName("userTreeRole");
		
		return modelAndView;
	}
	
	@RequestMapping("/getUserRole")
	@ResponseBody
	public JsonResult getUserRole(int userId){
		
		return adminService.getUserRole(userId);
	}
	
	@RequestMapping("/setUserRole")
	@ResponseBody
	public JsonResult setUserRole(String  listUserRoleStr, int userId){
		
		 List<UUserRole> listUserRole=JSONArray.parseArray(listUserRoleStr, UUserRole.class);
		return adminService.setUserRole(listUserRole, userId);
		
	}
	@RequestMapping("/setUserIsLock")
	@ResponseBody
	public JsonResult setUUserIsLock(int isLock,int userId){
		
		return adminService.setUUserIsLock(isLock, userId);
	}
	
	
}
