package com.btc.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class USDTUtil {

	private final static Logger logger=LoggerFactory.getLogger(USDTUtil.class);
	private final static String url="https://www.lbkex.co/request/tick?symbol=";
	
	/**
	 * 获取usdt
	 * @param coinCode
	 * @return
	 */
	public static BigDecimal getUSDT(String coinCode){
		
		try {
			
			String usdtCode=coinCode+"_usdt".toLowerCase();
			String str=HttpClientTool.doGet(url+usdtCode,null);
			JSONObject jb=JSON.parseObject(str);
			String unit=jb.getJSONObject("c").getString("usd");
			return new BigDecimal(unit);
			
		} catch (Exception e) {
			logger.error(""+e);
			try {
				String usdtCode="usdt_"+coinCode.toLowerCase();
				String str=HttpClientTool.doGet(url+usdtCode,null);
				JSONObject jb=JSON.parseObject(str);
				String unit=jb.getJSONObject("c").getString("price");
				return BigDecimal.ONE.divide(new BigDecimal(unit),10,BigDecimal.ROUND_HALF_DOWN);
				
			} catch (Exception e2) {
				logger.error(""+e2);
				
			}
			
		}
		
		
		return BigDecimal.ZERO;
	}
	
	public static void main(String[] args) {
		
		USDTUtil.getUSDT("btc");
	}
}
