package com.lgl.gms.webapi.inc.dto.response;

import java.util.Date;

import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

import lombok.Data;

@Data
public class BoIncPlanResponse {
	
	private Integer boId;				// 법인 ID
	private String incYy;		// 법인손익년도
	private String incMon;		// 손익 월
	private Integer finlVer;			// 최종 버전
	private String plnCls1;		// 계획마감1(매출)
	private String plnCls2;		// 계획마감2(비용)
	private String plnCls3;		// 계획마감3(본사)
	private String crncyCd;		// 통화코드
	private String cntryCd;		// 국가코드
	private Date regDt;			// 등록일자
	private Date updDt;			// 수정일자
	private String workIp;		// 작업자 IP
	private String regNo;		// 등록자 NO
	private String updNo;		// 갱신자 NO
	
}
