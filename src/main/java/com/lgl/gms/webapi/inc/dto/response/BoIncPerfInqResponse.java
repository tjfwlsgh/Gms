package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 손익조회 Response
 * @author jokim
 * @date 2022.03.18
 */
@Data
public class BoIncPerfInqResponse {

	private Integer boId;		// 법인 ID
	private String incYy;			// 손익 년월
	private String plnRetCl;		// 계획_실적 구분
	private String defCl;			// 확정 구분 (8일, 15일)	
	private String incItmId;			// 손익 항목 ID
	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon01Amt;	// 월 01 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon02Amt;	// 월 02 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon03Amt;	// 월 03 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon04Amt;	// 월 04 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon05Amt;	// 월 05 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon06Amt;	// 월 06 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon07Amt;	// 월 07 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon08Amt;	// 월 08 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon09Amt;	// 월 09 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon10Amt;	// 월 10 금액	
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon11Amt;	// 월 11 금액
	@JsonInclude(Include.NON_NULL)
	private BigDecimal mon12Amt;	// 월 12 금액
	
	// 메모
	@JsonInclude(Include.NON_NULL)
	private String mon01Memo;		// 월 01 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon02Memo;		// 월 02 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon03Memo;		// 월 03 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon04Memo;		// 월 04 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon05Memo;		// 월 05 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon06Memo;		// 월 06 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon07Memo;		// 월 07 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon08Memo;		// 월 08 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon09Memo;		// 월 09 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon10Memo;		// 월 10 메모	
	@JsonInclude(Include.NON_NULL)
	private String mon11Memo;		// 월 11 메모
	@JsonInclude(Include.NON_NULL)
	private String mon12Memo;		// 월 12 메모
	
	// 매모 갱신일
	@JsonInclude(Include.NON_NULL)
	private String mon01ChgDt;		// 월 01 최종갱신일
	@JsonInclude(Include.NON_NULL)
	private String mon02ChgDt;		// 월 02 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon03ChgDt;		// 월 03 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon04ChgDt;		// 월 04 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon05ChgDt;		// 월 05 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon06ChgDt;		// 월 06 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon07ChgDt;		// 월 07 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon08ChgDt;		// 월 08 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon09ChgDt;		// 월 09 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon10ChgDt;		// 월 10 최종갱신일	
	@JsonInclude(Include.NON_NULL)
	private String mon11ChgDt;		// 월 11 최종갱신일
	@JsonInclude(Include.NON_NULL)
	private String mon12ChgDt;		// 월 12 최종갱신일
	
	// 매모 ID
	@JsonInclude(Include.NON_NULL)
	private String mon01MemoId;		// 월 01 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon02MemoId;		// 월 02 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon03MemoId;		// 월 03 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon04MemoId;		// 월 04 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon05MemoId;		// 월 05 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon06MemoId;		// 월 06 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon07MemoId;		// 월 07 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon08MemoId;		// 월 08 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon09MemoId;		// 월 09 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon10MemoId;		// 월 10 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon11MemoId;		// 월 11 메모ID
	@JsonInclude(Include.NON_NULL)
	private String mon12MemoId;		// 월 12 메모ID
		
	private BigDecimal sum;		// 누계
	
	private String accCl;				//  계정구분 (S: 합계, A: 실 계정)
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
	private Integer lvCl; 		// Level 구분
	
}
