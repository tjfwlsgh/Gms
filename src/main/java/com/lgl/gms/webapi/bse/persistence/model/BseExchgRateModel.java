package com.lgl.gms.webapi.bse.persistence.model;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class BseExchgRateModel {
	private String yymm;	// 년월
	private String crncyCd;	// 통화 코드
	private String exchgRateDet;	// 환율 상세
	private BigDecimal curExchgRate;	// 기말 환율
	private String std;	// 시작일
	private String etd;	// 종료일
	private Date rcpDt;	// 수신 일자 -> 조회 시각
	
	private String crncyNm;	// 통화명

}
