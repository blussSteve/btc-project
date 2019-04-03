package com.btc.global.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页工具
 *
 * @author henryyan
 */
public class PageUtil {

    public static Integer PAGE_SIZE = 15;

    public static <T> Page<T> getPage(HttpServletRequest request) {
        Page<T> page = new Page<T>(PAGE_SIZE);
        // 当前页数
        String currPage = request.getParameter("pageNo");
        // 判断每页显示记录数
        String pageSize = request.getParameter("pageSize");
        
        String orderBy=request.getParameter("orderBy");
        
        String order=request.getParameter("order");

        if (StringUtils.isNotBlank(currPage) && StringUtils.isNumeric(currPage)) {
            page.setPageNo(Integer.parseInt(currPage));
        } else {
            page.setPageNo(1);
        }
        if (StringUtils.isNotBlank(pageSize) && StringUtils.isNumeric(pageSize)) {
            page.setPageSize(Integer.parseInt(pageSize));
        } else {
            page.setPageSize(PAGE_SIZE);
        } 
        if(StringUtils.isNotBlank(orderBy)){
        	page.setOrderBy(orderBy);
        }
        if(StringUtils.isNotBlank(order)){
        	page.setOrder(order);
        }
        
        return page;
    }
    
    public static <T> Map<String,Object> parsePage(Map<String,Object> params,Page<T> page){
    	if(!StringUtils.isEmpty(page.getOrderBy())){
			params.put("orderBy", page.getOrderBy());
			params.put("order", page.getOrder());
		} 
		params.put("pageNo",page.getFirst());
		params.put("pageSize",page.getPageSize());
    	return params;
    }
}