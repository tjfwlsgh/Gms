package com.lgl.gms.webapi.inc.dto.response;

import java.util.Date;

import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

import lombok.Data;

@Data
public class BoIncRetResponse {
	
	private Integer boId;			// 법인 ID
	private String incYymm;	// 손익 실적 년월
	private String defCl;			// 확정 구분 (8일, 15일)
	private String retCls1;		// 실적마감1(매출)
	private String retCls2;		// 실적마감2(비용)
	private String retCls3;		// 실적마감3(본사)
	private String crncyCd;		// 통화코드
	private String cntryCd;		// 국가코드
	private String retStd;		// 실적시작일
	private String retEtd;		// 실적종료일
	private Date regDt;			// 등록일자
	private Date updDt;			// 수정일자
	private String workIp;		// 작업자 IP
	private String regNo;		// 등록자 NO
	private String updNo;		// 갱신자 NO
	
}
