package com.btc.pojo.excel;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class AssetModel extends BaseRowModel {

	@ExcelProperty(index = 0)
	private String openId;
	
	@ExcelProperty(index = 1)
	private String coinCode;
	
	@ExcelProperty(index = 2)
	private BigDecimal coins;
	
	@ExcelProperty(index = 3)
	private String orderNo;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	

}
