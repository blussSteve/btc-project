package com.btc.springConfig.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.btc.global.auth.AuthUser;
import com.btc.global.auth.TokenUtil;
import com.btc.util.Constants;

@Component
public class UserSecurityInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String token = (String) request.getSession().getAttribute(Constants.AUTH_TOKEN_NAME_DEFAULT);
		
		if(!verifyCacheToken(token)){
			response.sendRedirect("/admin/login");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
    private boolean verifyCacheToken(String token){
    	boolean flag=TokenUtil.verifyToken(token);
    	if(!flag)
    		return false;
    	AuthUser auth=TokenUtil.getAuth(token);
    	if(null==auth||null==auth.getUserId()){
    		return false;
    	}
//    	UserInfo ucv=DataCacheUtil.getUserInfo(auth.getUserId());
//    	if(ucv==null){
//    		return false;
//    	}
//    	String cacheToken=ucv.getToken();
//    	if(!cacheToken.equals(token)){
//    		return false;
//    	}
    	return true;
    }
}