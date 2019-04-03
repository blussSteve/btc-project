package com.btc.controller.admin;

import javax.servlet.http.HttpServletRequest;

import com.btc.model.admin.UUser;
import com.btc.util.Constants;

 

public class AdminBaseController {
	
	
	
	
	public UUser getUuser(HttpServletRequest req){
		UUser uuser=(UUser)req.getSession().getAttribute(Constants.AUTH_USER);
		
		return uuser;
	}
	public long getUserId(HttpServletRequest req){
		UUser uuser=(UUser)req.getSession().getAttribute(Constants.AUTH_USER);
		
		return uuser.getId();
	}
	
}
