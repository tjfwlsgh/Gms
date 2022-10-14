package com.lgl.gms.webapi.sys.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.AuthRequest;
import com.lgl.gms.webapi.sys.dto.request.PgmAuthRequest;

public interface PgmAuthService {

	// --- 권한관리 ---
	BaseResponse getAuthList(AuthRequest body);
	BaseResponse getAuthOne(AuthRequest body);

	BaseResponse addAuth(AuthRequest body);	
	BaseResponse modifyAuth(AuthRequest body);
	BaseResponse deleteAuth(AuthRequest body);		

	// --- 프로그램 권한관리 ---
	BaseResponse getPgmAuthList(PgmAuthRequest body);
	BaseResponse getPgmAuthOne(PgmAuthRequest body);

	BaseResponse addPgmAuth(PgmAuthRequest body);	
	BaseResponse modifyPgmAuth(PgmAuthRequest body);
	BaseResponse deletePgmAuth(PgmAuthRequest body);		
}
