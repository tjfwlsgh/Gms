package com.lgl.gms.webapi.sys.service;

import java.util.HashMap;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.AccessHistRequest;

public interface AccessHistService {

	// --- 코드유형 리스트 조회 ---
	BaseResponse getAccessHistList(AccessHistRequest body);
//	BaseResponse getAccessHistOne(AccessHistRequest body);
//
//	BaseResponse addAccessHist(AccessHistRequest body);	
//	BaseResponse modifyAccessHist(AccessHistRequest body);
//	BaseResponse deleteAccessHist(AccessHistRequest body);		
//
//	BaseResponse getMenuCodeList(AccessHistRequest body);

}
