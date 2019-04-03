package com.btc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btc.global.json.JsonResult;
import com.btc.service.UserService;

@RequestMapping(value="/front/noAuth")
@RestController
public class AppLoginController {

	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param accessCode
	 * @return
	 */
	@RequestMapping("/login")
	public JsonResult login(String accessCode){
		return userService.userLogin(accessCode);
	}
}
