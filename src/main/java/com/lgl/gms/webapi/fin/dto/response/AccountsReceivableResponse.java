package com.lgl.gms.webapi.fin.dto.response;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class AccountsReceivableResponse {
	
	private String arYymm;				// 미수채권 년월
	private Integer boId;				// 법인 ID
	private String frmClssCd;			// 양식 분류 코드
	
	private String boNm;				// 법인 명
	private BigDecimal unrdBndsAmt;		// 미경과 채권 금액
	
	private BigDecimal rcvbBndsAmt30;	// 미회수 채권 금액 30
	private BigDecimal rcvbBndsAmt60;	// 미회수 채권 금액 60
	private BigDecimal rcvbBndsAmt90;	// 미회수 채권 금액 90
	private BigDecimal rcvbBndsAmt91;	// 미회수 채권 금액 91
	private BigDecimal rcvbBndsTamt;	// 미회수 채권 합계액
	
	private String incclCd;	// 업로드 대상

}
