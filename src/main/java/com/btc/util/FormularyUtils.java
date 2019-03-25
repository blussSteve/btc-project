package com.btc.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 各种公式工具类
 * @author lixiaojun
 *
 */
public class FormularyUtils {
	/**
     * 计算今日份额
     * 今日份额 = 昨日份额 + 今日存取 / 今日净值
     * @param lastDayShare 昨日份额
     * @param todayAccess 今日存取
     * @param todayNetValue 今日净值
     * @return
     */
    public static BigDecimal countShare(BigDecimal lastDayShare, BigDecimal todayAccess, BigDecimal todayNetValue){
		return lastDayShare.add(todayAccess.divide(todayNetValue, 10, RoundingMode.HALF_DOWN));
    }
    
    /**
     * 计算今日净值
     * 今日净值 = （昨日份额 * 昨日净值 + 今日收益）/昨日份额
     * @param lastDayNetValue 昨日净值
     * @param lastDayShare 昨日份额
     * @param todayRevenue 今日损益
     * @return
     */
    public static BigDecimal countNetValue(BigDecimal lastDayNetValue, BigDecimal lastDayShare, BigDecimal todayRevenue){
    	return lastDayNetValue.multiply(lastDayShare).add(todayRevenue).divide(lastDayShare, 10, RoundingMode.HALF_DOWN);
    }

    /**
     * 卖出后计算成本
     * nowPrice－((holdCount*nowPrice－holdCount*oldCost)+sellIncome)/holdCount
     * @param holdCount 卖出后持有股数
     * @param oldCost 旧成本价
     * @param nowPrice 现价
     * @param sellCount 卖出的股数
     * @return
     */
    public static BigDecimal sellCost(BigDecimal holdCount,BigDecimal oldCost,BigDecimal nowPrice,BigDecimal sellCount){
		return nowPrice.subtract(holdCount.multiply(nowPrice)
				.subtract(holdCount.multiply(oldCost))
				.add(selledIncome(sellCount, oldCost, nowPrice))
				.divide(holdCount, 10, RoundingMode.HALF_DOWN));
	}
	
	/**
	 * 买入后计算成本
	 * @param holdCount 持有股数
	 * @param oldCost 旧成本价
	 * @param buyCount 买入的股数
	 * @param buyPrice 买入价
	 * @return
	 */
	public static BigDecimal buyCost(BigDecimal holdCount,BigDecimal oldCost,BigDecimal buyCount,BigDecimal buyPrice){
		return holdCount.multiply(oldCost).add(buyCount.multiply(buyPrice))
				.divide(holdCount.add(buyCount), 10, RoundingMode.HALF_DOWN);
	}
	
	/**
	 * 卖出的收益
	 * @param sellCount 卖出股数
	 * @param oldCost 旧成本价
	 * @param nowPrice 现价
	 * @return
	 */
	public static BigDecimal selledIncome(BigDecimal sellCount, BigDecimal oldCost, BigDecimal nowPrice){
		return nowPrice.subtract(oldCost).multiply(sellCount);
	}
    
    
    
    
    /**
     * 现资产 = 昨日总资产  + 今日收益
     * 现资产（无存取） = 今日净值 * 昨日份额 
     * 昨日总资产 = 昨日份额 * 昨日净值
     * 今日净值 = （昨日份额 * 昨日净值 + 今日收益）/昨日份额
     * 今日份额 = 昨日份额 + 今日存取 / 昨日净值
     * 
     */
}
