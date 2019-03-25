package com.btc.springConfig.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.btc.global.auth.AuthUser;
import com.btc.global.auth.TokenUtil;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.service.RedisService;
import com.btc.util.Constants;

/**
 * Created by Think on 2017/8/27.
 */
@Component("secureCheckFilter")
public class SecureCheckFilter extends OncePerRequestFilter {
    private MappingJackson2JsonView jackson2JsonView;

    @Autowired
    private RedisService redisService;
    
    public SecureCheckFilter(){
        super();
        jackson2JsonView = new MappingJackson2JsonView();
    }
    private static final Logger log = LoggerFactory.getLogger(SecureCheckFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(checkAuth(httpServletRequest, httpServletResponse)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            try {
            	JsonResult jr= JsonResultHelp.buildFail(RspCodeEnum.$0001);
                //返回错误提示
                jackson2JsonView.render(jr.toMap(), httpServletRequest, httpServletResponse);
            }catch (Exception ce){
                log.error(ce.getMessage());
            }
        }
    }
    protected boolean checkAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException{
      
    	String tokenString =getTokenString(httpServletRequest);
        if (tokenString == null) {
            logger.warn("Not found authToken in cookie");
            return false;
        }
        if(!verifyCacheToken(tokenString)){
        	logger.warn("authToken is error or expire");
        	return false;
        }
        httpServletRequest.setAttribute(Constants.AUTH_TOKEN_NAME_DEFAULT, tokenString);
        return  true;
    }
    protected String getTokenString(HttpServletRequest httpRequest) {
        String tokenString = null;
        // from querystring
        if (StringUtils.isBlank(tokenString)) {
            tokenString = httpRequest.getParameter(Constants.AUTH_TOKEN_NAME_DEFAULT);
        }

        // from header
        if (StringUtils.isBlank(tokenString)) {
            tokenString = httpRequest.getHeader(Constants.AUTH_TOKEN_NAME_DEFAULT);
        }

        // from cookie 
        if (httpRequest.getCookies() != null) {
            for (Cookie c : httpRequest.getCookies()) {
                if (Constants.AUTH_TOKEN_NAME.equals(c.getName())) {
                    tokenString = c.getValue();
                    break;
                }
                if (Constants.AUTH_TOKEN_NAME_DEFAULT.equals(c.getName())) {
                    tokenString = c.getValue();
                    break;
                }
            }
        }

        return tokenString;
    }
    
    private boolean verifyCacheToken(String token){
    	boolean flag=TokenUtil.verifyToken(token);
    	if(!flag)
    		return false;
    	AuthUser auth=TokenUtil.getAuth(token);
    	if(null==auth.getUserId()){
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
