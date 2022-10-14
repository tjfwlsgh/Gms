package com.lgl.gms.webapi.bse.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data 
public class BseBranchOfficeMgmtResponse {
	
	@JsonInclude(Include.NON_NULL)
	private String bo;			// 법인
	@JsonInclude(Include.NON_NULL)
	private String subBo;		// 지사 
	
	@JsonInclude(Include.NON_NULL)
	private Integer boId;		// 법인 ID
	@JsonInclude(Include.NON_NULL)
	private Integer subBoId;	// 지사 ID
	@JsonInclude(Include.NON_NULL)
	private String useYn;		// 사용 여부		
	
	@JsonInclude(Include.NON_NULL)
	private Integer compId;		// 회사 ID
	@JsonInclude(Include.NON_NULL)
	private String boCd;		// 법인 코드
	@JsonInclude(Include.NON_NULL)
	private String boCl;		// 법인 구분
	
	@JsonInclude(Include.NON_NULL)
	private String boNm;		// 법인 명칭
	@JsonInclude(Include.NON_NULL)
	private String boSnm;		// 법인 단축명
	@JsonInclude(Include.NON_NULL)
	private String boNmEng;		// 법인 명칭 영어
	@JsonInclude(Include.NON_NULL)
	private String boSnmEng;	// 법인 단축명 영어
	
	@JsonInclude(Include.NON_NULL)
	private String cntryCd;		// 국가 코드
	@JsonInclude(Include.NON_NULL)
	private String crncyCd;		// 통화 코드
	
	@JsonInclude(Include.NON_NULL)
	private String std;			// 시작일
	@JsonInclude(Include.NON_NULL)
	private String etd;			// 종료일	
	
	@JsonInclude(Include.NON_NULL)
	private Integer pboId;		// 상위 법인 ID
	@JsonInclude(Include.NON_NULL)
	private Integer trrtId;		// 지역 ID
	
	@JsonInclude(Include.NON_NULL)
	private Date regDt;			// 등록 일자
	@JsonInclude(Include.NON_NULL)
	private Date updDt;			// 수정 일자
	
	@JsonInclude(Include.NON_NULL)
	private String workIp;		// 작업자 IP
	@JsonInclude(Include.NON_NULL)
	private String regNo;		// 등록자 NO
	@JsonInclude(Include.NON_NULL)
	private String updNo;	 	// 갱신자 NO
	
	private String boNmH;		// 법인 명 (boCl = 'H')
	private String subBoNm;		// 지사 명 (boCl = 'B')
	
	public BseBranchOfficeMgmtResponse() {}
	
}
