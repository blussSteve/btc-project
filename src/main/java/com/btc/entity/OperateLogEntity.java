package com.btc.entity;

import java.io.Serializable;


public class OperateLogEntity implements Serializable {
	
	private static final long serialVersionUID = 4941590327442195357L;
	

	private long id;
	private String beanName;
	
	private String userName;
	
	private long userId;
	
	private String appId;
	
	private String token;
	
	private String methodName;
	
	private String params;
	
	private String remoteAddr;
	
	private String url;
	
	private String urlCode;
	
	private String urlDesc;
	
	private String result;
	
	private String isExpLog;
	
	private String expBody;
	
	private long requestTime;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlCode() {
		return urlCode;
	}

	public void setUrlCode(String urlCode) {
		this.urlCode = urlCode;
	}

	public String getUrlDesc() {
		return urlDesc;
	}

	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getIsExpLog() {
		return isExpLog;
	}

	public void setIsExpLog(String isExpLog) {
		this.isExpLog = isExpLog;
	}

	public String getExpBody() {
		return expBody;
	}

	public void setExpBody(String expBody) {
		this.expBody = expBody;
	}

	public long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(long requestTime) {
		this.requestTime = requestTime;
	}

}
