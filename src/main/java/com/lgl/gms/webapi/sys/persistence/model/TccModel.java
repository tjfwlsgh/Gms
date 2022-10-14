package com.lgl.gms.webapi.sys.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * 공통코드 유형
 */
@Data
public class TccModel extends BaseModel {

	private Integer tccId;		// 공통코드_ID
	private Integer compId;		// 회사 ID
	private String bseCd;		// 기준 코드
	private String typNm;		// 유형 명
	private String typNmEng;	// 유형 명 영어
	private String cdCl;		// 코드 구분(S:삭제불가, B:삭제가능, M:마스터 삭제가능) => 제거
	private String userCl;		// 사용자 구분(USER:사용자, MNGR:관리자)
	private String cdLv;		// 코드 레벨
	private String delYn;		// 삭제 YN
//	private String regNo;		// 등록자 NO
//	private String updNo;		// 갱신자 NO	
//	private Date regDt;			// 등록일자
//	private Date updDt;			// 수정일자
//	private String workIp;		// 작업자 IP

}