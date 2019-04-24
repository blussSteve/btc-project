package com.btc.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.btc.model.Account;
import com.btc.model.AccountBack;

public interface AccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
    
    @Select("SELECT * FROM btc_account")
    @ResultMap("BaseResultMap")
    List<Account> queryAll();
    
    void batchUpdateAccountIncome(@Param("list") List<Account> list);
    
    void batchUpdateAccountAssetIncome(@Param("list") List<Account> list);
    
    List<Account> queryAccount(Map<String,Object> params);
    
    int getAccountCount(Map<String,Object> params);
     
    
    @Select("SELECT * FROM btc_account t WHERE t.user_id=#{arg0}")
    @ResultMap("BaseResultMap")
    List<Account> queryUserAccount(long userId);
    
    @Select("SELECT * FROM btc_account t WHERE t.user_id=#{arg0} AND t.coin_code=#{arg1}")
    @ResultMap("BaseResultMap")
    Account getAccountByUser2Code(long userId,String code);
 
    @Update("UPDATE btc_account t SET t.coins=t.coins+#{arg0} WHERE t.id=#{arg1}")
    int updateAccount(String amount,long accountId);
    
    @Update("UPDATE btc_account t SET t.coins=t.coins-#{arg0} WHERE t.id=#{arg1}")
    int updateAccountSub(String amount,long accountId);
    
    @Update("UPDATE btc_account t SET t.status=#{arg1} WHERE t.id=#{arg0}")
    int updateAccountStatus(long accountId,int status);
    
    @Update("UPDATE btc_account t SET t.coins=0,t.can_use_coins=0 WHERE t.id=#{arg0}")
    int updateAccountAssetZero(long accountId);
    
    @Update("UPDATE btc_account t SET t.coins=t.coins-#{arg0},t.can_use_coins=t.can_use_coins-#{arg1} WHERE t.id=#{arg2}")
    int updateAccountAsset(BigDecimal totalCostAmount,BigDecimal curCostAmount,long accountId);
    
    @Update("UPDATE btc_account t SET t.can_use_coins=t.can_use_coins-#{arg0} WHERE t.id=#{arg1}")
    int updateAccountCanUseAmount(BigDecimal amount,long accountId);
    
    @Update("UPDATE btc_account t SET t.coins=0,t.today_income=0,t.today_real_income=0,t.can_use_coins=0,t.today_coins=0 WHERE t.id=#{arg0}")
    int clearAccountAsset(long accountId);
    
    List<Map<String,String>> queryAllAsset();
    

    List<Account> queryAccountInOpenIds(List<String> list);
    
    int batchUpdateAccountAssetCoins(@Param("list") List<Account> list);
    
    int batchInsert(@Param("list") List<Account> list);
    
    @Select("SELECT SUM(t.coins) FROM btc_account t WHERE t.coin_code=#{arg0}")
    BigDecimal getTotalCoins(String coinCode);
    
}