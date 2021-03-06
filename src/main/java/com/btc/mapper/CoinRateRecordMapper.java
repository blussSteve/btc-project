package com.btc.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.CoinRateRecord;

public interface CoinRateRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table btc_coin_rate_record
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table btc_coin_rate_record
     *
     * @mbggenerated
     */
    int insert(CoinRateRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table btc_coin_rate_record
     *
     * @mbggenerated
     */
    int insertSelective(CoinRateRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table btc_coin_rate_record
     *
     * @mbggenerated
     */
    CoinRateRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table btc_coin_rate_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CoinRateRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table btc_coin_rate_record
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CoinRateRecord record);
    
    
    List<CoinRateRecord> queryCoinRate(Map<String,Object> map);
    
    int getCoinRateCount(Map<String,Object> map);
    
    @Select("SELECT * FROM btc_coin_rate_record t WHERE t.coin_code=#{arg0} AND t.apply_date=#{arg1}")
    @ResultMap("BaseResultMap")
    CoinRateRecord getCoinRate(String coinCode,Date date);
    
    
    /**
     * 获取收益配置信息
     * @param date
     * @return
     */
    @Select("SELECT * FROM btc_coin_rate_record t WHERE DATE(t.apply_date)=DATE(#{arg0}) AND t.status=1")
    @ResultMap("BaseResultMap")
    List<CoinRateRecord> queryCoinRateByDate(String date);
    
}