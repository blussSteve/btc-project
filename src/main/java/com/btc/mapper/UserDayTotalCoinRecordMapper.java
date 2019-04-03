package com.btc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.btc.model.UserDayTotalCoinRecord;

public interface UserDayTotalCoinRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDayTotalCoinRecord record);

    int insertSelective(UserDayTotalCoinRecord record);

    UserDayTotalCoinRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDayTotalCoinRecord record);

    int updateByPrimaryKeyWithBLOBs(UserDayTotalCoinRecord record);

    int updateByPrimaryKey(UserDayTotalCoinRecord record);
    
    @Select("SELECT * FROM btc_user_day_total_coin_record t WHERE  t.coins>0 AND DATE(t.count_date)=DATE(#{arg0})")
    @ResultMap("BaseResultMap")
    List<UserDayTotalCoinRecord> queryUserDayTotalCoinRecord(Date date);
    
    @Select("SELECT * FROM btc_user_day_total_coin_record t WHERE t.user_id=#{arg0} AND t.account_id=#{arg1} AND t.coin_code=#{arg2} AND DATE(t.count_date)=DATE(#{arg3})")
    @ResultMap("BaseResultMap")
    UserDayTotalCoinRecord getUserDayTotalCoinRecord(long userId,long accountId,String coinCode,Date date);
    
    @Update("UPDATE btc_user_day_total_coin_record t SET t.coins=t.coins+#{arg0} WHERE t.user_id=#{arg1} AND t.account_id=#{arg2} AND t.coin_code=#{arg3} AND DATE(t.count_date)=DATE(#{arg4})")
    int updateUserDayTotalCoinRecord(String amount, long userId,long accountId,String coinCode,Date date);
}