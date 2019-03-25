package com.btc.global.enums;

public enum UploadImgEnum {

	HEAD_IMG(1,"上传头像"),
	BG_IMG(2,"上传背景图片"),
	BANNER(3,"banner图片");
	
	private int type;
	
	private String desc;

	
	private UploadImgEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
