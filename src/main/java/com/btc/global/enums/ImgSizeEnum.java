package com.btc.global.enums;

/**
 * 图片尺寸设置
 * @author admin
 *
 */
public enum ImgSizeEnum {

	$1(300,300,1,"1x"),
	$2(320,320,2,"2x"),
	$3(480,480,3,"3x"),
	$4(460,320,3,"3x");
	
	private int width;
	
	private int height;
	
	private int num;

	private String px;

	
	private ImgSizeEnum(int width, int height, int num, String px) {
		this.width = width;
		this.height = height;
		this.num = num;
		this.px = px;
	}

	public static ImgSizeEnum getImgSizeEnumByPxNum(int pxNum){
		
		for(ImgSizeEnum enums:ImgSizeEnum.values()){
			
			if(enums.getNum()==pxNum){
				return enums;
			}
		}
		
		return ImgSizeEnum.$1;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPx() {
		return px;
	}

	public void setPx(String px) {
		this.px = px;
	}

	
	
	
}
