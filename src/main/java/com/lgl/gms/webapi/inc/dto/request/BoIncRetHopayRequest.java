package com.lgl.gms.webapi.inc.dto.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BoIncRetHopayRequest {

	private Integer boId;		// 법인 ID
	private String incYymm;		// 손익 실적 년월
	private String defCl;			// 확정 구분 (Q1:8일, Q2:15일)
	private BigDecimal payment; 	// 급여
	private BigDecimal rentAmt; 	// 주택임차료
	private BigDecimal tuition; 	// 자녀학자금
	private BigDecimal etcAmt; 		// 기타
	private Integer seq;			// seq
	
	
}
