package com.lgl.gms.webapi.cmm.dto.response;
  
 import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
  
 @Data
 public class CommonAuthResponse {
  
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
 	private String viewAuth;		// 조회 권한
 	@JsonInclude(Include.NON_NULL)
 	private String addAuth;			// 추가 권한
 	@JsonInclude(Include.NON_NULL)
 	private String chgAuth;			// 수정 권한
 	@JsonInclude(Include.NON_NULL)
 	private String delAuth;			// 삭제 권한
 	@JsonInclude(Include.NON_NULL)
 	private String dwlAuth;			// download 권한
 	@JsonInclude(Include.NON_NULL)
 	private String uplAuth;			// upload 권한
 	
 	@JsonInclude(Include.NON_NULL)
 	private String sveAuth;			// 저장 권한
 	@JsonInclude(Include.NON_NULL)
 	private String clsAuth;			// 마감 권한
 
 	@JsonInclude(Include.NON_NULL)
 	private String authCd;		// 권한 코드
	@JsonInclude(Include.NON_NULL)
 	private String topMenuCd;		// 상위 메뉴 코드
	 	
 }