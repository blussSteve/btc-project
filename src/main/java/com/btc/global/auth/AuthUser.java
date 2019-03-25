package com.btc.global.auth;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 认证用户信息
 * @author admin
 *
 */
public class AuthUser implements Serializable{

	private static final long serialVersionUID = 7533637902119487862L;

	private Long userId;

	private String role;
	
    private Timestamp loginTime;
    
    public AuthUser() {
		
	}
    
	public AuthUser(Long userId, String role, Timestamp loginTime) {
		this.userId = userId;
		this.role = role;
		this.loginTime = loginTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}



    
}
