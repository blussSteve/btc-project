package com.btc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.btc.global.enums.ImgTypeEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.util.ImageUtil;
import com.btc.util.StringUtil;

@RequestMapping("/front/noAuth/")
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
	
	private final static String UPLOAD_IMG_REQ="/uploadImg";
	
	/**
	 * 图片上传
	 * @param req 
	 * @return
	 */
	@RequestMapping(value=UPLOAD_IMG_REQ)
	public JsonResult uploadImg(MultipartHttpServletRequest req){
		 MultipartFile file= ((MultipartHttpServletRequest) req).getFile("file");
		 if(!file.isEmpty()){
			 
			 int uploadImgType=StringUtil.isEmpty(req.getParameter("imgType"))?0:Integer.parseInt(req.getParameter("imgType"));
		     String fileOrgName=file.getOriginalFilename();
		     String type=fileOrgName.substring(fileOrgName.lastIndexOf(".") + 1, fileOrgName.length());
		     
		     if(!ImgTypeEnum.isImg(type)){
		    	 return  JsonResultHelp.buildFail(RspCodeEnum.$1101);
		     }
		    String path=  imageUtil.saveHeadImg(file,uploadImgType);
		    return JsonResultHelp.buildSucc(path);
		 }
		 return JsonResultHelp.buildSucc();
		
	}
	
}
