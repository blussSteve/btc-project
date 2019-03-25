package com.btc.springConfig.aop;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSONObject;
import com.btc.entity.OperateLogEntity;
import com.btc.global.auth.TokenUtil;
import com.btc.service.RedisService;
import com.btc.springConfig.aop.annotation.UrlAnnotation;
import com.btc.util.Constants;



//@Aspect
//@Component
public class WebRequestLogAspect {

    private static Logger logger = LoggerFactory.getLogger(WebRequestLogAspect.class);

    private ThreadLocal<OperateLogEntity> tlocal = new ThreadLocal<OperateLogEntity>();

    @Autowired
	private RedisService redisService;

//    @Autowired
//    private OperateLogDao operateLogDao;


    @Pointcut("@annotation(com.btc.springConfig.aop.annotation.UrlAnnotation)")
    public void webRequestLog() {}
    
    @Order(5)
    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {

            long beginTime = System.currentTimeMillis();

            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            UrlAnnotation urlAnnotation= method.getAnnotation(UrlAnnotation.class);
            
            String beanName = methodSignature.getDeclaringTypeName();
            String methodName = methodSignature.getName();
            
            
            String url = request.getRequestURL().toString();
            String remoteAddr = getIpAddr(request);
            
            long userId=-1;
            String userName="";
    		String token=getTokenString(request);
    		if(!StringUtils.isEmpty(token)){
    			 userId=TokenUtil.getUserId(token);
    		} 
    		
           
            
            String reqMethod = request.getMethod();
            String params = "";
            if ("POST".equals(reqMethod)) {
                Object[] paramsArray = joinPoint.getArgs();
                params = argsArrayToString(paramsArray);
            } else {
                Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                params = paramsMap.toString();
            }

//            logger.debug("uri=" + url + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; userId=" + userId
//                    +"userName="+userName+ "; methodName=" + methodName + "; params=" + params);

            
            OperateLogEntity optLog = new OperateLogEntity();
           
            if(null!=urlAnnotation){
            	 optLog.setUrlCode(urlAnnotation!=null?urlAnnotation.urlCode().getCode()+"":"");
            	 optLog.setUrlDesc(urlAnnotation!=null?urlAnnotation.urlCode().getName()+"":"");
            }
            optLog.setBeanName(beanName);
            optLog.setUserId(userId);
            optLog.setUserName(userName);
            optLog.setToken(token);
            optLog.setMethodName(methodName);
            optLog.setParams(params != null ? params.toString() : "");
            optLog.setRemoteAddr(remoteAddr);
            optLog.setUrl(url);
            optLog.setRequestTime(beginTime);
            tlocal.set(optLog);

        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doBefore()***", e);
        }
    }

     @Order(5)
    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        try {
            // 处理完请求，返回内容
            OperateLogEntity optLog = tlocal.get();
            optLog.setIsExpLog("0");
            optLog.setResult(JSONObject.toJSONString(result));
            long beginTime = optLog.getRequestTime();
            long requestTime = (System.currentTimeMillis() - beginTime) / 1000;
            optLog.setRequestTime(requestTime);

//            logger.debug("请求耗时：" + optLog.getRequestTime() + optLog.getUrl() + "   **  " + optLog.getParams() + " ** "
//                    + optLog.getMethodName());
//            System.out.println("RESPONSE : " + result);
            
            
//            logger.info("请求日志######:{}",JSONObject.toJSONString(optLog));
            
        } catch (Exception e) {
            logger.error("***操作请求日志记录失败doAfterReturning()***", e);
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
    	                       params += jsonObj.toString() + " ";
    	            	}
    	             
    	            }
    	        }
    	        return params.trim();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "";
		}
      
    }
    
    
    @AfterThrowing(pointcut = "webRequestLog()", throwing = "e")  
    public void doAfterThrow(JoinPoint joinPoint,  Throwable e){  
    	  try {
              // 处理完请求，返回内容
              OperateLogEntity optLog = tlocal.get();
              optLog.setIsExpLog("1");
              optLog.setExpBody(getException(e));
              long beginTime = optLog.getRequestTime();
              long requestTime = (System.currentTimeMillis() - beginTime) / 1000;
              optLog.setRequestTime(requestTime);

              logger.debug("请求耗时：" + optLog.getRequestTime() + optLog.getUrl() + "   **  " + optLog.getParams() + " ** "
                      + optLog.getMethodName());
//              operateLogDao.save(optLog);	
          } catch (Exception ex) {
              logger.error("***操作请求日志记录失败doAfterReturning()***", e);
          }
    	
        //写入数据库  
        return;  
    }  
    
    private String getException(Throwable e){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    	e.printStackTrace(new PrintStream(baos));  
    	String exception = baos.toString(); 
    	return exception.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\tat", "");
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