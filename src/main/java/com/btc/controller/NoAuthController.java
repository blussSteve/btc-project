package com.btc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.util.FileUtil;
import com.btc.util.StringUtil;

@RequestMapping("/front/noAuth/")
@RestController
public class NoAuthController {

	@Autowired
	private FileUtil fileUtil;
	
	@RequestMapping("/uploadFile")
	public JsonResult uploadFile(MultipartHttpServletRequest req){
		 MultipartFile file= ((MultipartHttpServletRequest) req).getFile("file");
		 if(file.isEmpty()){
			 return JsonResultHelp.buildFail(RspCodeEnum.$1102);
		 }
		 
		 String fileUrl=fileUtil.uploadFile(file);
		 if(StringUtil.isEmpty(fileUrl)){
			 return JsonResultHelp.buildFail(RspCodeEnum.$1100);
		 }
		return JsonResultHelp.buildSucc(fileUrl);
	}
	
	
	@RequestMapping("/downFile/{fileName:.+}")
	public void downFile(@PathVariable("fileName") String fileName,HttpServletRequest req, HttpServletResponse rsp) throws IOException{
		fileUtil.downFile(fileName, req, rsp);
	}
	
}
