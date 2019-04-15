package com.btc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.btc.global.json.enums.RspCodeEnum;

@Component
public class FileUtil {
	
	@Value("${lhy.file.path}")
	private String filePath;
	
	@Value("${lhy.file.down.url}")
	private String downFileUrl;
	
	public String uploadFile(MultipartFile file){
		  //原始文件名
        String originalFilename = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString();
        if(originalFilename.contains(".")){
            newName +=originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        FileOutputStream fos=null;
        try {
        	 //如果目录不存在，自动创建文件夹
            File dir = new File(filePath);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            fos = new FileOutputStream(filePath+newName);
            IOUtils.copy(file.getInputStream(),fos);
            return downFileUrl+newName;
            
        }catch (Throwable e){
           return "";
        }finally {
        	if(fos!=null){
        		try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	public void downFile(String fileName,HttpServletRequest req, HttpServletResponse rsp) throws IOException{
		 if(StringUtils.isEmpty(fileName)){
			   rsp.sendError(Integer.parseInt(RspCodeEnum.$1103.getCode()),RspCodeEnum.$1103.getDesc());
	            return;
	        }
	        String savePath = filePath+fileName;
	        File file = new File(savePath);
	        if(!file.exists()){
	        	 rsp.sendError(Integer.parseInt(RspCodeEnum.$1103.getCode()),RspCodeEnum.$1103.getDesc());
	            return;
	        }
	        rsp.setHeader("Content-Disposition", "attachment;filename="+fileName);
	        rsp.setContentType("image/svg+xml;charset=UTF-8");

	        InputStream in = null;

	        try {
                in= new FileInputStream(savePath);
                IOUtils.copy(in,rsp.getOutputStream());
	        }catch (Exception e){

	        }finally {
	        	if(null!=rsp.getOutputStream()){
	        		try {
						rsp.getOutputStream().close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        	if(in!=null){
	        		 try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	        }

	}

}
