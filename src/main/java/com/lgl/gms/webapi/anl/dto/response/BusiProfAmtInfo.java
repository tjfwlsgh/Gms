package com.lgl.gms.webapi.anl.dto.response;

import java.math.BigDecimal;



import lombok.Data;

/*
 * 영업이익 추이 - 집계금액
 */
@Data
public class BusiProfAmtInfo {
	
	private String incYy    ;      // 연도
	private String incItm1  ;      // 손익항목(매출액/영업이익/일반괸리비 등)
	private BigDecimal mon01Amt ;  // 01월 금액
	private BigDecimal mon02Amt ;  // 02월 금액
	private BigDecimal mon03Amt ;  // 03월 금액
	private BigDecimal mon04Amt ;  // 04월 금액
	private BigDecimal mon05Amt ;  // 05월 금액
	private BigDecimal mon06Amt ;  // 06월 금액
	private BigDecimal mon07Amt ;  // 07월 금액
	private BigDecimal mon08Amt ;  // 08월 금액
	private BigDecimal mon09Amt ;  // 09월 금액
	private BigDecimal mon10Amt ;  // 10월 금액
	private BigDecimal mon11Amt ;  // 11월 금액
	private BigDecimal mon12Amt ;  // 12월 금액

}
