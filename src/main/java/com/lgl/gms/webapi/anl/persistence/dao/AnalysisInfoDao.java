package com.lgl.gms.webapi.anl.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.anl.dto.request.BoSummaryRequest;
import com.lgl.gms.webapi.anl.dto.request.BusiProfRequest;
import com.lgl.gms.webapi.anl.dto.request.CustPerfRequest;
import com.lgl.gms.webapi.anl.dto.response.BoEmpInfo;
import com.lgl.gms.webapi.anl.dto.response.BoSummaryAmtInfo;

/*
 * 분석관리
 */
public interface AnalysisInfoDao {

	// --- 법인 요약 정보 조회 ---
	public BoEmpInfo selectEmpInfo(BoSummaryRequest param);	// 법인-인원 조회
	public List<BoSummaryAmtInfo> selectSummaryAmtList(BoSummaryRequest param);		// 법인-매출액/영업이익 조회
	
	// -- 거래처 매출분석 
	public List<?> selectCustPerfList(CustPerfRequest param);	
	
	// -- 영업이익 추이 
	public List<?> selectSalesProfAmtList(BusiProfRequest param);		// 매출액 or 영업이익 조회
	public List<?> selectCumSalesProfAmtList(BusiProfRequest param);	// 매출액 or 영업이익 누계 
	public List<?> selectBusiProfExpAmtList(BusiProfRequest param);		// 영업이익&영업비용
	public List<?> selectCumBusiProfExpAmtList(BusiProfRequest param);	// 영업이익&영업비용 누계
	
}
