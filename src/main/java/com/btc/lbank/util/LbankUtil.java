package com.btc.lbank.util;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.btc.lbank.bean.LbankToken;
import com.btc.lbank.bean.LbankTrade;
import com.btc.lbank.bean.LbankUserInfo;
import com.btc.lbank.enums.LbankRspCodeEnum;
import com.btc.util.HMACSHA256Util;
import com.btc.util.HttpClientTool;
import com.google.common.collect.Maps;

@Component
public class LbankUtil {
	
	private final static Logger logger=LoggerFactory.getLogger(LbankUtil.class);

	@Value("${lbank.app_id}")
	private String lbankAppId;

	@Value("${lbank.secret}")
	private String lbankSecret;

	@Value("${lbank.url}")
	private String lbankUrl;
	
	private Map<String, String> headers;

	@PostConstruct
	public void init() {
		headers = Maps.newHashMap();
		headers.put("authorization", lbankSecret);
	}

	/**
	 * 授权
	 * @param accessCode
	 * @return
	 */
	public LbankResult<LbankToken> auth(String accessCode){
		
		try {
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("access_code", accessCode);
			
			
			String str=HttpClientTool.doPost(lbankUrl+"/security/tocken", params,headers);
			
			LbankToken lbankToken=JSON.parseObject(str, LbankToken.class);
			
			if(null!=lbankToken){
				return LbankResult.buildSucc(lbankToken);
			}
			LbankRspData lbankRspData=JSON.parseObject(str, LbankRspData.class);
			return LbankResult.buildFailMsg(LbankRspCodeEnum.FAIL,lbankRspData.getMessage());
		} catch (Exception e) {
			logger.error(""+e);
			return LbankResult.buildFail();
		}
	}
	
	/**
	 * 获取用户信息
	 * @param openid
	 * @return
	 */
	public LbankResult<LbankUserInfo> getUserInfo(String openid,String token){
		
		try {
			
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("open_id", openid);
			params.put("access_token", token);
			 
			String str=HttpClientTool.doPost(lbankUrl+"/oauth2/userInfo", params,headers);
			
			LbankUserInfo lbankToken=JSON.parseObject(str, LbankUserInfo.class);
			
			if(null!=lbankToken){
				return LbankResult.buildSucc(lbankToken);
			}
			LbankRspData lbankRspData=JSON.parseObject(str, LbankRspData.class);
			return LbankResult.buildFailMsg(LbankRspCodeEnum.FAIL,lbankRspData.getMessage());
		} catch (Exception e) {
			 logger.error(""+e);
			 return LbankResult.buildFail();
		}
		
	}
	
	/**
	 * 检测token是否有效
	 * @param token
	 * @param openId
	 * @return
	 */
	public LbankResult<Boolean> checkTokenIsValid(String token,String openId){
		try {
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("access_token", token);
			params.put("open_id", openId);
			
			String str=HttpClientTool.doPost(lbankUrl+"/oauth2/valid_token", params,headers);
			
			JSONObject jb=JSON.parseObject(str);
			
			if(jb.getBooleanValue("valid")){
				return LbankResult.buildSucc(true);
			}
			return LbankResult.buildFail();
			
		} catch (Exception e) {
			logger.error(""+e);
			return LbankResult.buildFail();
		}
		
	}
	/**
	 * 刷新token
	 * @param refreshToken
	 * @return
	 */
	public LbankResult<LbankToken> refreshToken(String refreshToken){
		
		try {
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			
			params.put("grant_type", "authorization_code");
			params.put("refresh_token", refreshToken);
			
			String str=HttpClientTool.doPost(lbankUrl+"/oauth2/refresh_token", params,headers);
			
			JSONObject jb=JSON.parseObject(str);
			
			if(null!=jb.getString("status")){
				return LbankResult.buildFailMsg(LbankRspCodeEnum.$1001,jb.getString("message"));
				
			}
			LbankToken lbankToken=JSON.parseObject(str, LbankToken.class);
			
			return LbankResult.buildSucc(lbankToken);
			
		} catch (Exception e) {
			logger.error(""+e);
			return LbankResult.buildFail();
		}
		
	}
	
	
	/**
	 * 交易
	 * @param token
	 * @param orderNo
	 * @param tradeType
	 * @param assetCode
	 * @param amount
	 * @param openId
	 * @return
	 */
	public LbankResult<LbankTrade> trade(String token,String orderNo,String tradeType,String assetCode,String amount,String openId){
		
		
		try {
			
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("open_id", openId);
			
			params.put("access_token", token);
			params.put("order_no", orderNo);
			params.put("trade_type", tradeType);
			params.put("asset_code", assetCode);	
			params.put("amount", amount);	
			String str=HttpClientTool.doPost(lbankUrl+"/business/trade", params,headers);
			JSONObject jb=JSON.parseObject(str);
			
			if(null!=jb.getString("status")){
				if("3008"==jb.getString("status")){
					return LbankResult.buildFail(LbankRspCodeEnum.$1000);
				}
				return LbankResult.buildFailMsg(LbankRspCodeEnum.$1001, jb.getString("message"));
				
			}
			if(!"0000".equals(jb.getString("code"))){
				return LbankResult.buildFail(LbankRspCodeEnum.$1001);
			}
			LbankTrade trade=new LbankTrade();
			trade.setBiz_number(jb.getString("biz_number"));
			trade.setBalance_amt(jb.getString("balanceAmt"));
			return LbankResult.buildSucc(trade);
		} catch (Exception e) {
			 logger.error(""+e);
			 return LbankResult.buildFail();
		}
		
	}
	/**
	 * 订单查询
	 * @param token
	 * @param orderNo
	 * @return
	 */
	public LbankResult<LbankTrade> queryOrder(String orderNo){
		
		try {
			
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("order_no", orderNo);
			
			String str=HttpClientTool.doPost(lbankUrl+"/business/queryOrder", params,headers);
			JSONObject jb=JSON.parseObject(str);
			
			if(null!=jb.getString("status")){
				if("3008"==jb.getString("status")){
					return LbankResult.buildFail(LbankRspCodeEnum.$1000);
				}
				return LbankResult.buildFailMsg(LbankRspCodeEnum.$1001, jb.getString("message"));
				
			}
			LbankTrade trade=new LbankTrade();
			trade.setBiz_number(jb.getString("biz_number"));
			trade.setStatus(jb.getInteger("status"));
			return LbankResult.buildSucc(trade);
		} catch (Exception e) {
			 logger.error(""+e);
			 return LbankResult.buildFail();
		}
		
	}
	/**
	 * 批量获获取账户余额
	 * @param openId
	 * @param token
	 * @param assetCodes
	 * @return
	 */
	public LbankResult<JSONObject> batchQueryBalance(String openId,String token,String assetCodes){
		try {
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("asset_codes", assetCodes);
			params.put("open_id", openId);
			params.put("access_token",  token);
			
			String str=HttpClientTool.doPost(lbankUrl+"/oauth2/batchQueryBalance", params,headers);
			
			JSONObject jb=JSON.parseObject(str);
			
			if(null!=jb.getString("status")){
				if("3008"==jb.getString("status")){
					return LbankResult.buildFail(LbankRspCodeEnum.$1000);
				}
				return LbankResult.buildFailMsg(LbankRspCodeEnum.$1001,jb.getString("message"));
				
			}
			return LbankResult.buildSucc(jb.getJSONObject("balance"));
			
		} catch (Exception e) {
			logger.error(""+e);
			return LbankResult.buildFail();
		}
		
	}
	
	/**
	 * 批量获获取账户余额
	 * @param openId
	 * @param token
	 * @param assetCodes
	 * @return
	 */
	public LbankResult<String> queryBalance(String openId,String token,String assetCode){
		try {
			Map<String,String> params=Maps.newHashMap();
			
			params.put("client_id", lbankAppId);
			params.put("asset_code", assetCode);
			params.put("open_id", openId);
			params.put("access_token",  token);
			
			String str=HttpClientTool.doPost(lbankUrl+"/oauth2/queryBalance", params,headers);
			
			JSONObject jb=JSON.parseObject(str);
			
			if(null!=jb.getString("status")){
				if("3008"==jb.getString("status")){
					return LbankResult.buildFail(LbankRspCodeEnum.$1000);
				}
				return LbankResult.buildFailMsg(LbankRspCodeEnum.$1001,jb.getString("message"));
				
			}
			return LbankResult.buildSucc(jb.getString("balanceAmt"));
			
		} catch (Exception e) {
			logger.error(""+e);
			return LbankResult.buildFail();
		}
		
	}
	
	/**
	 * 手动划出接口
	 * @param openId
	 * @param orderNo
	 * @param tradeType
	 * @param assetCode
	 * @param amount
	 * @return
	 */
	public LbankResult<LbankTrade> sysTrade(String openId,String orderNo,String tradeType,String assetCode,String amount){
		try {
			
			Map<String,String> params=Maps.newHashMap();
			params.put("client_id", lbankAppId);
	        params.put("open_id", openId);
	        params.put("order_no", orderNo);
	        params.put("trade_type", "1");
	        params.put("asset_code",assetCode);
	        params.put("amount", amount);
	        params.put("sign", HMACSHA256Util.signWithHMACSHA256(params, lbankSecret));
	        String str=HttpClientTool.doPost(lbankUrl+"/business/transfer", params,headers);
			JSONObject jb=JSON.parseObject(str);
			
			if(null!=jb.getString("status")){
				if("3008"==jb.getString("status")){
					return LbankResult.buildFail(LbankRspCodeEnum.$1000);
				}
				return LbankResult.buildFailMsg(LbankRspCodeEnum.$1001, jb.getString("message"));
				
			}
			if(!"0000".equals(jb.getString("code"))){
				return LbankResult.buildFail(LbankRspCodeEnum.$1000);
			}
			LbankTrade trade=new LbankTrade();
			trade.setBiz_number(jb.getString("biz_number"));
			trade.setBalance_amt(jb.getString("balanceAmt"));
			return LbankResult.buildSucc(trade);
		} catch (Exception e) {
			 logger.error(""+e);
			 return LbankResult.buildFail();
		}
		
	}
}
