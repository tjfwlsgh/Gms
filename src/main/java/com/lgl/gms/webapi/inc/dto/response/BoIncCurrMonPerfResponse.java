package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 손익당월실적 조회 Response
 * @author jokim
 * @date 2022.03.31
 */
@Data
public class BoIncCurrMonPerfResponse {

	private String itmNm;		// 손익항목명
	private Integer incItmId;		// 손익 항목 ID
	
	// 당월실적
	private BigDecimal monCurrAmtLy;	// 전년당월실적
	private BigDecimal monCurrAmtP;	// 당월계획	
	private BigDecimal monCurrAmtR;	// 당월실적		
	private BigDecimal monCurrYoy;	// 전년비 증감액
	private BigDecimal monCurrPoy;	// 계획비 증감액	
	
	// 당월실적 누계	
	private BigDecimal monSumAmtLy;	// 전년실적 실적 누계	
	private BigDecimal monSumAmtP;	// 당월계획 누계	
	private BigDecimal monSumAmtR;	// 당월실적 누계	
	private BigDecimal monSumYoy;	// 전년비누계
	private BigDecimal monSumPoy;	// 계획비누계
	
	private Integer lvCl;				// 레벨

	
}
