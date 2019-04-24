package com.btc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import net.paoding.rose.web.annotation.Param;

import com.btc.model.AssetAddRecordTemp;

public interface AssetAddRecordTempMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AssetAddRecordTemp record);

    int insertSelective(AssetAddRecordTemp record);

    AssetAddRecordTemp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AssetAddRecordTemp record);

    int updateByPrimaryKey(AssetAddRecordTemp record);
    
    int batchInsert(@Param("list") List<AssetAddRecordTemp> list);
    
    
    List<AssetAddRecordTemp> queryAssetAddRecordTemp(Map<String,Object> map);
    
    int queryAssetAddRecordTempCount(Map<String,Object> map);
    
    @Delete("TRUNCATE btc_asset_add_record_temp")
    int truncateAssetAddRecordTemp();
    
    @Select("SELECT * FROM btc_asset_add_record_temp")
    @ResultMap("BaseResultMap")
    List<AssetAddRecordTemp> queryAll();
}