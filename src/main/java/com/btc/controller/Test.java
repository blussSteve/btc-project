package com.btc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.springConfig.aop.annotation.RepeatAnnotation;

@RestController
@RequestMapping("/test")
public class Test {

	@RepeatAnnotation()
	@RequestMapping(value="/test1")
	public JsonResult test1() throws InterruptedException{
		Thread.sleep(1000);
		return JsonResultHelp.buildSucc();
	}
	
}
