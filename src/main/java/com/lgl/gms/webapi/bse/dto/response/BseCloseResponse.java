package com.lgl.gms.webapi.bse.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class BseCloseResponse {
	
	@JsonInclude(Include.NON_NULL)
	private Integer boId; // 법인 id
	@JsonInclude(Include.NON_NULL)
	private String yy; // 년도
	@JsonInclude(Include.NON_NULL)
	private String clsPgm; // 마감 프로그램

	@JsonInclude(Include.NON_NULL)
	private Integer compId;  // 회사 id
	
	private String clsNo;	// 확정구분
	
	@JsonInclude(Include.NON_NULL)
	private String clsNoQ1; // 마감 번호 1
	@JsonInclude(Include.NON_NULL)
	private String clsNoQ2; // 마감 번호 2
	
	private String mon01Q1; // 월 01
	private String mon01Q2; // 월 01
	
	private String mon02Q1; // 월 02
	private String mon02Q2; // 월 02
	
	private String mon03Q1; // 월 03
	private String mon03Q2; // 월 03
	
	private String mon04Q1; // 월 04
	private String mon04Q2; // 월 04
	
	private String mon05Q1; // 월 05
	private String mon05Q2; // 월 05
	
	private String mon06Q1; // 월 06
	private String mon06Q2; // 월 06
	
	private String mon07Q1; // 월 07
	private String mon07Q2; // 월 07
	
	private String mon08Q1; // 월 08
	private String mon08Q2; // 월 08
	
	private String mon09Q1; // 월 09
	private String mon09Q2; // 월 09
	
	private String mon10Q1; // 월 10
	private String mon10Q2; // 월 10
	
	private String mon11Q1; // 월 11
	private String mon11Q2; // 월 11
	
	private String mon12Q1; // 월 12
	private String mon12Q2; // 월 12
	
	@JsonInclude(Include.NON_NULL)
	private String delYn; // 삭제Yn
	@JsonInclude(Include.NON_NULL)
	private Date regDt; // 등록일자
	@JsonInclude(Include.NON_NULL)
	private Date updDt; // 수정일자
	@JsonInclude(Include.NON_NULL)
	private String workIp; // 작업자Ip
	@JsonInclude(Include.NON_NULL)
	private String regNo; // 등록자 no
	@JsonInclude(Include.NON_NULL)
	private String updNo; // 갱신자 no
	
	private String trrt; // 지역 
	private String trrtNm; // 지역 
	private String boNm; // 법인 또는 지사명
	private String boNmH; // 법인명
	private String subBoNm; // 지사명
	private String clsPgmNm; // 마감 프로그램 명
	private Integer boIdH;	// 법인 id
	private Integer boIdS;	// 지사 id
	private String yymm; // 년월


}
