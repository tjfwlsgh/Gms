package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 손익데이터 조회 Response
 * @author jokim
 * @date 2022.04.06
 */
@Data
public class BoIncDataInqResponse {

	private Integer boId;			// 법인ID
	private Integer subBoId;		// 지점ID
	
	private String boNm;			// 법인명
	private String subBoNm;			// 지점명
	
	private String incYymm;			// 년월
	private String crncyCd;			// 통화
	private String inc10Nm;			// 유형
	
	private String cl1Cd;		// 구분1
	private String cl2Cd;		// 구분2
	private String cl3Cd;		// 구분3
	
	private String custNm;			// 거래처
	
	private String grp1Cd;			// 그룹1
	private String grp2Cd;			// 그룹2
	private String grp3Cd;			// 그룹3
	
	private String svcTyp;			// 서비스
	
	private String bizClCd;				// 영업 구분 코드
	private String bizObjCd;			// 영업 주체 코드
	
	/** 매출실적 **/
	private BigDecimal	salAmt;		// 매출_금액
	private BigDecimal	salCst;		// 매출_원가
	private BigDecimal	seaeAmt;	// 판매관리비 금액
	private BigDecimal	lbrCost;	// 인건비
	private BigDecimal	expAmt;		// 경비 금액
	private BigDecimal	ifoAmt;		// 영업이익 금액
	/**************************/
	
	/** 비용실적 **/
	private BigDecimal	rvnExpAmt;		// 비용 금액
	/**************************/
	
	/** 본사지급분 **/
	private BigDecimal	ptAmt;			// 지급 금액
	/**************************/
	
	
	private String frArea;				// From Area
	private String frSbuArea;			// From SBU Area
	private String toSbuArea;			// To SBU Area
	
	
}
