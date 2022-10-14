package com.lgl.gms.webapi.cmm.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class CommonSubMenuResponse {
	
	@JsonInclude(Include.NON_NULL)
	private String MenuCd;		// 메뉴 코드
	@JsonInclude(Include.NON_NULL)
	private String menuNm;		// 메뉴명(한글)
	@JsonInclude(Include.NON_NULL)
	private String menuNmEng;	// 메뉴명(영문)

	@JsonInclude(Include.NON_NULL)
	private String pgmWebPage;		// 관련 Web Page명

	@JsonInclude(Include.NON_NULL)
	private String viewSeq;			// 메뉴 순서
	
	@JsonInclude(Include.NON_NULL)
	private String authCd;			// 권한 코드
	@JsonInclude(Include.NON_NULL)
	private String topMenuCd;		// 상위 메뉴 코드

	@JsonInclude(Include.NON_NULL)
	private String GrpNm;			// 메뉴 그룹명
	@JsonInclude(Include.NON_NULL)
	private String GrpNmEng;		// 메뉴 그룹명(영어)
}
