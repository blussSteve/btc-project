package com.btc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.AssetTotal;
import com.btc.model.CoinRateRecord;
import com.btc.model.admin.SysCoinsDic;
import com.btc.model.admin.SystemFirstItem;

import java.util.List;
import java.util.Map;

public interface SystemService {

    List<Map<String,Object>> listItem(HttpServletRequest request, HttpServletResponse response);

    List<SystemFirstItem> mainlistItem(HttpServletRequest request, HttpServletResponse response);

	Page<SysCoinsDic> querySysCoinsDic(Page<SysCoinsDic> page, SysCoinsDic dic);

	JsonResult saveOrUpdateSysCoinsDic(SysCoinsDic dic);

	JsonResult delSysCoinsDic(long dicId);

	JsonResult checkCoinCodeIsExist(String coinCode);

	JsonResult updateSysCoinsDicStatus(long id, int status);

	Page<CoinRateRecord> queryCoinRate(Page<CoinRateRecord> page,
			CoinRateRecord coinRateRecord);

	JsonResult saveOrUpdateCoinRate(CoinRateRecord coinRateRecord);

	JsonResult checkCoinRateIsExist(String code, String dateStr);

	List<SysCoinsDic> queryAllCoins();

	JsonResult delCoinRate(long id);

	Page<AssetTotal> queryAssetTotal(Page<AssetTotal> page);

	JsonResult queryAssetTotalKline();
}