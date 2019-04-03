package com.btc.mapper;

import java.util.List;
import java.util.Map;

import net.paoding.rose.web.annotation.Param;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.AssetTotal;

public interface AssetTotalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AssetTotal record);

    int insertSelective(AssetTotal record);

    AssetTotal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AssetTotal record);

    int updateByPrimaryKey(AssetTotal record);
    
    @Select("SELECT * FROM btc_asset_total")
    @ResultMap("BaseResultMap")
    List<AssetTotal> queryAll();
    
    void batchInsert(@Param("list") List<AssetTotal> list);
    
    @Delete("TRUNCATE btc_asset_total")
    void delAll();
    
    List<AssetTotal> queryCoinAssetTotal(Map<String,Object> map);
    
    int getCoinAssetTotalCount(Map<String,Object> map);
}