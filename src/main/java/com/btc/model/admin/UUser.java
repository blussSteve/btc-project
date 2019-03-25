package com.btc.model.admin;

import java.io.Serializable;

public class UUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2719654266902107061L;

	/**主键ID**/
	private Integer id;

	/**用户名**/
	private String userName;

	/**密码**/
	private String password;

	/**密码是否过期(0-正常,1-已过期)**/
	private Integer isExpired;

	/**是否锁定(0-正常,1-锁定)**/
	private Integer isLocked;

	/**是否禁用(0-否,1-是)**/
	private Integer isEnable;

	/**手机号**/
	private String mobile;

	/**用户全称**/
	private String fullname;

	/**最后登陆时间**/
	private java.util.Date lastLoginTime;

	/**登录错误次数**/
	private Integer loginErrorCount;

	/**创建时间**/
	private String createTime;

	/**渠道【默认是0，代表所有渠道】**/
	private String channel;

	/**权限**/
	private int level;

    /**搜索关键字**/
	private String key;

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return this.password;
	}

	public void setIsExpired(Integer isExpired){
		this.isExpired = isExpired;
	}

	public Integer getIsExpired(){
		return this.isExpired;
	}

	public void setIsLocked(Integer isLocked){
		this.isLocked = isLocked;
	}

	public Integer getIsLocked(){
		return this.isLocked;
	}

	public void setIsEnable(Integer isEnable){
		this.isEnable = isEnable;
	}

	public Integer getIsEnable(){
		return this.isEnable;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setFullname(String fullname){
		this.fullname = fullname;
	}

	public String getFullname(){
		return this.fullname;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public java.util.Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLoginErrorCount(Integer loginErrorCount){
		this.loginErrorCount = loginErrorCount;
	}

	public Integer getLoginErrorCount(){
		return this.loginErrorCount;
	}

	public void setChannel(String channel){
		this.channel = channel;
	}

	public String getChannel(){
		return this.channel;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


}
