package com.btc.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserIncomeVerify {

	private Long id;

	private Long userId;

	private Long accountId;

	private BigDecimal coins;

	private BigDecimal coinIncome;

	private BigDecimal coinDayRate;

	private BigDecimal coinYearRate;

	private String coinCode;

	private Date countDate;

	private BigDecimal usdtUnit;

	private BigDecimal usdtIncome;

	private Integer isSystemOperate;

	private Long operateId;

	private String orderNo;

	private Date gmtCreate;

	private String userName;

	private String beginDate;

	private String endDate;

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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getCoins() {
		return coins;
	}

	public void setCoins(BigDecimal coins) {
		this.coins = coins;
	}

	public BigDecimal getCoinIncome() {
		return coinIncome;
	}

	public void setCoinIncome(BigDecimal coinIncome) {
		this.coinIncome = coinIncome;
	}

	public BigDecimal getCoinDayRate() {
		return coinDayRate;
	}

	public void setCoinDayRate(BigDecimal coinDayRate) {
		this.coinDayRate = coinDayRate;
	}

	public BigDecimal getCoinYearRate() {
		return coinYearRate;
	}

	public void setCoinYearRate(BigDecimal coinYearRate) {
		this.coinYearRate = coinYearRate;
	}

	public String getCoinCode() {
		return coinCode;
	}

	public void setCoinCode(String coinCode) {
		this.coinCode = coinCode;
	}

	public Date getCountDate() {
		return countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public BigDecimal getUsdtUnit() {
		return usdtUnit;
	}

	public void setUsdtUnit(BigDecimal usdtUnit) {
		this.usdtUnit = usdtUnit;
	}

	public BigDecimal getUsdtIncome() {
		return usdtIncome;
	}

	public void setUsdtIncome(BigDecimal usdtIncome) {
		this.usdtIncome = usdtIncome;
	}

	public Integer getIsSystemOperate() {
		return isSystemOperate;
	}

	public void setIsSystemOperate(Integer isSystemOperate) {
		this.isSystemOperate = isSystemOperate;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}