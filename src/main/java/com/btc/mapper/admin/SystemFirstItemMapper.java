package com.btc.mapper.admin;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.admin.SystemFirstItem;

@Mapper
public interface SystemFirstItemMapper {

    @Select("SELECT a.* FROM system_first_item a WHERE a.status='1' order by a.order_index asc")
    @ResultMap("tableMap")
    List<SystemFirstItem> getListFirst();
    
    @Select("SELECT a.* FROM system_first_item a,u_user_role u WHERE a.id=u.first_item_id AND u.user_id=#{arg0} AND a.status='1' GROUP BY a.id ORDER BY a.order_index ASC")
    @ResultMap("tableMap")
    List<SystemFirstItem> getListFirstByUserId(int userId);
    

    List<SystemFirstItem> listSystemFirstItem(Map<String, Object> params);

    int pageCount(Map<String, Object> params);

    void addFirstItem(SystemFirstItem systemFirstItem);

    void updateFirstItem(SystemFirstItem systemFirstItem);
    
    void delItem(long id);
}
