//package com.btc.util;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.btc.model.Position;
//import com.btc.model.Stock;
//import com.btc.mapper.StockMapper;
//import com.btc.pojo.StockInfo;
//import com.btc.service.RedisService;
//import com.btc.service.StockService;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 股票信息工具类
// * @author Administrator
// *
// */
//@Component
//public class StockInfoUtils {
//	private static final Logger LOGGER = LoggerFactory.getLogger(StockInfoUtils.class);
//	private static final ObjectMapper MAPPER = new ObjectMapper();
//
//	@Autowired
//	private StockMapper stockMapper;
//	@Autowired
//	private RedisService redisService;
//	@Autowired
//	private StockService stockService;
//
//
//	private static StockInfoUtils stockInfoUtils;
//
//
//	@PostConstruct
//	public void init() {
//		stockInfoUtils = this;
//		stockInfoUtils.stockMapper = this.stockMapper;
//		stockInfoUtils.redisService = this.redisService;
//		stockInfoUtils.stockService = this.stockService;
//
//	}
//
//	/**
//	 * 获取股票名称
//	 * @param stockFullCode
//	 * @return
//	 */
//	public static String getStockName(String stockFullCode){
//		if(StringUtils.isBlank(stockFullCode)){
//			return "";
//		}
//		stockFullCode = stockFullCode.toLowerCase();
//		RedisService redisService = (RedisService) BeanTools.getBean(RedisService.class);
//		if(!redisService.exists(Constants.REDIS_STOCK_PREFIX + stockFullCode)){
//			return "";
//		}
//		String stockStr = redisService.get(Constants.REDIS_STOCK_PREFIX + stockFullCode);
//		if(StringUtils.isBlank(stockStr)){
//			return "";
//		}
//		try {
//			StockInfo stockInfo = MAPPER.readValue(stockStr, StockInfo.class);
//			return stockInfo.getName();
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//	
//	/**
//	 * 获取股票信息
//	 * @param stockCode 股票代码
//	 * @param stockType 股票类型
//	 * @return
//	 */
//	public static List<StockInfo> getStockInfo(String stockCode, byte stockType){
//		if(StringUtils.isBlank(stockCode)){
//			return null;
//		}
//		return parseSinaStockJson(getFromSina(stockCode, stockType));
////		return parseBaiduStockJson(getFromBaidu(stockCode, stockType));
//	}
//
//	/**
//	 * 从百度获取股票信息
//	 * @param stockCode 股票代码（多个用,分隔，最多10个）
//	 * @param stockType 股票类型
//	 * @return
//	 */
//	private static String getFromBaidu(String stockCode, byte stockType){
//		stockCode = stockCode.toLowerCase();
//		String httpUrl = null;
//		switch(stockType){
//		case Constants.STOCK_TYPE_SH:
//			httpUrl = "http://apis.baidu.com/apistore/stockservice/stock";
//			break;
//		case Constants.STOCK_TYPE_HK:
//			httpUrl = "http://apis.baidu.com/apistore/stockservice/hkstock";
//			break;
//		case Constants.STOCK_TYPE_USA:
//			httpUrl = "http://apis.baidu.com/apistore/stockservice/usastock";
//			break;
//		}
//		
//		String httpArg = "stockid=" + stockCode + "&list=1";
//		BufferedReader reader = null;
//	    String result = null;
//	    StringBuffer sbf = new StringBuffer();
//	    httpUrl = httpUrl + "?" + httpArg;
//
//	    try {
//	        URL url = new URL(httpUrl);
//	        HttpURLConnection connection = (HttpURLConnection) url
//	                .openConnection();
//	        connection.setRequestMethod("GET");
//	        // 填入apikey到HTTP header
//	        connection.setRequestProperty("apikey",  "3f5b7c21cfc92758009675051bdc447f");
//	        connection.connect();
//	        InputStream is = connection.getInputStream();
//	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//	        String strRead = null;
//	        while ((strRead = reader.readLine()) != null) {
//	            sbf.append(strRead);
//	            sbf.append("\r\n");
//	        }
//	        reader.close();
//	        result = sbf.toString();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	    return result;
//	}
//	
//	private static List<StockInfo> parseBaiduStockJson(String stockInfoJson){
//		Map<String, Object> stockMap = null;
//		try {
//			stockMap = MAPPER.readValue(stockInfoJson, Map.class);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if(stockMap == null){
//			return null;
//		}
//		stockMap = (Map)stockMap.get("retData");
//		List<Map> stockMapList = (List)stockMap.get("stockinfo");
//		List<StockInfo> stockList = new ArrayList<>(stockMapList.size());
//		StockInfo stock = null;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		for(Map oneStockMap : stockMapList){
//			stock = new StockInfo();
//			stock.setName(oneStockMap.get("name").toString());
//			stock.setFullCode(oneStockMap.get("code").toString());
//			stock.setNowPrice(BigDecimal.valueOf(Double.parseDouble(oneStockMap.get("currentPrice").toString())));
//			stock.setHighLimit(getHighLimit(oneStockMap.get("closingPrice").toString()));
//			stock.setLowLimit(getLowLimit(oneStockMap.get("closingPrice").toString()));
//			try {
//				stock.setUpdateTime(new Timestamp(dateFormat.parse(oneStockMap.get("date").toString() + " " + oneStockMap.get("time").toString()).getTime()));
//			} catch (ParseException e) {
//				stock.setUpdateTime(new Timestamp(0));
//				LOGGER.error("can not parse date[" + oneStockMap.get("date").toString() + " " + oneStockMap.get("time").toString() + "]");
//			}
//			stock.setBuy5(new ArrayList<Map<String, String>>());
//			stock.setSell5(new ArrayList<Map<String, String>>());
//			stock.setBuyOnePrice(null);
//			stock.setSellOnePrice(null);
//			stockList.add(stock);
//		}
//		return stockList;
//	}
//	
//	/**
//	 * 从新浪获取股票信息
//	 * @param stockCode 股票代码（多个用,分隔，最多10个）
//	 * @param stockType 股票类型
//	 * @return
//	 */
//	private static String getFromSina(String stockCode, byte stockType){
//		stockCode = stockCode.toLowerCase();
//		return HttpClientUtils.get("http://hq.sinajs.cn/list=" + stockCode);
//	}
//	
//	private static List<StockInfo> parseSinaStockJson(String stockInfoStr){
//		List<StockInfo> stockList = new ArrayList<>();
//		if(StringUtils.isBlank(stockInfoStr)){
//			return stockList;
//		}
//		String[] stockInfoStrArray = StringUtils.split(stockInfoStr, ";");
//		if(stockInfoStrArray.length == 0){
//			return stockList;
//		}
//		StockInfo stock = null;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		for(String oneStockInfoStr : stockInfoStrArray){
//			if(StringUtils.isBlank(oneStockInfoStr) || oneStockInfoStr.length() < 30){
//				continue;
//			}
//			stock = new StockInfo();
//			stock.setCode(oneStockInfoStr.split("=")[0].substring(oneStockInfoStr.split("=")[0].length() - 8));
//			stock.setFullCode(stock.getCode());
//			oneStockInfoStr = oneStockInfoStr.split("=")[1];
//			oneStockInfoStr = oneStockInfoStr.substring(1, oneStockInfoStr.length() - 1);
//			String[] stockDetail = oneStockInfoStr.split(",");
//			stock.setName(stockDetail[0]);
//			//当前价
//			BigDecimal nowPrice = BigDecimal.valueOf(Double.parseDouble(stockDetail[3]));
//			//昨收
//			BigDecimal yestodEndPri = BigDecimal.valueOf(Double.parseDouble(stockDetail[2]));
//			//若当前价为0则取昨收
//			if (nowPrice.compareTo(BigDecimal.ZERO) == 0) {
//				/*Stock stockDB = stockInfoUtils.stockMapper.getByCode(stock.getFullCode());
//				if (stockDB != null) {
//					nowPrice = stockDB.getClosePrice();
//				}*/
//				nowPrice = yestodEndPri;
//			}
//			stock.setNowPrice(nowPrice);
//			stock.setHighLimit(getHighLimit(stockDetail[2]));
//			stock.setLowLimit(getLowLimit(stockDetail[2]));
//			try {
//				stock.setUpdateTime(new Timestamp(dateFormat.parse(stockDetail[30] + " " + stockDetail[31]).getTime()));
//			} catch (Exception e) {
//				stock.setUpdateTime(new Timestamp(0));
//				LOGGER.error("can not parse date[" + stockDetail[30] + " " + stockDetail[31] + "]");
//			}
//			stock.setBuy5(new ArrayList<Map<String, String>>(5));
//			stock.getBuy5().add(Parser.parsePriceItem("买一", stockDetail[11], stockDetail[10]));
//			stock.getBuy5().add(Parser.parsePriceItem("买二", stockDetail[13], stockDetail[12]));
//			stock.getBuy5().add(Parser.parsePriceItem("买三", stockDetail[15], stockDetail[14]));
//			stock.getBuy5().add(Parser.parsePriceItem("买四", stockDetail[17], stockDetail[16]));
//			stock.getBuy5().add(Parser.parsePriceItem("买五", stockDetail[19], stockDetail[18]));
//			stock.setSell5(new ArrayList<Map<String, String>>(5));
//			stock.getSell5().add(Parser.parsePriceItem("卖一", stockDetail[21], stockDetail[20]));
//			stock.getSell5().add(Parser.parsePriceItem("卖二", stockDetail[23], stockDetail[22]));
//			stock.getSell5().add(Parser.parsePriceItem("卖三", stockDetail[25], stockDetail[24]));
//			stock.getSell5().add(Parser.parsePriceItem("卖四", stockDetail[27], stockDetail[26]));
//			stock.getSell5().add(Parser.parsePriceItem("卖五", stockDetail[29], stockDetail[28]));
//			stock.setBuyOnePrice(new BigDecimal(stockDetail[11]));
//			stock.setSellOnePrice(new BigDecimal(stockDetail[21]));
//			
//			stock.setTodayStartPri(""+stockDetail[1]);
//			stock.setYestodEndPri(""+stockDetail[2]);
//			stock.setTodayMax(""+stockDetail[4]);
//			stock.setTodayMin(""+stockDetail[5]);
//			stock.setCompetitivePri(""+stockDetail[6]);
//			stock.setReservePri(""+stockDetail[7]);
//			stock.setTraNumber(""+stockDetail[8]);
//			stock.setTraAmount(""+stockDetail[9]);
//
//			if(yestodEndPri.compareTo(BigDecimal.ZERO)==0){
//				stock.setZdf(BigDecimal.ZERO);
//			}else {
//				stock.setZdf(nowPrice.subtract(yestodEndPri).divide(yestodEndPri, 10, RoundingMode.HALF_DOWN));
//			}
//
//			stockList.add(stock);
//		}
//		return stockList;
//	}
//	
//	/**
//	 * 计算涨停价
//	 * @param closingPriceStr
//	 * @return
//	 */
//	private static BigDecimal getHighLimit(String closingPriceStr){
//		BigDecimal closingPrice = BigDecimal.valueOf(Double.parseDouble(closingPriceStr));
//		return closingPrice.multiply(BigDecimal.valueOf(Double.parseDouble("1.1")));
//	}
//	
//	/**
//	 * 计算跌停价
//	 * @param closingPriceStr
//	 * @return
//	 */
//	private static BigDecimal getLowLimit(String closingPriceStr){
//		BigDecimal closingPrice = BigDecimal.valueOf(Double.parseDouble(closingPriceStr));
//		return closingPrice.multiply(BigDecimal.valueOf(Double.parseDouble("0.9")));
//	}
//	
//	/**
//	 * 计算股票当前持仓总值
//	 * @param positions
//	 * @return
//	 */
//	public static BigDecimal countPositionValue(List<Position> positions){
//		BigDecimal total = BigDecimal.ZERO;
//		if(positions==null){
//			return total;
//		}
//		for(Position position : positions){
////			List<StockInfo> stockInfos = StockInfoUtils.getStockInfo(position.getStockCode(), Constants.STOCK_TYPE_SH);
//			/**
//			 * 从redis中取值
//			 */
//			List<StockInfo> stockInfos = null;//StockInfoUtils.getSotckInfoRedis(position.getStockCode(), Constants.STOCK_TYPE_SH);
//			/**
//			 * redis中没有，从sina接口取值
//			 */
//			if(stockInfos == null || stockInfos.isEmpty()){
//				stockInfos = StockInfoUtils.getStockInfo(position.getStockCode(), Constants.STOCK_TYPE_SH);
//			}
//			if(stockInfos == null || stockInfos.isEmpty()){
//				continue;
//			}
//
//			if(stockInfos.get(0).getNowPrice().compareTo(BigDecimal.ZERO)==0){
//				BigDecimal zs = new BigDecimal(stockInfos.get(0).getYestodEndPri());
//				total = total.add(zs.multiply(new BigDecimal(position.getNumberOfShares())));
//			}else {
//				total = total.add(stockInfos.get(0).getNowPrice().multiply(new BigDecimal(position.getNumberOfShares())));
//			}
//		}
////		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>countPositionValue>>>2"+System.currentTimeMillis());
//		return total;
//	}
//	/**
//	 * 获取所有股票的总成本
//	 * @param positions
//	 * @return
//     */
//	public static BigDecimal countPostionCostValue(List<Position> positions){
//		BigDecimal total = BigDecimal.ZERO;
//		if(positions==null){
//			return total;
//		}
//		for(Position position : positions){
//			total = total.add(position.getCostPrise().multiply(new BigDecimal(position.getNumberOfShares())));
//		}
//		return total;
//	}
//	public static BigDecimal countPositionValueForDB(List<Position> positions){
//		BigDecimal total = BigDecimal.ZERO;
//		if(positions==null){
//			return total;
//		}
//		if(positions.size()==0){
//			return total;
//		}
//		StringBuffer sb = new StringBuffer();
//		for(Position p:positions){
//			sb.append("'"+p.getStockCode()+"',");
//		}
//		sb.append("''");
//		//得到需要的股票信息
//		List<Stock> stocks = stockInfoUtils.stockMapper.getByCodes(sb.toString());
//		HashMap<String,Stock> map = new HashMap<>();
//		for(Stock stock:stocks){
//			map.put(stock.getFullCode(),stock);
//		}
//		for(Position p:positions){
//			Stock stock = map.get(p.getStockCode());
//			if(stock==null){
//				continue;
//			}
////			if(stockInfos.get(0).getNowPrice().compareTo(BigDecimal.ZERO)==0){
//			if(stock.getZxz().compareTo(BigDecimal.ZERO)==0){
//				total = total.add(stock.getZsp().multiply(new BigDecimal(p.getNumberOfShares())));
//			}else {
//				total = total.add(stock.getZxz().multiply(new BigDecimal(p.getNumberOfShares())));
//			}
//		}
//		return total;
//	}
//	/**
//	 * 计算持仓总值
//	 * @param position
//	 * @return
//	 */
//	public static BigDecimal countPositionValue(Position position){
//		BigDecimal total = BigDecimal.ZERO;
//		List<StockInfo> stockInfos = StockInfoUtils.getStockInfo(position.getStockCode(), Constants.STOCK_TYPE_SH);
//		if(stockInfos == null || stockInfos.isEmpty()){
//			return total;
//		}
//		return total.add(stockInfos.get(0).getNowPrice().multiply(new BigDecimal(position.getNumberOfShares())));
//	}
//	
//	public static String getStockType(String stockCode){
//		
//		if(stockCode.indexOf("60")==0){
//			return "1";
//		}
//		if(stockCode.indexOf("00")==0||stockCode.indexOf("300")==0){
//			return "2";
//		}
//		return null;
//	}
//	
//	
//	
//	public static void main(String[] args) {
//		System.out.println(getStockType("600123"));
//
//	}
//}
