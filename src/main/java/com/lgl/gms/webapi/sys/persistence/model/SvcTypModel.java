package com.lgl.gms.webapi.sys.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * 서비스 유형
 */
@Data
public class SvcTypModel extends BaseModel {

	private Integer tccvId;		// 공통코드 상세 ID
	private String svcTyp;		// 서비스 유형
	private String cl1Cd;		// 구분1 코드
	private String svcNm;		// 서비스 명
	private String svcSnm;		// 서비스 단축명
	private String svcNmEng;	// 서비스 명 영어
	private String svcSnmEng;	// 서비스 단축명 영어
	private Integer viewSeq;	// VIEW 순서
	private String delYn;		// 삭제 YN
//	private String regNo;		// 등록자 NO
//	private String updNo;		// 갱신자 NO
//	private Date regDt;			// 등록일자
//	private Date updDt;			// 수정일자
//	private String workIp;		// 작업자 IP


}