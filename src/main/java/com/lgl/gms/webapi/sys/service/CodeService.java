package com.lgl.gms.webapi.sys.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.SvcTypRequest;
import com.lgl.gms.webapi.sys.dto.request.TccRequest;
import com.lgl.gms.webapi.sys.dto.request.TccValRequest;

public interface CodeService {

	// --- 코드유형 리스트 조회 ---
	BaseResponse getCodeTypeList(TccRequest body);
	BaseResponse checkCodeVal(TccRequest body);
	
	BaseResponse addCodeType(TccRequest body);	
	BaseResponse modifyCodeType(TccRequest body);
	BaseResponse deleteCodeType(TccRequest body);		
	
	// --- 코드값 리스트 조회 ---
	BaseResponse getCodeValList(TccValRequest body);
	BaseResponse checkSvcType(TccRequest body);
	
	BaseResponse addCodeVal(TccValRequest body);
	BaseResponse modifyCodeVal(TccValRequest body);
	BaseResponse deleteCodeVal(TccValRequest body);
	
	// --- 서비스 유형 리스트 조회 ---
	BaseResponse getSvcTypeList(SvcTypRequest body);
	
	BaseResponse addSvcType(SvcTypRequest body);	
	BaseResponse modifySvcType(SvcTypRequest body);
	BaseResponse deleteSvcType(SvcTypRequest body);
	
}
