package com.lgl.gms.webapi.inc.dto.response;

import java.util.Date;

import lombok.Data;

/**
 * 서비스유형
 * @author jokim
 * 2022.02.21
 */
@Data
public class SvcTypResponse {
	
	private Integer tccvId;			// 공통코드값 ID
	private String svcTyp;			// 서비스 유형
	private String cl1Cd;			// 구분1 코드
	private String svcNm;			// 서비스 명
	private String svcSnm;			// 서비스 단축명
	private String svcNmEng;		// 서비스 명 영어
	private String svcSnmEng;		// 서비스 단축명 영어
	private Integer viewSeq;		// VIEW 순서
	private String delYn;			// 삭제 YN
	private Date regDt;				// 등록일자
	private Date updDt;				// 수정일자
	private String workIp;			// 작업자 IP
	private String regNo;			// 등록자 NO
	private String updNo;			// 갱신자 NO
	
	private String stdCd;			// 기초 코드
	private String stdCdNm;			// 기초 코드 명
	private String stdCdNmEng;		// 기초 코드 명 영어
	
}