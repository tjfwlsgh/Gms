package com.lgl.gms.webapi.anl.dto.response;

import java.math.BigDecimal;



import lombok.Data;

/*
 * 법인 요약 - 매출액/영업이익 금액 정보(단위: 백만원)
 */
@Data
public class BoSummaryAmtInfo {
	
//	private Integer boId            ; // 법인 id
	
	private String incItm1          ; // 매출액/영업이익
	private String incItm2          ; // 기존/협력
	private Integer incSeq          ; // 구분(1:합계, 2:항목)
	private String itmNm			; // 항목명
	private BigDecimal befRet    ; // 전년 매출액 실적, 바그래프에도 표시
	private BigDecimal aftPln    ; // 금년 계획 금액, 바그래프에도 표시
	private BigDecimal aftRet    ; // 금년 실적 금액, 바그래프에도 표시
	private BigDecimal increaseAmt	; // 전년대비 증감액
	private BigDecimal increaseRate   	; // 전년대비 증감률
	private BigDecimal planRate       	; // 계획대비 달성률

}
