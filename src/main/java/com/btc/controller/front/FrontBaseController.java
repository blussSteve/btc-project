//package com.btc.controller.front;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.btc.global.auth.AuthUser;
//import com.btc.global.auth.TokenUtil;
//import com.btc.pojo.UserInfo;
//import com.btc.util.Constants;
//import com.btc.util.DataCacheUtil;
// 
//
//public class FrontBaseController {
//	@Autowired
//	private UserMapper userMapper;
//	/**
//	 * 获取缓存信息
//	 * @param req 
//	 * @return
//	 */
//	public UserInfo getUserInfo(HttpServletRequest req){
//		String token=getTokenString(req);
//	 	AuthUser auth=TokenUtil.getAuth(token);
//	 	UserInfo ucv=DataCacheUtil.getUserInfo(auth.getUserId());
//    	return ucv;
//	}
//	
//	
//	/**
//	 * 获取用户id
//	 * @param req
//	 * @return
//	 */
//	public Long getUserId(HttpServletRequest req){
//			String token=getTokenString(req);
//			if(StringUtils.isEmpty(token)){
//				return null;
//			}
//		 	AuthUser auth=TokenUtil.getAuth(token);
//	    	return auth.getUserId();
//	}
//	
//    protected String getTokenString(HttpServletRequest httpRequest) {
//        String tokenString = null;
//        // from querystring
//        if (StringUtils.isBlank(tokenString)) {
//            tokenString = httpRequest.getParameter(Constants.AUTH_TOKEN_NAME_DEFAULT);
//        }
//
//        // from header
//        if (StringUtils.isBlank(tokenString)) {
//            tokenString = httpRequest.getHeader(Constants.AUTH_TOKEN_NAME_DEFAULT);
//        }
//
//        // from cookie 
//        if (httpRequest.getCookies() != null) {
//            for (Cookie c : httpRequest.getCookies()) {
//                if (Constants.AUTH_TOKEN_NAME.equals(c.getName())) {
//                    tokenString = c.getValue();
//                    break;
//                }
//                if (Constants.AUTH_TOKEN_NAME_DEFAULT.equals(c.getName())) {
//                    tokenString = c.getValue();
//                    break;
//                }
//            }
//        }
//
//        return tokenString;
//    }
//	
//}
