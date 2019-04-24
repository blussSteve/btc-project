package com.btc.springConfig.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.service.RedisService;
import com.btc.springConfig.aop.annotation.RepeatAnnotation;
import com.btc.util.Constants;
import com.btc.util.MD5Util;
import com.btc.util.RedissonManager;



@Aspect
@Component
public class WebRequestRepeatAspect {

    private static Logger logger = LoggerFactory.getLogger(WebRequestRepeatAspect.class);

    
    private final static String PAGE_REPEAT_KEY="PAGE_REPATE_KEY_";
    

    @Autowired
	private RedisService redisService;



    @Pointcut("@annotation(com.btc.springConfig.aop.annotation.RepeatAnnotation)")
    public void webRequestRepeat() {}

//    @Pointcut("execution(public * com.lhy.sam.controller..*.*(..))")
//    public void webRequestRepeat() {}
    
  
    @Order(5)
    @Around("webRequestRepeat()")
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable{
        try {
           StringBuffer sb=new StringBuffer();
           Object[] args = joinPoint.getArgs();
           String str=  argsArrayToString(args);
           
           ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
           HttpServletRequest request = attributes.getRequest();
           
           
           
           MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
           Method method = methodSignature.getMethod();
           RepeatAnnotation repeatAnnotation= method.getAnnotation(RepeatAnnotation.class);
           if(!repeatAnnotation.isRepeat()){
        	   return joinPoint.proceed();
           }
           
           String url = request.getRequestURL().toString();
           String remoteAddr = getIpAddr(request);
           
           String token=getTokenString(request);
	   		if(!StringUtils.isEmpty(token)){
	   			sb.append(token).append("--");
	   		} 
           sb.append(str).append("--").append(url).append("--").append(remoteAddr);
           String md5= MD5Util.md5Encode(sb.toString());
           
           try {
        	   if(RedissonManager.lock(PAGE_REPEAT_KEY+md5,10,TimeUnit.SECONDS)){
            	   Object obj= joinPoint.proceed();
            	   RedissonManager.unlock(PAGE_REPEAT_KEY+md5);
            	   return obj;
               }
        	   return JsonResultHelp.buildFail(RspCodeEnum.$0005);
			}finally{
				RedissonManager.unlock(md5);
			}
//	          
        } catch (Exception e) {
        	logger.error("",e);
            return JsonResultHelp.buildFail(RspCodeEnum.$0005);
        }
    }
    
    /**
     * 请求参数拼装
     * 
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
    	try {
    		  String params = "";
    	        if (paramsArray != null && paramsArray.length > 0) {
    	            for (int i = 0; i < paramsArray.length; i++) {
    	            	if(paramsArray[i].getClass().toString().indexOf("org")==-1){
    	            		   Object jsonObj = JSONObject.toJSONString(paramsArray[i]);
    	                       params += jsonObj.toString() + "--";
    	            	}
    	             
    	            }
    	        }
    	        return params.trim();
		} catch (Exception e) {
			logger.error("",e.getMessage());
			return "";
		}
      
    }
    
    /**
     * 获取登录用户远程主机ip地址
     * 
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    private String getTokenString(HttpServletRequest httpRequest) {
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
}