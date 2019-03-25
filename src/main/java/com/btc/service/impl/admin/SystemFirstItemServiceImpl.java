package com.btc.service.impl.admin;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.global.page.Page;
import com.btc.mapper.admin.SystemFirstItemMapper;
import com.btc.model.admin.SystemFirstItem;
import com.btc.service.SystemFirstItemService;
import com.btc.util.Constants;
import com.btc.util.ObjectUtil;

@Service
public class SystemFirstItemServiceImpl implements SystemFirstItemService {
   @Autowired
    private SystemFirstItemMapper systemFirstItemMapper;
    @Override
    public List<SystemFirstItem> listSystemFirstItem(Page<SystemFirstItem> page,SystemFirstItem systemFirstItem) {
    	Map<String,Object> params=ObjectUtil.bean2Map(systemFirstItem);
    	
    	if(!StringUtils.isEmpty(page.getOrderBy())){
			params.put("orderBy", page.getOrderBy());
			params.put("order", page.getOrder());
		}else{
			params.put("orderBy", "id");
			params.put("order", "asc");
		}
		
		params.put("pageNo",page.getFirst());
		params.put("pageSize",page.getPageSize());
        List<SystemFirstItem> list=systemFirstItemMapper.listSystemFirstItem(params);
        return list;
    }

    @Override
    public int pageCount(SystemFirstItem systemFirstItem) {
    	Map<String,Object> params=ObjectUtil.bean2Map(systemFirstItem);
        int i=systemFirstItemMapper.pageCount(params);
        return  i;
    }

    @Override
    public int addFirstItem(SystemFirstItem system) {
        SystemFirstItem systemFirstItem=new SystemFirstItem();
        systemFirstItem.setItemName(system.getItemName());
        systemFirstItem.setStatus(system.getStatus());
        systemFirstItem.setOrderIndex(system.getOrderIndex());
        systemFirstItem.setLevel(system.getLevel());
        systemFirstItem.setId(system.getId());
        if(system.getId()==null||system.getId()==0){
            //新增
            systemFirstItemMapper.addFirstItem(systemFirstItem);
        }else{
            //更新
            systemFirstItemMapper.updateFirstItem(systemFirstItem);
        }
        return 1;
    }
    
    @Override
    public JsonResult delItem(HttpServletRequest req,long id){
    	int level=(int)req.getSession().getAttribute("level");
    	if(level==1){
    		return JsonResultHelp.buildFail(RspCodeEnum.$2101);
    	}
    	systemFirstItemMapper.delItem(id);
    	return JsonResultHelp.buildSucc();
    }

}
