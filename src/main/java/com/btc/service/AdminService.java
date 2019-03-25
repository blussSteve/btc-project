package com.btc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.admin.UUser;
import com.btc.model.admin.UUserRole;

public interface AdminService{

	String checkPassword(String userName, String password,HttpServletRequest request,HttpServletResponse response);

	UUser message(String attribute);

	List<UUser> listAdmin(Page<UUser> page, UUser  admin);
	int pageCount(UUser  admin);
 
	JsonResult addAdmin(UUser user);

	int getLevel(HttpServletRequest request, HttpServletResponse response);

	JsonResult editMessage(HttpServletRequest request,UUser user);

	JsonResult setUserRole(List<UUserRole> listUserRole, int userId);

	JsonResult getUserRole(int userId);

	JsonResult setUUserIsLock(int isLock, int userId);

	

}