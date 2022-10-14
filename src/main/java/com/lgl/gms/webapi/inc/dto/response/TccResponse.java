package com.lgl.gms.webapi.inc.dto.response;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 공통코드 값
 * @author jokim
 * 2022.02.21
 */
@Data
public class TccResponse {
	
	private Integer tccId;				// 공통코드_ID
	private Integer compId;			// 회사 ID
	private String bseCd;			// 기준 코드
	private String typNm;			// 유형 명
	private String typNmEng;		// 유형 명 영어
	private String cdCl;				// 코드 구분
	private String userCl;				// 사용자 구분
	private String cdLv;				// 코드 레벨
	private String delYn;			// 삭제 YN
	private Date regDt;			// 등록일자
	private Date updDt;			// 수정일자
	private String workIp;		// 작업자 IP
	private String regNo;		// 등록자 NO
	private String updNo;		// 갱신자 NO
	
	private List<TccValResponse> tccVals;		// 공통코드값 리스트	
	
	

}