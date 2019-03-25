package com.btc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btc.util.ImageUtil;

@RequestMapping("/sam/noAuth/")
@RestController
public class NoAuthController {

	@Autowired
	private ImageUtil imageUtil;
	
	private final static String DOWN_IMG_PATH_REQ = "/downImg/{fileName:.+}";

	/**
	 * 图片下载
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 */
	@RequestMapping(value = DOWN_IMG_PATH_REQ)
	public void downImg(@PathVariable("fileName") String fileName,	HttpServletRequest req, HttpServletResponse rsp) {
		imageUtil.getOutputStream(fileName, rsp);
	}

}
