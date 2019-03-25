package com.btc.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.admin.SystemSecondItem;

@Mapper
public interface SystemSecondItemMapper {

    @Select("SELECT * FROM system_second_item a WHERE a.status='1' and a.first_id=#{arg1} order by a.order_index asc ")
    @ResultMap("tableMap")
    List<SystemSecondItem> getListSecondItem(long id);
    
    
    @Select("SELECT * FROM system_second_item a WHERE a.status='1' order by a.order_index asc ")
    @ResultMap("tableMap")
    List<SystemSecondItem> getAllListSecondItem();

    List<SystemSecondItem> listSystemSecondItem(Map<String, Object> params);

    int pageCount(Map<String, Object> params);

    void insertSecondItem(SystemSecondItem systemSecondItem);

    void updateSecondItem(SystemSecondItem systemSecondItem);
    
    void delItem(long itemId);
}
