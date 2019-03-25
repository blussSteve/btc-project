package com.btc.service.impl.admin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btc.global.enums.admin.AdminRoleEnum;
import com.btc.mapper.admin.SystemFirstItemMapper;
import com.btc.mapper.admin.SystemSecondItemMapper;
import com.btc.model.admin.SystemFirstItem;
import com.btc.model.admin.SystemSecondItem;
import com.btc.model.admin.UUser;
import com.btc.service.SystemService;
import com.btc.util.Constants;

@Service
public class SystemServiceImpl implements SystemService {
	@Autowired
	private SystemSecondItemMapper systemSecondItemMapper;
	@Autowired
	private SystemFirstItemMapper systemFirstItemMapper;


	@Override
	public List<Map<String, Object>> listItem(HttpServletRequest request, HttpServletResponse response) {
		//1.获取管理员级别
		HttpSession session=request.getSession();
	    UUser user=(UUser)session.getAttribute(Constants.AUTH_USER);	
		List<SystemFirstItem>  listFirstItem=null;
		//如果是管理员用户
		if(AdminRoleEnum.SUP_ADMIN.getType()==user.getLevel()){
			listFirstItem=systemFirstItemMapper.getListFirst();
		}else{
			listFirstItem=systemFirstItemMapper.getListFirstByUserId(user.getId());
		}
		
		
		//2.查找符合级别已开启的一级菜单
		List<Map<String,Object>> listMap=new ArrayList<>();
		for (SystemFirstItem list:listFirstItem) {
			Map<String,Object> map1=new HashMap<>();
			map1.put("itemName",list.getItemName());
			map1.put("url",list.getUrl());
			map1.put("itemId", list.getId());
			List<Map<String,Object>> listMap2=new ArrayList<>();
			//3.根据一级菜单查找符合级别已开启的二级菜单
			List<SystemSecondItem>  listSecondItem=systemSecondItemMapper.getListSecondItem(list.getId());
			List<Map<String,Object>> listSecond=new ArrayList<>();
			for (SystemSecondItem systemSecondItem:listSecondItem) {
				Map<String,Object> map=new HashMap<>();
				map.put("itemName",systemSecondItem.getItemName());
				map.put("url",systemSecondItem.getUrl());
				map.put("itemId",systemSecondItem.getId());
				listSecond.add(map);
			}
			map1.put("totalItem",listSecond);
			listMap.add(map1);
		}
		return listMap;
	}

	@Override
	public List<SystemFirstItem> mainlistItem(HttpServletRequest request, HttpServletResponse response) {
		//1.获取管理员级别
		HttpSession session=request.getSession();
		 UUser user=(UUser)session.getAttribute(Constants.AUTH_USER);
		List<SystemFirstItem>  listFirstItem=null;
		//如果是管理员用户
		if(AdminRoleEnum.SUP_ADMIN.getType()==user.getLevel()){
			listFirstItem=systemFirstItemMapper.getListFirst();
		}else{
			listFirstItem=systemFirstItemMapper.getListFirstByUserId(user.getId());
		}
		return listFirstItem;
	}
}
