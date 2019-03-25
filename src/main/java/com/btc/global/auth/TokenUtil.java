package com.btc.global.auth;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
public class TokenUtil {
	/**密钥*/
    private static String SECRET="127df0f260594a7d9f9637c80d564299";
    
    private static Key key;
  
    private static long TOKEN_EXPIRE_TIME= 14 * 24 * 3600*1000;
    private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);  
    
    static{
    	 byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);  
	     key= new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }
    
    /** 
     * 生成token 
     * Key以字节流形式存入redis 
     * 
     * @param date  失效时间 
     * @param appId AppId 
     * @return 
     */  
    public static String generateToken(AuthUser auth){  
    	logger.info("创建token开始,auth is {}",JSONObject.toJSONString(auth));
        String token = Jwts.builder()
                .setSubject(JSONObject.toJSONString(auth))
                .claim("userId", auth.getUserId())
                .claim("role", auth.getRole())
                .claim("loginTime", auth.getLoginTime())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))  
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, key)  
                .compact();  
        logger.info("创建token结束,token is {}",token);
        return token;  
    }  
  
    /** 
     * 验证token 
     * @param appId AppId 
     * @param token token 
     * @return 
     */  
    public static boolean verifyToken(String token) {  
        if(token == null){  
            return false;  
        }  
        try {  
            Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();  
            return true;  
        } catch (Exception e) {  
        	e.printStackTrace();
            return false;  
        }  
    }  
    
    /**
     * 获取用户id
     * @param appId
     * @param token
     * @return
     */
    public static Long getUserId(String token){
    	 if(token == null){  
	            return -1l;  
	        }  
	        try {  
	        	Claims claim=Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	            AuthUser au= JSONObject.parseObject(claim.getSubject(),AuthUser.class);
	            return au.getUserId();  
	        } catch (Exception e) {  
	        	e.printStackTrace();
	            return -1l;  
	        }  
    }
    /**
     * 获取用户权限
     * @param appId
     * @param token
     * @return
     */
    public static AuthUser getAuth(String token){
    	 if(token == null){  
	            return null;  
	        }  
	        try {  
	        	Claims claim=Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	        	AuthUser au= JSONObject.parseObject(claim.getSubject(),AuthUser.class);
	            return au;  
	        } catch (Exception e) { 
	        	e.printStackTrace();
	            return null;  
	        }  
    }
    
    
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
    	AuthUser au=new AuthUser(1l,"ADMIN",new Timestamp(System.currentTimeMillis()));
    	String token=	TokenUtil.generateToken(au);
//    
    	
//    	String token="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJsb2dpblRpbWVcIjoxNTM4MjA5MjQyNzcxLFwidXNlcklkXCI6NTYsXCJ4dXNlcklkXCI6XCIzMzMzMzMzMzNcIn0iLCJ1c2VySWQiOjU2LCJsb2dpblRpbWUiOjE1MzgyMDkyNDI3NzEsImV4cCI6MTUzOTQxODg0MywiaWF0IjoxNTM4MjA5MjQzfQ._ER7GfIuILqMhrhUS56dmhXSPazO6Riv-5QqiN7X3uhbEXDLJ4zDPNwlu4GgV-YbtUy03oxdlg1Il5yA3QiZAw";
    	
    	long uerid=TokenUtil.getUserId(token);
    	
    	System.out.println(uerid);
    	
    }
	    
}
