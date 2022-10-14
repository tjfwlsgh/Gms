package com.lgl.gms.webapi.sys.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class PgmAuthResponse {
	
	private String authCd	 ;    // 권한 코드
	private String topMenuCd;	 // top 메뉴 코드
	private String topMenuNm;	// top 메뉴명
	private String topMenuNmEng;	// top 메뉴명
	private String pgmId	 ;    // 프로그램 id
	private String pgmNm	 ;    // 프로그램 명
	private String pgmNmEng	 ;    // 프로그램 명(영어)
	private Integer viewSeq	 ;    // 뷰 순서
	private String viewAuth	 ;    // view 권한
	private String addAuth	 ;    // 추가 권한
	private String chgAuth	 ;    // 수정 권한
	private String delAuth	 ;    // 삭제 권한
	private String dwlAuth	 ;    // 다운로드 권한
	private String uplAuth	 ;    // 업로드 권한
	private String sveAuth	 ;    // 저장 권한
	private String clsAuth	 ;    // 마감 권한
	private String codeLv	 ;    // code level
	private String delYn	 ;    // 삭제 yn

	private String regNo	 ;    // 등록자 no
	private String updNo	 ;    // 갱신자 no
	private Date regDt	 ;    // 등록일자
	private Date updDt	 ;    // 수정일자
	private String workIp	 ;    // 작업자 ip
	
// 2022.03.31 불필요해서 삭제
//	private String viewMenuCd;    // 뷰 메뉴 코드
//	private String viewNm	 ;    // 뷰 명
//	private String viewNmEng;	 // 뷰 명 영어

	
}
