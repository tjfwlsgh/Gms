package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 법인 비용실적 내 매출실적 상세
 * @author jokim
 * @date 2022.03.10
 */
@Data
public class IncSaleRetDetResponse {
	
	private Integer	boId;			// 법인 ID
	private String	IncYymm;		// 법인손익년도
	private Long	seq;				// SEQ
	private String	custNm;		// 거래처 명
	private String	svcTyp;			// 서비스 유형
	private String	grp1Cd;		// 그룹1 코드
	private String	grp2Cd;		// 그룹2 코드
	private String	grp3Cd;		// 그룹3 코드
	private BigDecimal retAmt;	// 실적 금액

}