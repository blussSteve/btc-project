package com.btc.controller.admin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.service.AdminService;
import com.btc.util.Constants;
@Controller
@RequestMapping("/admin")
public class LoginController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(value="/login")
	public String login(){
		return "login";
	}
	//验证登录密码
	@RequestMapping(value="/login/checkPassword")
	@ResponseBody
	public JsonResult checkPassword(String userName,String password,HttpServletRequest request,HttpServletResponse response){
		String status=adminService.checkPassword(userName,password,request,response);
		
		return JsonResultHelp.buildSucc(status);
	}
	@RequestMapping(value="/returnSystem")
	public String returnSystem(HttpServletRequest request){
		HttpSession session=request.getSession();
		session.removeAttribute("userName");
		session.removeAttribute(Constants.AUTH_TOKEN_NAME_DEFAULT);
		return "login";
	}
}
