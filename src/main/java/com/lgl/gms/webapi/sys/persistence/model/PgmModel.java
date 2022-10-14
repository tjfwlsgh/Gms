package com.lgl.gms.webapi.sys.persistence.model;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

@Data
public class PgmModel extends BaseModel{
	
	private String pgmId     ;  // 프로그램 ID(화면ID)
	private String pgmNm     ;  // 프로그램 명(화면명)
	private String pgmSnm    ;  // 프로그램 단축명(호면 단축명)
	private String pgmNmEng  ;  // 프로그램 명 영어(화면명(영어))
	private String pgmSnmEng ;  // 프로그램 단축명 영어(화면 단축명(영어))
	private String topMenuCd ;  // TOP 메뉴 코드(메뉴 코드)
	private String pgmTyp    ;  // 프로그램 유형
	private String linkFile  ;  // LINK FILE(Vue 파일명)
	private String rmrk      ;  // 비고
	private Integer compId    ; // 회사 ID
	private String delYn     ;  // 삭제 YN
	
	private Integer viewSeq    ; // 뷰 순서
//	private Date regDt     ;    // 등록일자
//	private Date updDt     ;    // 수정일자
//	private String workIp    ;  // 작업자 IP
//	private String regNo     ;  // 등록자 NO
//	private String updNo     ;  // 갱신자 NO

}
