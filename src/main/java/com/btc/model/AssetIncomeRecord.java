package com.btc.model;

import java.math.BigDecimal;
import java.util.Date;

public class AssetIncomeRecord {
    private Long id;

    private String coinCode;

    private BigDecimal coinIncome;

    private BigDecimal usdtInit;

    private BigDecimal usdtIncome;

    private Date countDate;

    private Date gmtCreate;
    
    public AssetIncomeRecord(){
    	coinIncome=BigDecimal.ZERO;
    	usdtInit=BigDecimal.ZERO;
    	usdtIncome=BigDecimal.ZERO;
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

    public BigDecimal getCoinIncome() {
        return coinIncome;
    }

    public void setCoinIncome(BigDecimal coinIncome) {
        this.coinIncome = coinIncome;
    }

    public BigDecimal getUsdtInit() {
        return usdtInit;
    }

    public void setUsdtInit(BigDecimal usdtInit) {
        this.usdtInit = usdtInit;
    }

    public BigDecimal getUsdtIncome() {
        return usdtIncome;
    }

    public void setUsdtIncome(BigDecimal usdtIncome) {
        this.usdtIncome = usdtIncome;
    }

    public Date getCountDate() {
        return countDate;
    }

    public void setCountDate(Date countDate) {
        this.countDate = countDate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}