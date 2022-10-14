package com.lgl.gms.webapi.sys.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * 서비스 유형
 */
@Data
public class SvcTypRequest  {

	private Integer tccvId;		// 공통코드 상세 ID

	private String svcTyp;		// 서비스 유형
	private String cl1Cd;		// 구분1 코드
	private String svcNm;		// 서비스 명
	private String svcSnm;		// 서비스 단축명
	private String svcNmEng;	// 서비스 명 영어
	private String svcSnmEng;	// 서비스 단축명 영어
	private Integer viewSeq;	// VIEW 순서
	private String delYn;		// 삭제 YN



}