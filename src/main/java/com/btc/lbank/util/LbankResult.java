package com.btc.lbank.util;

import com.btc.lbank.enums.LbankRspCodeEnum;


public class LbankResult<T> {

	private static final String SUCCESS_CODE = "0000";

	private static final String SUCCESS_MSG = "成功";
	private static final String FAIL_CODE = "9999";
	private static final String FAIL_MSG = "失败";

	private String code;
	private String msg;
	private T obj;

	private LbankResult(String code, String msg, T t) {
		this.code = code;
		this.msg = msg;
		this.obj = t;

	}

	/**
	 * 构建成功请求
	 * 
	 * @param t
	 * @return
	 */
	public static <T> LbankResult<T> buildSucc(T t) {
		return new LbankResult<T>(SUCCESS_CODE, SUCCESS_MSG, t);
	}
	/**
	 *构建成功请求
	 * @return
	 */
	public static <T> LbankResult<T> buildSucc() {
		return new LbankResult<T>(SUCCESS_CODE, SUCCESS_MSG, null);
	}

	/**
	 * 构建失败请求
	 * @return
	 */
	public static <T> LbankResult<T> buildFail() {
		return new LbankResult<T>(FAIL_CODE, FAIL_MSG, null);
	}
	/**
	 * 构建失败请求
	 * @return
	 */
	public static <T> LbankResult<T> buildFailMsg(LbankRspCodeEnum rspCodeEnum,String msg) {
		return new LbankResult<T>(rspCodeEnum.getCode(), msg, null);
	}
	/**
	 * 构建失败请求
	 * @param t
	 * @return
	 */
	public static <T> LbankResult<T> buildFail(LbankRspCodeEnum rspCodeEnum) {
		return new LbankResult<T>(rspCodeEnum.getCode(), rspCodeEnum.getDesc(), null);
	}
	/**
	 * 构建失败请求
	 * @param t
	 * @return
	 */
	public static <T> LbankResult<T> buildFail(LbankRspCodeEnum rspCodeEnum,T t) {
		return new LbankResult<T>(rspCodeEnum.getCode(), rspCodeEnum.getDesc(), null);
	}

	public  boolean isSuccess() {
		if (SUCCESS_CODE.equals(code))
			return true;
		return false;
	}
	public  boolean isFullSuccess() {
		if (SUCCESS_CODE.equals(code)&&null!=obj)
			return true;
		return false;
	}
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getObj() {
		return obj;
	}


}
