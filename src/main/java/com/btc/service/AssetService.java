package com.btc.service;

import com.btc.global.json.JsonResult;
import com.btc.global.page.Page;
import com.btc.model.CoinRecord;
import com.btc.model.UserIncomeRecord;
import com.btc.model.UserIncomeVerify;
import com.btc.model.UserIncomeVerifyTemp;

public interface AssetService {

	Page<CoinRecord> queryCoinRecord(Page<CoinRecord> page,
			CoinRecord coinRecord);

	Page<UserIncomeRecord> queryCoinIncomeRecord(Page<UserIncomeRecord> page,
			UserIncomeRecord coinRecord);

	Page<UserIncomeVerify> queryUserIncomeVerifyRecord(
			Page<UserIncomeVerify> page, UserIncomeVerify coinRecord);

	JsonResult createUserIncomeVerifyTemp(int isSystemOperate, long operateId);

	JsonResult commitUserIncomeVerifyTemp(long userId);

	Page<UserIncomeVerifyTemp> queryUserIncomeVerifyTempRecord(
			Page<UserIncomeVerifyTemp> page, UserIncomeVerifyTemp coinRecord);

	JsonResult clearUserAsset(long userId);

	JsonResult clearAccountAsset(long userId, long accountId);

	JsonResult countCanUseAsset();

}
 