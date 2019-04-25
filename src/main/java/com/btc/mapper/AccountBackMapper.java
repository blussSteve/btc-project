package com.btc.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.Account;
import com.btc.model.AccountBack;

public interface AccountBackMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AccountBack record);

    int insertSelective(AccountBack record);

    AccountBack selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountBack record);

    int updateByPrimaryKey(AccountBack record);
    
    void batchInsert(List<AccountBack> list);
    
    @Delete("DELETE t FROM btc_account_back t WHERE t.count_date< DATE_ADD(t.count_date,INTERVAL #{arg0} DAY)")
    void delAccountLastDay(int day);
    
    @Delete("DELETE t FROM btc_account_back t WHERE t.count_date=DATE(#{arg0})")
    void delAccountByDay(Date date);
    
    @Select("SELECT * FROM btc_account_back t WHERE DATE(t.count_date)=DATE(#{arg0})")
    @ResultMap("BaseResultMap")
    List<AccountBack> queryAll(Date date);
    
    List<AccountBack> queryAccountHis(Map<String,Object> params);
    
    int getAccountHisCount(Map<String,Object> params);
    
    int batchUpdateAccountAssetCoins(@Param("list") List<Account> list);
}