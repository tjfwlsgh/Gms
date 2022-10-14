package com.lgl.gms.webapi.sys.service;

import java.util.HashMap;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.PgmRequest;

public interface PgmService {

	// --- 코드유형 리스트 조회 ---
	BaseResponse getPgmList(PgmRequest body);
	BaseResponse getPgmOne(PgmRequest body);

	BaseResponse addPgm(PgmRequest body);	
	BaseResponse modifyPgm(PgmRequest body);
	BaseResponse deletePgm(PgmRequest body);		

	BaseResponse getMenuCodeList(PgmRequest body);

}
