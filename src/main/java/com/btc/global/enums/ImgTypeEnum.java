package com.btc.global.enums;

public enum ImgTypeEnum {
	JPG(1,"jpg"),
	GIF(2,"gif"),
	JPEG(3,"jpeg"),
	PNG(4,"png"), 
	MBP(5,"bmp");
	
	
	private int num;
	
	private String code;

	private ImgTypeEnum(int num, String code) {
		this.num = num;
		this.code = code;
	}

	public static boolean isImg(String type){
		boolean flag=false;
		for(ImgTypeEnum enums:ImgTypeEnum.values()){
			
			if(enums.getCode().toUpperCase().equals(type.toUpperCase())){
				flag=true;
				break;
			}
		}
		
		return flag;
	}
	
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
