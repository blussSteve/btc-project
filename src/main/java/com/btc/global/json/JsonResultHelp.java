package com.btc.global.json;

import com.btc.global.json.enums.RspCodeEnum;
import com.btc.util.StringUtil;

public class JsonResultHelp {

	/**
	 * 成功
	 * @return
	 */
	public static JsonResult buildSucc(){
		JsonResult jr=new JsonResult(RspCodeEnum.SUCCESS.getCode(),RspCodeEnum.SUCCESS.getDesc(),null);
		return jr;
	}
	
	/** 
	 * 成功
	 * @param data 返回结果
	 * @return
	 */
	public static JsonResult buildSucc(Object data){
		JsonResult jr=new JsonResult(RspCodeEnum.SUCCESS.getCode(),RspCodeEnum.SUCCESS.getDesc(),data);
		return jr;
	}
	
	/**
	 * 失败
	 * @return
	 */
	public static JsonResult buildFail(){
		JsonResult jr=new JsonResult(RspCodeEnum.FAIL.getCode(),RspCodeEnum.FAIL.getDesc(),null);
		return jr;
	}
	/**
	 * 失败
	 * @param rspCode 失败反馈
	 * @return
	 */
	public static JsonResult buildFail(RspCodeEnum rspCode){
		JsonResult jr=new JsonResult(rspCode.getCode(),rspCode.getDesc(),null);
		return jr;
	}
	
	/**
	 * 失败
	 * @param rspCode 失败反馈
	 * @return
	 */
	public static JsonResult buildFail(RspCodeEnum rspCode,String desc){
		JsonResult jr=new JsonResult(rspCode.getCode(),desc,null);
		return jr;
	}

	/**
	 * 失败
	 * @param rspCode 失败反馈
	 * @return
	 */
	public static JsonResult buildFailWithCode(RspCodeEnum rspCode){
		JsonResult jr=new JsonResult(rspCode.getCode(),rspCode.getDesc(),null);
		return jr;
	}


	/**
	 * 失败
	 * @param rspCode 反馈响应码
	 * @param data 反馈描述
	 * @return
	 */
	public static JsonResult buildFail(RspCodeEnum rspCode,String desc,Object data){
		JsonResult jr=new JsonResult(rspCode.getCode(),StringUtil.isEmpty(desc)?rspCode.getDesc():desc,data);
		return jr;
	}
}
