package com.btc.service.impl.admin;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btc.global.json.JsonResult;
import com.btc.global.json.JsonResultHelp;
import com.btc.global.json.enums.RspCodeEnum;
import com.btc.global.page.Page;
import com.btc.mapper.admin.SystemSecondItemMapper;
import com.btc.model.admin.SystemSecondItem;
import com.btc.service.SystemSecondItemService;
import com.btc.util.ObjectUtil;

@Service
public class SystemSecondItemServiceImpl implements SystemSecondItemService {
   @Autowired
    private SystemSecondItemMapper systemSecondItemMapper;
    @Override
    public List<SystemSecondItem> listSystemSecondItem(SystemSecondItem systemSecondItem ,Page<SystemSecondItem> page) {
        Map<String,Object> params= ObjectUtil.bean2Map(systemSecondItem);
//        params.put("pageNo",page.getFirst());
//        params.put("pageSize",page.getPageSize());
        List<SystemSecondItem> list=systemSecondItemMapper.listSystemSecondItem(params);
        return list;
    }

    @Override
    public int pageCount(SystemSecondItem systemSecondItem) {
        Map<String,Object> params= ObjectUtil.bean2Map(systemSecondItem);
        int i=systemSecondItemMapper.pageCount(params);
        return  i;
    }

    @Override
    public int addSecondItem(SystemSecondItem systemSecondItem) {
        if(systemSecondItem.getId()==null){
            //新增
            systemSecondItemMapper.insertSecondItem(systemSecondItem);
        }else{
            //更新
            systemSecondItemMapper.updateSecondItem(systemSecondItem);
        }
        return 1;
    }
    
    @Override
    public JsonResult delItem(HttpServletRequest req,long id){
    	int level=(int)req.getSession().getAttribute("level");
    	if(level==1){
    		return JsonResultHelp.buildFail(RspCodeEnum.$2101);
    	}
    	systemSecondItemMapper.delItem(id);
    	return JsonResultHelp.buildSucc();
    }

}
