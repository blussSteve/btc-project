package com.btc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btc.model.admin.SystemFirstItem;

import java.util.List;
import java.util.Map;

public interface SystemService {

    List<Map<String,Object>> listItem(HttpServletRequest request, HttpServletResponse response);

    List<SystemFirstItem> mainlistItem(HttpServletRequest request, HttpServletResponse response);
}