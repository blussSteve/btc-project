package com.btc.model;

import java.math.BigDecimal;
import java.util.Date;

public class AssetAddRecord {
	private Long id;

    private String openId;

    private String coinCode;

    private BigDecimal coins;

    private BigDecimal orgTotalCoins;

    private String bindNo;

    private Date gmtModify;

    private Date gmtCreate;
    
    private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCoinCode() {
		return coinCode;
	}

	public void setCoinCode(String coinCode) {
		this.coinCode = coinCode;
	}

	public BigDecimal getCoins() {
		return coins;
	}

	public void setCoins(BigDecimal coins) {
		this.coins = coins;
	}

	public BigDecimal getOrgTotalCoins() {
		return orgTotalCoins;
	}

	public void setOrgTotalCoins(BigDecimal orgTotalCoins) {
		this.orgTotalCoins = orgTotalCoins;
	}

	public String getBindNo() {
		return bindNo;
	}

	public void setBindNo(String bindNo) {
		this.bindNo = bindNo;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
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
    
    
    
}