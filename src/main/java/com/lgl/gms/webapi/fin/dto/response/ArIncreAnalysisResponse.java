package com.lgl.gms.webapi.fin.dto.response;

import java.math.BigDecimal;

import com.lgl.gms.webapi.fin.persistence.model.ArSmmryCustModel;

import lombok.Data;

@Data
public class ArIncreAnalysisResponse {
	
	private String arYymm;	// 미수채권 년월
	private Integer boId;	// 법인 ID
	private BigDecimal arSeq;	// 미수채권 순서
	private String boCustCd;	// 법인 거래처 코드
	private String boNm;	// 법인 명
	private String custNm;	// 거래처 명
	
	private BigDecimal rcvbBndsAmt30;	// 미회수 채권 금액 30
	private BigDecimal rcvbBndsAmt60;	// 미회수 채권 금액 60
	private BigDecimal rcvbBndsAmt90;	// 미회수 채권 금액 90
	private BigDecimal rcvbBndsAmt91;	// 미회수 채권 금액 91
	private BigDecimal rcvbBndsTamt;	// 미회수 채권 합계액
	
	private String lastMonth;	// 회수 계획 (전월)
	private String numPlnTxt;	// 회수 계획 텍스트 : 당월만
	private String ntpytRsnTxt;	// 미입금 사유 텍스트 
	
	private String bllPrd; 		// 청구기간
	private String crdtTrm; 	// 신용 거래 조건
	private String bllStd;		// 청구 시작일
	private String bllEtd;		// 정구 종료일
	private Integer crdtTrmId;	// 신용거래 조건 ID

	private Integer trrtId;		// 지역 ID
	private String trrtNm; 		// 지역 명
}
