package com.lgl.gms.webapi.common.context;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserInfo {

	public static String getUserId() {
		String userId = "SYSTEM";
		if(UserContextHolder.getContext() != null) {
			userId = UserContextHolder.getContext().getUserId();
			if("anonymousUser".equals(userId)){
				userId = "SYSTEM";
			}
		}
		return userId;
	}

	public static String getAuthCd() {
		String authCd = "";
		if(UserContextHolder.getContext() != null) {
			authCd = UserContextHolder.getContext().getAuthCd();
		}
		return authCd;
	}
	
	public static Integer getCompId() {
		Integer compId = 100;
		if(UserContextHolder.getContext() != null) {
			compId = UserContextHolder.getContext().getCompId();
		}
		return compId;
	}
	
	public static Integer getBoId() {
		Integer boId = null;
		if(UserContextHolder.getContext() != null) {
			boId = UserContextHolder.getContext().getBoId();
		}
		return boId;
	}
	
	public static String getWorkIp() {
		String workIp = "127.9.9.9";
		if(UserContextHolder.getContext() != null) {
			workIp = UserContextHolder.getContext().getWorkIp();
		}
		return workIp;
	}
	
	public static String getLocale() {
		String locale = null;
		if(LocaleContextHolder.getLocale() != null) {
			locale = LocaleContextHolder.getLocale().getLanguage();
		}
		return locale;
	}	
		
}
