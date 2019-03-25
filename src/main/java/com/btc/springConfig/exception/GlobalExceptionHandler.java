package com.btc.springConfig.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);  
  	@ResponseBody
    @ExceptionHandler
    public JsonResult jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
  		logger.error("系统异常");
  		e.printStackTrace();
    	return JsonResultHelp.buildFail(RspCodeEnum.FAIL);
    }

}
