package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 손익집계조회 Response
 * @author jokim
 * @date 2022.03.24
 */
@Data
public class BoIncMonAggrResponse {

	private String incYy;			// 손익 년월
	private String plnRetCl;		// 계획_실적 구분
	private String defCl;			// 확정 구분 (8일, 15일)	
	private Integer incItmId;		// 손익 항목 ID
//	private Integer incItmGrp1;		// 상위1 그룹
	private Integer lvCl; 			// Level 구분
	
	// 실적(전)
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01AmtLy;	// 월 01 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02AmtLy;	// 월 02 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03AmtLy;	// 월 03 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04AmtLy;	// 월 04 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05AmtLy;	// 월 05 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06AmtLy;	// 월 06 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07AmtLy;	// 월 07 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08AmtLy;	// 월 08 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09AmtLy;	// 월 09 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10AmtLy;	// 월 10 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11AmtLy;	// 월 11 금액
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12AmtLy;	// 월 12 금액
	
	// 계획
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01AmtP;	// 월 01 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02AmtP;	// 월 02 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03AmtP;	// 월 03 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04AmtP;	// 월 04 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05AmtP;	// 월 05 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06AmtP;	// 월 06 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07AmtP;	// 월 07 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08AmtP;	// 월 08 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09AmtP;	// 월 09 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10AmtP;	// 월 10 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11AmtP;	// 월 11 금액
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12AmtP;	// 월 12 금액
	
	// 실적
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01AmtR;	// 월 01 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02AmtR;	// 월 02 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03AmtR;	// 월 03 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04AmtR;	// 월 04 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05AmtR;	// 월 05 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06AmtR;	// 월 06 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07AmtR;	// 월 07 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08AmtR;	// 월 08 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09AmtR;	// 월 09 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10AmtR;	// 월 10 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11AmtR;	// 월 11 금액
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12AmtR;	// 월 12 금액
	
	// 전년비 증감액
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01YoyAmt;	// 월 01
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02YoyAmt;	// 월 02	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03YoyAmt;	// 월 03
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04YoyAmt;	// 월 04
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05YoyAmt;	// 월 05
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06YoyAmt;	// 월 06
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07YoyAmt;	// 월 07
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08YoyAmt;	// 월 08	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09YoyAmt;	// 월 09
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10YoyAmt;	// 월 10
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11YoyAmt;	// 월 11
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12YoyAmt;	// 월 12
	
	// 전년비 증감율
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01Yoy;	// 월 01
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02Yoy;	// 월 02	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03Yoy;	// 월 03
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04Yoy;	// 월 04
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05Yoy;	// 월 05
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06Yoy;	// 월 06
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07Yoy;	// 월 07
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08Yoy;	// 월 08	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09Yoy;	// 월 09
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10Yoy;	// 월 10
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11Yoy;	// 월 11
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12Yoy;	// 월 12
	
	
	// 전월비 증감액
	private BigDecimal mon01MonAmt;	// 월 01
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02MonAmt;	// 월 02	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03MonAmt;	// 월 03
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04MonAmt;	// 월 04
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05MonAmt;	// 월 05
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06MonAmt;	// 월 06
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07MonAmt;	// 월 07
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08MonAmt;	// 월 08	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09MonAmt;	// 월 09
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10MonAmt;	// 월 10
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11MonAmt;	// 월 11
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12MonAmt;	// 월 12
	
	
	// 전월비 증감율
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01Mon;	// 월 01
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02Mon;	// 월 02	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03Mon;	// 월 03
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04Mon;	// 월 04
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05Mon;	// 월 05
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06Mon;	// 월 06
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07Mon;	// 월 07
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08Mon;	// 월 08	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09Mon;	// 월 09
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10Mon;	// 월 10
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11Mon;	// 월 11
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12Mon;	// 월 12
	
	// 계획비 달성율
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01Poy;	// 월 01
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02Poy;	// 월 02	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03Poy;	// 월 03
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04Poy;	// 월 04
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05Poy;	// 월 05
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06Poy;	// 월 06
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07Poy;	// 월 07
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08Poy;	// 월 08	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09Poy;	// 월 09
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10Poy;	// 월 10
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11Poy;	// 월 11
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12Poy;	// 월 12
	
	private BigDecimal sumLy;		// 누계(전년도실적)
	private BigDecimal sumP;		// 누계(계획)
	private BigDecimal sumR;		// 누계(실적)
	
	private BigDecimal sumYoyAmt;	// 누계(증감액)
	private BigDecimal sumYoy;		// 누계(증감율)
	private BigDecimal sumPoy;		// 누계(계획비달성율)
	
	private String accCl;			//  계정구분 (S: 합계, A: 실 계정)
	private String crncyCd;			//  통화 코드
	
	private Date regDt;			// 등록일자
	private Date updDt;			// 수정일자
	private String workIp;		// 작업자 IP
	private String regNo;		// 등록자 NO
	private String updNo;		// 갱신자 NO
	
	private BigDecimal viewSeq;	// View 순서
	private String incItm1;		// 손익항목1
	private String incItm2;		// 손익항목2
	private String incItm3;		// 손익항목3
	private String itmNm;		// 손익항목명
	
}
