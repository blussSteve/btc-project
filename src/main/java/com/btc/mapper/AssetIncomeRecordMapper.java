package com.btc.mapper;

import java.util.Date;
import java.util.List;

import net.paoding.rose.web.annotation.Param;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.AssetIncomeRecord;

public interface AssetIncomeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AssetIncomeRecord record);

    int insertSelective(AssetIncomeRecord record);

    AssetIncomeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AssetIncomeRecord record);

    int updateByPrimaryKey(AssetIncomeRecord record);
    
    void batchInsert(@Param("list") List<AssetIncomeRecord> list);
    
    @Delete("DELETE t FROM btc_asset_income_record t WHERE DATE(t.count_date)=DATE(#{arg0})")
    void deleAssetIncomeRecord(Date date);
    
    @Select("SELECT * FROM btc_asset_income_record t WHERE DATE(t.count_date)>DATE_ADD(NOW(),INTERVAL #{arg0} DAY)")
    @ResultMap("BaseResultMap")
    List<AssetIncomeRecord> queryAssetIncomeRecord(int day);
    
    
}