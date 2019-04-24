package com.btc.mapper;

import java.util.List;
import java.util.Map;

import net.paoding.rose.web.annotation.Param;

import com.btc.model.AssetAddRecord;

public interface AssetAddRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AssetAddRecord record);

    int insertSelective(AssetAddRecord record);

    AssetAddRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AssetAddRecord record);

    int updateByPrimaryKey(AssetAddRecord record);
    
    int batchInsert(@Param("list") List<AssetAddRecord> list);

    List<AssetAddRecord> queryAssetAddRecord(Map<String,Object> map);
    
    int queryAssetAddRecordCount(Map<String,Object> map);
    
    
    List<String> queryBindNos(@Param("list") List<String> list);
}