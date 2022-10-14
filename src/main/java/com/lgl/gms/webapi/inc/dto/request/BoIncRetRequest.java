package com.lgl.gms.webapi.inc.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BoIncRetRequest {

	private Integer boId;		// 법인 ID
	private String incYymm;		// 손익 실적 년월
	private String defCl;		// 확정 구분 (8일, 15일)
	private String typCd;		// 유형코드( 1 : 판관비(영업실적), 2 -> 영업외손익(ETC) )
	private String typCdNm;
	private String cls3;		// 구분검색
	private Integer seq;		// seq
	private Integer sheetNum;	// 엑셀시트번호
	
	private String workIp = UserInfo.getWorkIp();		// 작업자 IP
	private String updNo = UserInfo.getUserId();		// 사용자 NO
	private String pgmId;		// 프로그램ID
	private String jobCl;		// 집계구분 (PLN_CLS1:(계획)영업손익, PLN_CLS2 (계획)영업외손익,판관비, RET_CLS1:(실적)영업손익, RET_CLS2:(실적)영업외손익/판관비, RET_CLS3:(실적)본사지급분)
	private String taskNm; 		// 마감의 초기화 작업(sp_int_cls_mgmt) - ( PLN(계획), RET(실적) )
	private String incYy;		// 법인손익년도
	private String rtn;			// 프로시저 RTN
	private String locale = UserInfo.getLocale();		// 언어
	private Integer compId = UserInfo.getCompId();

}
