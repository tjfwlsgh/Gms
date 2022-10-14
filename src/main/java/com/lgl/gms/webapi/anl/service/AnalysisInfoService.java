package com.lgl.gms.webapi.anl.service;

import com.lgl.gms.webapi.anl.dto.request.BoSummaryRequest;
import com.lgl.gms.webapi.anl.dto.request.BusiProfRequest;
import com.lgl.gms.webapi.anl.dto.request.CustPerfRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;


public interface AnalysisInfoService {
	
	BaseResponse selectBoSummaryInfo(BoSummaryRequest body);  // 법인 요약정보 조회
	
	BaseResponse selectBusiProfList(BusiProfRequest body);	// 영업이익추이 조회
	
	BaseResponse selectCustPerfList(CustPerfRequest body);	// 거래처매출분석 조회

}
