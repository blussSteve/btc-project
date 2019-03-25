package com.btc.mapper.admin;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.btc.model.admin.UUser;
@Mapper
public interface UUserMapper {

	@Select("SELECT id,user_name,PASSWORD,is_expired,is_locked,is_enable,mobile,fullname,level FROM u_user WHERE user_name=#{arg0}")
	@ResultMap("tableMap")
	UUser checkPassword(String userName);

	@Select("SELECT id,user_name,PASSWORD,is_expired,is_locked,is_enable,mobile,fullname,level FROM u_user WHERE user_name=#{arg0}")
	@ResultMap("tableMap")
	UUser message(String attribute);

	List<UUser> listAdmin(Map<String, Object> params);

	int pageCount(Map<String, Object> params);

	@Insert("INSERT INTO u_user (user_name,PASSWORD,mobile,fullname,is_locked,create_time,level) VALUES (#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},NOW(),2)")
	int addAdmin(String userName, String password, String mobile,
			String realName, Integer integer);

	@Select("select count(1) from u_user where user_name=#{arg0}")
	int checkUserName(String userName);

	@Select("SELECT LEVEL FROM u_user WHERE user_name=#{arg0}")
	int getLevel(String attribute);

	@Select("select user_name from u_user where id <>#{arg0} ")
	@ResultMap("tableMap")
	List<UUser> checkUserNameById(Integer integer);

	@Update("UPDATE u_user SET user_name=#{arg1},mobile=#{arg2},fullname=#{arg3},LEVEL=#{arg4},PASSWORD=#{arg5},is_locked=#{arg6} WHERE id=#{arg0}")
	int updateAdminMessage(Integer integer, String userName, String mobile,
			String realName, int level, String password,int status);

	@Update("UPDATE u_user SET user_name=#{arg1},mobile=#{arg2},fullname=#{arg3},LEVEL=#{arg4},is_locked=#{arg5} WHERE id=#{arg0}")
	int updateAdminPartMessage(Integer integer, String userName, String mobile,
			String realName, int level, int status);


	@Select("SELECT id,user_name,is_expired,is_locked,is_enable,mobile,fullname,create_time FROM `u_user` WHERE user_name=#{arg0}")
	@ResultMap("tableMap")
	UUser getAdminMessageByUserName(String userName);
	
	@Update("UPDATE u_user t SET t.token=#{arg0},t.last_login_time=NOW() WHERE t.id=#{arg1}")
	int updateAdminToken(String token,int userId);
	
	@Update("UPDATE u_user t SET t.is_locked=#{arg0} WHERE t.id=#{arg1}")
	int setUUserIsLock(int isLock,int userId);
}
