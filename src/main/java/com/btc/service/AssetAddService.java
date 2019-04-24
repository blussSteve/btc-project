package com.btc.service;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.AssetAddRecord;
import com.btc.model.AssetAddRecordTemp;

public interface AssetAddService {

	Page<AssetAddRecord> queryAssetAddRecord(Page<AssetAddRecord> page,
			AssetAddRecord record);

	Page<AssetAddRecordTemp> queryAssetAddRecordTemp(
			Page<AssetAddRecordTemp> page, AssetAddRecordTemp record);

	JsonResult importAsset(HttpServletRequest req) throws FileNotFoundException;

	JsonResult shareAsset();

}
