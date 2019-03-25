package com.btc.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.admin.SystemFirstItem;

public interface SystemFirstItemService {

    List<SystemFirstItem> listSystemFirstItem(Page<SystemFirstItem> page,SystemFirstItem systemFirstItem);

    int pageCount(SystemFirstItem systemFirstItem);

    int addFirstItem(SystemFirstItem system);

	JsonResult delItem(HttpServletRequest req,long id);
}