package com.btc.global.json;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.btc.global.json.enums.RspCodeEnum;

/**
 * json
 * @author admin
 *
 */
public  class JsonResult{
	
	private String code=RspCodeEnum.FAIL.getCode();
	
	private String msg=RspCodeEnum.FAIL.getDesc();
	
	@JsonInclude(Include.NON_NULL)
	private Object data;

	protected JsonResult() {
		super();
	}

	public JsonResult(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	public Object getData() {
		return data;
	}
	public String toJson(){
		return JSONObject.toJSONString(this);
	}
	public Map<String,Object> toMap(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("code", this.code);
		map.put("msg",this.msg);
		if(this.data != null){
			map.put("data", this.data);
		}
		return map;
	}
	@Override
	public String toString(){
		return JSONObject.toJSONString(this);
	}
	
}