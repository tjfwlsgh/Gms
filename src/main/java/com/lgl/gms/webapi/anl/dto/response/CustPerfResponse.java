package com.lgl.gms.webapi.anl.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CustPerfResponse {
	
	private String boCustCd       ; // 법인 거래처 코드
	private String custNm         ; // 법인 거래처명
	private Integer rank          ; // 순위 
	private BigDecimal salAmt        ; // 매출액
	private String salPer         ; // 매출액 비율(단위문자 및 포맷이 필요해서 문자로 처리)
	private BigDecimal cumsumSalAmt  ; // 수익합계 누계
	private String cumsumSalAmtPer; // 수익합계 누계율

}
