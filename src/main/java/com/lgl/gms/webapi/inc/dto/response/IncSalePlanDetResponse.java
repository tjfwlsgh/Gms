package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 법인 비용계획 내 매출계획 상세
 * @author jokim
 * @date 2022.03.10
 */
@Data
public class IncSalePlanDetResponse {
	
	private Integer	boId;			// 법인 ID
	private String	IncYy;		// 법인손익년도
	private String	incMon;		// 손익 월
	private Long		seq;				// SEQ
	private String	custNm;		// 거래처 명
	private String	svcTyp;			// 서비스 유형
	private String	grp1Cd;		// 그룹1 코드
	private String	grp2Cd;		// 그룹2 코드
	private String	grp3Cd;		// 그룹3 코드
	private BigDecimal retAmt;	// 실적 금액

}