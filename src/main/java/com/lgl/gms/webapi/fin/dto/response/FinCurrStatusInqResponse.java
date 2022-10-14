package com.lgl.gms.webapi.fin.dto.response;

import lombok.Data;

@Data
public class FinCurrStatusInqResponse {
	
	// 재무상태표 | 손익계산서
	private Integer accId;			// 계정 ID
	private String accItmNm;		// 계정 과목
	private Integer befAmt; 		// i년 nQ말 금액
	private Integer befCompRation;	// i년 nQ말 구성비
	private Integer aftAmt;			// i+1년 nQ말 금액
	private Integer aftCompRation;	// i+1년 nQ말 구성비
	private Integer increaseAmt;	// 증감액
	private Integer increaseRate;	// 증감율
	
}
