package com.lgl.gms.webapi.inc.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BoIncPlanRequest {

	private Integer boId;		// 법인 ID
	private String incYy;		// 법인손익년도
	private String incMon;		// 법인손익년도
	private String typCd;		// 유형코드( 1 : 판관비(영업실적), 2 -> 영업외손익(ETC) )
	private String typCdNm;
	private Integer seq;		// seq
	private Integer sheetNum;	// 엑셀시트번호
	
	private String workIp = UserInfo.getWorkIp();		// 작업자 IP
	private String updNo = UserInfo.getUserId();		// 사용자 NO
	private Integer compId = UserInfo.getCompId();
	private String pgmId;		// 프로그램ID
	
	// 손익집계처리(sp_upd_inc_agg_proc) - (PLN_CLS1:(계획)영업손익, PLN_CLS2 (계획)영업외손익,판관비, RET_CLS1:(실적)영업손익, RET_CLS2:(실적)영업외손익/판관비, RET_CLS3:(실적)본사지급분)
	// 계획이력생성(sp_crt_inc_pln_hst) - (PLN_SAL:(계획)영업손익, PLN_EXP:(계획)영업외손익,판관비
	// 마감의 초기화 작업(sp_int_cls_mgmt) - ( PLN(계획), RET(실적) )
	private String taskNm; 		// 작업구분
	
	private String defCl = "Q1";  // 마감구분(Q1, Q2) - 계획은 모두 Q1으로
	
	private String rtn;			// 프로시저 RTN
	private String locale = UserInfo.getLocale();	// 언어
	
}
