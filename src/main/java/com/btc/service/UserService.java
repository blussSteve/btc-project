package com.btc.service;

import java.util.Map;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.Account;
import com.btc.model.AccountBack;
import com.btc.model.CoinRecord;
import com.btc.model.UserInfo;

public interface UserService {


	Page<UserInfo> queryUserInfo(Page<UserInfo> page, UserInfo user);

	Page<Account> queryAccountInfo(Page<Account> page, Account account);

	JsonResult getAssetList(long userId);

	Page<Map<String, String>> queryCoinRecord(Page<Map<String, String>> page,CoinRecord coinRecord);

	JsonResult getAssetInfo(long userId);

	JsonResult userLogin(String accessCode);

	JsonResult trade(String openId, long userId, String amount,
			String assetCode, int tradeType);

	JsonResult queryAllAsset();

	Page<AccountBack> queryAccountHisInfo(Page<AccountBack> page,
			AccountBack obj);

}
