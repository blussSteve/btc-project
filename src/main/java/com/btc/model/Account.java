package com.btc.model;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
   private Long id;

    private Long userId;

    private BigDecimal coins;

    private BigDecimal canUseCoins;

    private String coinCode;

    private BigDecimal todayCoins;

    private BigDecimal todayIncome;

    private BigDecimal todayRealIncome;

    private BigDecimal totalIncome;

    private Integer status;

    private Date gmtCreate;

    private Date gmtModify;
    
    private String openId;
    
    private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getCoins() {
		return coins;
	}

	public void setCoins(BigDecimal coins) {
		this.coins = coins;
	}

	public BigDecimal getCanUseCoins() {
		return canUseCoins;
	}

	public void setCanUseCoins(BigDecimal canUseCoins) {
		this.canUseCoins = canUseCoins;
	}

	public String getCoinCode() {
		return coinCode;
	}

	public void setCoinCode(String coinCode) {
		this.coinCode = coinCode;
	}

	public BigDecimal getTodayCoins() {
		return todayCoins;
	}

	public void setTodayCoins(BigDecimal todayCoins) {
		this.todayCoins = todayCoins;
	}

	public BigDecimal getTodayIncome() {
		return todayIncome;
	}

	public void setTodayIncome(BigDecimal todayIncome) {
		this.todayIncome = todayIncome;
	}

	public BigDecimal getTodayRealIncome() {
		return todayRealIncome;
	}

	public void setTodayRealIncome(BigDecimal todayRealIncome) {
		this.todayRealIncome = todayRealIncome;
	}

	public BigDecimal getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    
}