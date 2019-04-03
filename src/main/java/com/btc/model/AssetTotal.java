package com.btc.model;

import java.math.BigDecimal;
import java.util.Date;

public class AssetTotal {
    private Long id;

    private String coinCode;

    private BigDecimal coins;

    private BigDecimal todayIncome;

    private BigDecimal totalIncome;

    private BigDecimal yesterdayCoins;

    private Date gmtCreate;

    private Date gmtModify;
    
    private String coinIcon;
    public AssetTotal(){
    	coins=BigDecimal.ZERO;
    	todayIncome=BigDecimal.ZERO;
    	totalIncome=BigDecimal.ZERO;
    	yesterdayCoins=BigDecimal.ZERO;
    	
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(BigDecimal todayIncome) {
        this.todayIncome = todayIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getYesterdayCoins() {
        return yesterdayCoins;
    }

    public void setYesterdayCoins(BigDecimal yesterdayCoins) {
        this.yesterdayCoins = yesterdayCoins;
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

	public String getCoinIcon() {
		return coinIcon;
	}

	public void setCoinIcon(String coinIcon) {
		this.coinIcon = coinIcon;
	}
    
}