package com.btc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.btc.global.enums.ImgTypeEnum;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.util.ImageUtil;

@RequestMapping("/sam/auth/file")
@RestController
public class FileController {

	
	@Autowired
	private ImageUtil imageUtil;
	
	private final static String UPLOAD_IMG_REQ="/uploadImg";
	
	/**
	 * 图片上传
	 * @param req 
	 * @return
	 */
	@RequestMapping(value=UPLOAD_IMG_REQ, method = RequestMethod.POST)
	public JsonResult uploadImg(MultipartHttpServletRequest req){
		 MultipartFile file= ((MultipartHttpServletRequest) req).getFile("file");
		 if(!file.isEmpty()){
			 int uploadImgType=Integer.parseInt(req.getParameter("imgType"));
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
