package com.btc.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.btc.global.enums.ImgSizeEnum;
import com.btc.global.enums.ImgTypeEnum;
import com.btc.global.enums.UploadImgEnum;

@Component
public class ImageUtil {
	
	@Value("${lhy.file.path}")
	private String imgPath;
	
	@Value("${lhy.file.down.url}")
	private String downImgUrl;
	
	public  String saveHeadImg(MultipartFile file,int uploadImgType){
		String fileName=UUID.randomUUID().toString().replaceAll("-", "")+"."+ImgTypeEnum.JPG.getCode();
		String outFilePath="";
		String filePath="";
		try {
			outFilePath=imgPath;
			createMkdirs(outFilePath);
			filePath=outFilePath+fileName;
			
			
			if(UploadImgEnum.HEAD_IMG.getType()==uploadImgType){
				Thumbnails.of(file.getInputStream()).
				size(ImgSizeEnum.$1.getWidth(), ImgSizeEnum.$1.getHeight()).
				outputFormat(ImgTypeEnum.JPG.getCode()).
				toFile(filePath);
			}else if(UploadImgEnum.BG_IMG.getType()==uploadImgType){
				Thumbnails.of(file.getInputStream()).
				size(ImgSizeEnum.$4.getWidth(), ImgSizeEnum.$4.getHeight()).
				outputFormat(ImgTypeEnum.JPG.getCode()).
				toFile(filePath);
			}else{
				Thumbnails.of(file.getInputStream()).
				scale(1).
				outputFormat(ImgTypeEnum.JPG.getCode()).
				toFile(filePath);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
		
		return downImgUrl+fileName;
	}
	
	/**
	 * 创建图片路径
	 * @param path
	 */
	private void createMkdirs(String path){
	 File uploadedFileDir = new File(path);
        if (!uploadedFileDir.exists()) {
            uploadedFileDir.mkdirs();
        }
	}
	
	
	/**
	 * @param imgType
	 * @param pxNum
	 * @param imgName
	 * @return
	 */
	public OutputStream getOutputStream(String imgName,HttpServletResponse rsp){
	
		OutputStream os=null;
		try {
			 String path=imgPath+imgName;
			 Thumbnails.of(path).scale(1).toOutputStream(rsp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return os;
	}
}
