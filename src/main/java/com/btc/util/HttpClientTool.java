package com.btc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.btc.lbank.bean.LbankToken;

public class HttpClientTool {
	private static Logger logger = LoggerFactory.getLogger(HttpClientTool.class);
    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
 
    static {
    	 PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
         cm.setMaxTotal(1000);
         cm.setDefaultMaxPerRoute(100);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().
        		setDefaultRequestConfig(config).
        		setRetryHandler(new StandardHttpRequestRetryHandler()).
        		evictExpiredConnections().
        		setConnectionManager(cm).
        		build();
    }
    
    public static String doGet(String url, Map<String, String> params,Map<String,String> headers){
        return doGet(url, params,headers,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params,Map<String,String> headers){
        return doPost(url, params,headers,CHARSET);
    }
    
    public static String doGet(String url, Map<String, String> params){
        return doGet(url, params,null,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params){
        return doPost(url, params,null,CHARSET);
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,Map<String,String> headers, String charset){
    	logger.info("httpGet请求开始:url={}||params:{}||headers:{}||charset:{}",url,JSONObject.fromObject(params),JSONObject.fromObject(headers),charset);
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            if(null!=headers){
            	 for(Entry<String, String> entry: headers.entrySet()){
               	  httpGet.setHeader(entry.getKey(), entry.getValue());
               }
            }
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
        	logger.info("httpGet请求结束:"+result);
        	if(null!=result)
        		result=result.trim();
            return result;
        } catch (Exception e) { 
        	logger.error("httpGet请求异常",e);
            e.printStackTrace();
        }
        return null;
    }
     
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPost(String url,Map<String,String> params, Map<String,String> headers, String charset){
    	logger.info("httpPost请求开始:url={}||params:{}||headers:{}||charset:{}",url,JSONObject.fromObject(params),JSONObject.fromObject(headers),charset);
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            
            if(null!=headers){
	           	 for(Entry<String, String> entry: headers.entrySet()){
	           		httpPost.setHeader(entry.getKey(), entry.getValue());
	              }
           }
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            System.out.println(entity);
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            if(null!=result)
        		result=result.trim();
            logger.info("httpPost请求结束:"+result);
            return result;
        } catch (Exception e) {
        	logger.error("httpPost请求异常",e);
            e.printStackTrace();
        } 
        return null;
    }
    /** 
     * 根据url下载文件，保存到filepath中 
     * @param url 
     * @param filepath 
     * @return 
     */  
    public static String download(String url, String filepath) {  
        try {  
            HttpClient client = new DefaultHttpClient();  
            HttpGet httpget = new HttpGet(url);  
            HttpResponse response = client.execute(httpget);  
            httpget.addHeader("Authorization", "Basic " +Base64Utils.getBase64("xingyezq:yqm24ds2"));
            HttpEntity entity = response.getEntity();  
            InputStream is = entity.getContent();  
            File file = new File(filepath);  
            file.getParentFile().mkdirs();  
            FileOutputStream fileout = new FileOutputStream(file);  
            /** 
             * 根据实际运行效果 设置缓冲区大小 
             */  
            byte[] buffer=new byte[10240];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                fileout.write(buffer,0,ch);  
            }  
            is.close();  
            fileout.flush();  
            fileout.close();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    /**
    * 
    *
    * @param url
    * @param param
    * @return
    */
   public static String getAPIResult(String url, String param) {
       PrintWriter out = null;
       BufferedReader in = null;
       String result = "";
       try {
           URL realUrl = new URL(url);
           URLConnection conn = realUrl.openConnection();
           //conn.setConnectTimeout(5000);
           String plainCredentials = "xingyezq:yqm24ds2";
           String base64Credentials = new String(Base64Utils.getBase64(plainCredentials));
           conn.setRequestProperty("Authorization", "Basic " + base64Credentials);
           conn.setRequestProperty("accept", "*/*");
           conn.setRequestProperty("Content-type", "application/json");
           conn.setRequestProperty("connection", "Keep-Alive");
           conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           conn.setDoOutput(true);
           conn.setDoInput(true);
           out = new PrintWriter(conn.getOutputStream());
           out.print(param);
           out.flush();
           in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           String line;
           while ((line = in.readLine()) != null) {
        	   System.out.println(line);
               result += line;
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           try {
               out.close();
               in.close();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
       }
       return result;
   }
   
   /**
    * 构造Basic Auth认证头信息
    * 
    * @return
    */
   private static String getHeader() {
     String auth = "xingyezq:yqm24ds2";
     byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
     String authHeader = "Basic " + new String(encodedAuth);
     return authHeader;
   }
   public  static void send(String url) {
	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpGet post = new HttpGet(url);
	    post.addHeader("Authorization", getHeader());
	    String responseContent = null; // 响应内容
	    CloseableHttpResponse response = null;
	    try {
	        response = client.execute(post);
	        System.out.println(JSONObject.fromObject(response).toString());
	        if (response.getStatusLine().getStatusCode() == 200) {
	            HttpEntity entity = response.getEntity();
	            responseContent = EntityUtils.toString(entity, "UTF-8");
	            
	            if(!StringUtil.isEmpty(responseContent)){
	            	 System.out.println("===========");
		        	File fileText = new File("E:\\logs\\1.txt");
			    	FileWriter fw=new FileWriter(fileText);
			    	fw.write(responseContent);
			    	fw.flush();
			    	fw.close();
	            }
	           
		    	
	        }
	        
	        if (response != null) 
	            response.close();
	        if (client != null) 
	            client.close();
	        
	        System.out.println("responseContent:" + responseContent);
	        
	    } catch(ClientProtocolException e) {
	        e.printStackTrace();
	    } catch(IOException e) {
	        e.printStackTrace();
	    } 
	}
   
   
   public static void main(String[] args) {
	   
	  String url="http://140.206.187.210:9001/security/tocken";
	  Map<String,String> params=new HashMap<String,String>();
	  params.put("client_id", "85685133141624");
	  params.put("access_code ", "7dc937ec-5a34-431b-9a59-d4f9715d0a5d");
	  
	  Map<String,String> headers=new HashMap<String,String>();
	  headers.put("authorization", "373c47b475b36c9c8964c2e6b33422f0");
	   
	  String str=HttpClientTool.doPost(url,params,headers);
	  
	  LbankToken token=JSON.parseObject(str, LbankToken.class);
	  
	  System.out.println(JSON.toJSONString(token));
	
}

   
   
   
}
