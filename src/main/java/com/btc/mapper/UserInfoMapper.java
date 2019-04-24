package com.btc.mapper;

import java.util.List;
import java.util.Map;

import net.paoding.rose.web.annotation.Param;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.btc.model.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    
    List<UserInfo> queryUserInfo(Map<String,Object> params);
    
    int getUserInfoCount(Map<String,Object> params);
    
    @Select("SELECT * FROM btc_user_info t WHERE t.open_id=#{arg0}")
    @ResultMap("BaseResultMap")
    UserInfo getUserInfo(String openId);
    
    public List<String> queryInOpenId(List<String> list);
   
    public List<UserInfo> queryUserInOpenId(List<String> list);
}