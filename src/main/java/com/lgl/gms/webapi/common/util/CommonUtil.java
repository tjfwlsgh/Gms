package com.lgl.gms.webapi.common.util;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {
	/**
	 * 클라이언트 접속 IP 취득
	 * @param request
	 * @return
	 */
	public static String getClientIP(HttpServletRequest request) {
	
		String ip = request.getHeader("X-Forwarded-For");
//	    log.debug(">>> X-FORWARDED-FOR : " + ip);

	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
//	        log.debug(">>> Proxy-Client-IP : " + ip);
	    }
	    if (ip == null) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
//	        log.debug(">>>  WL-Proxy-Client-IP : " + ip);
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
//	        log.debug(">>> HTTP_CLIENT_IP : " + ip);
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//	        log.debug(">>> HTTP_X_FORWARDED_FOR : " + ip);
	    }
	    if (ip == null) {
	        ip = request.getRemoteAddr();
	        log.debug(">>> request.getRemoteAddr : "+ip);
	    }
//	    log.debug(">>> Result : IP Address : "+ip);

	    return ip;
	}
}
