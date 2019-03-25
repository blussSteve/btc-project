package com.btc.service.impl.admin;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.btc.global.auth.AuthRoleEnum;
import com.btc.global.auth.AuthUser;
import com.btc.global.auth.TokenUtil;
import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.global.page.Page;
import com.btc.mapper.admin.SystemFirstItemMapper;
import com.btc.mapper.admin.SystemSecondItemMapper;
import com.btc.mapper.admin.UUserMapper;
import com.btc.mapper.admin.UUserRoleMapper;
import com.btc.model.admin.SystemFirstItem;
import com.btc.model.admin.SystemSecondItem;
import com.btc.model.admin.UUser;
import com.btc.model.admin.UUserRole;
import com.btc.service.AdminService;
import com.btc.service.RedisService;
import com.btc.util.Constants;
import com.btc.util.ObjectUtil;
import com.btc.util.password.PasswordUtil;


@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private UUserMapper adminMapper;
	@Autowired
	protected RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RedisService redisService;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SystemFirstItemMapper systemFirstItemMapper;
	
	@Autowired
	private SystemSecondItemMapper systemSecondItemMapper;
	
	@Autowired
	private  UUserRoleMapper uUserRoleMapper;
	@Override
	public String checkPassword(String userName, String password,HttpServletRequest request,HttpServletResponse response) {
		UUser admin=adminMapper.checkPassword(userName);
		if(admin !=null){
			if(PasswordUtil.equals(password, admin.getPassword())){
				if(admin.getIsLocked()==1){
					return "3";
				}else{
					HttpSession session=request.getSession();
					session.setAttribute("userName", admin.getUserName());
					session.setAttribute("level", admin.getLevel());
					session.setMaxInactiveInterval(720*60);
					
					//配置token
					String token=TokenUtil.generateToken(new AuthUser((long)admin.getId(), AuthRoleEnum.ADMIN.getRole(), new Timestamp(System.currentTimeMillis())));
					session.setAttribute(Constants.AUTH_TOKEN_NAME_DEFAULT, token);
					session.setAttribute(Constants.AUTH_USER, admin);
					
					adminMapper.updateAdminToken(token, admin.getId());
					return "1";
				}
			}
			return "2";
		}else{
			return "4";
		}
	}

	@Override
	public UUser message(String attribute) {
		UUser admin=adminMapper.message(attribute);
		return admin;
	}

	@Override
	public List<UUser> listAdmin(Page<UUser> page,UUser  admin) {
		Map<String,Object> params=ObjectUtil.bean2Map(admin);
		
		if(!StringUtils.isEmpty(page.getOrderBy())){
			params.put("orderBy", page.getOrderBy());
			params.put("order", page.getOrder());
		}else{
			params.put("orderBy", "id");
			params.put("order", "asc");
		}
		
		params.put("pageNo",page.getFirst());
		params.put("pageSize",page.getPageSize());
		List<UUser> list=adminMapper.listAdmin(params);
		return list;
	}

	@Override
	public int pageCount(UUser admin) {
		Map<String,Object> params=ObjectUtil.bean2Map(admin);
		int pageCount=adminMapper.pageCount(params);
		return pageCount;
	}

	@Override
	public JsonResult addAdmin(UUser user) {
		//判断用户名是否存在
		int j=adminMapper.checkUserName(user.getUserName());
		if(j>0){
			return JsonResultHelp.buildFail(RspCodeEnum.$2100);
		}
		adminMapper.addAdmin(user.getUserName(),PasswordUtil.encode(user.getPassword()),user.getMobile(),user.getFullname(),user.getIsLocked());
		
		return JsonResultHelp.buildSucc();
	}

	@Override
	public int getLevel(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		int i=adminMapper.getLevel((String)session.getAttribute("userName"));
		return i;
	}

	@Override
	public JsonResult editMessage(HttpServletRequest request,UUser user) {
		//判断用户权限
		HttpSession session=request.getSession();
		int l=adminMapper.getLevel((String)session.getAttribute("userName"));
		if(l<user.getLevel()){
			return JsonResultHelp.buildFail(RspCodeEnum.$2101);
		}
		
		//检查用户名
		List<UUser> ListUserName=adminMapper.checkUserNameById(user.getId());
		for (UUser admin : ListUserName) {
			if(admin.getUserName().equals(user.getUserName())){	
				return JsonResultHelp.buildFail(RspCodeEnum.$2100);
			}
		}
		//更新用户
		if(user.getPassword()==null || "".equals(user.getPassword())){
			adminMapper.updateAdminPartMessage(user.getId(),user.getUserName(),user.getMobile(),user.getFullname(),user.getLevel(),user.getIsLocked());
		}else{
			adminMapper.updateAdminMessage(user.getId(),user.getUserName(),user.getMobile(),user.getFullname(),user.getLevel(),PasswordUtil.encode(user.getPassword()),user.getIsLocked());
		}
		
		return JsonResultHelp.buildSucc();
	}
    
	@Override
	public JsonResult getUserRole(int userId){
		
		List<SystemFirstItem> listFirstItem=systemFirstItemMapper.getListFirst();
		
		List<SystemSecondItem> listSecondeItem= systemSecondItemMapper.getAllListSecondItem();
		
		
		List<UUserRole> listUserRole=uUserRoleMapper.queryUserRole(userId);
		
		JSONArray jarry=new JSONArray();
		
		
		for(SystemFirstItem item1:listFirstItem){
			
			//设置父类节点
			JSONObject jb=new JSONObject();
			
			jb.put("id", item1.getId());
			jb.put("pid", 0);
			jb.put("name", item1.getItemName());
			jb.put("isCheck", 0);
			
			jarry.add(jb);
			
			for(SystemSecondItem item2:listSecondeItem){
				
				if(item2.getFirstId()==item1.getId()){
					jb=new JSONObject();
					jb.put("id", item2.getId());
					jb.put("pid", item2.getFirstId());
					jb.put("name", item2.getItemName());
					jb.put("isCheck", 0);
					
					for(UUserRole urole:listUserRole){
						
						if(urole.getSecondItemId().equals(item2.getId())){
							jb.put("isCheck", 1);
						}
						
					}
					jarry.add(jb);
				}
				
				
			}
			
		}
		
		
		return JsonResultHelp.buildSucc(jarry);
		
	}
	
	@Override
	@Transactional
	public JsonResult setUserRole(List<UUserRole> listUserRole,int userId){
		
		
		for(UUserRole urole:listUserRole){
			urole.setUserId((long)userId);
		}
		
		uUserRoleMapper.deleteUserRole(userId);
		if(null!=listUserRole&&listUserRole.size()>0){
			uUserRoleMapper.batchInsert(listUserRole);
		}
		
		return JsonResultHelp.buildSucc();
	}
	
	@Override
	/**
	 * 设置用户的启用和禁用
	 * @param isLock
	 * @param userId
	 * @return
	 */
	public JsonResult setUUserIsLock(int isLock,int userId){
		
		adminMapper.setUUserIsLock(isLock, userId);
		
		return JsonResultHelp.buildSucc();
		
	}

}
