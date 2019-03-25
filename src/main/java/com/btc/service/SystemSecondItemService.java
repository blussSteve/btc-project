package com.btc.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.admin.SystemSecondItem;

public interface SystemSecondItemService {

    List<SystemSecondItem> listSystemSecondItem(SystemSecondItem systemSecondItem,Page<SystemSecondItem> page);

    int pageCount(SystemSecondItem systemSecondItem);

    int addSecondItem(SystemSecondItem systemSecondItem);

    JsonResult delItem(HttpServletRequest req,long id);
}