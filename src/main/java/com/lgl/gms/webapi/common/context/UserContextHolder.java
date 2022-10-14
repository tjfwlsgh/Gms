package com.lgl.gms.webapi.common.context;

import com.lgl.gms.webapi.common.dto.TokenInfo;

public class UserContextHolder {

	public static final ThreadLocal<TokenInfo> CONTEXT = new ThreadLocal<>();
	
	public static TokenInfo getContext() {
		return UserContextHolder.CONTEXT.get();
	}
	
}
