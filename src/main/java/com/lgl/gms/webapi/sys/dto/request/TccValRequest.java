package com.lgl.gms.webapi.sys.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * 공통코드 유형
 */
@Data
public class TccValRequest {

	private Integer tccId;		// 공통코드 유형 ID
	
	private Integer tccvId;		// 공통코드 상세 ID
	private String stdCd;		// 기초(상세) 코드
	private String stdCdNm;		// 기초(상세) 코드 명
	private String stdCdNmEng;	// 기초(상세) 코드 명 영어
	private String cdVal;		// 코드 값
	private Integer viewSeq;	// 표시 순서
	private String delYn;		// 삭제 YN


}