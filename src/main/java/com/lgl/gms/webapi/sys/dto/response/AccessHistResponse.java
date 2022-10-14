package com.lgl.gms.webapi.sys.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class AccessHistResponse {
	
	private Integer logId	;  // 로그 id
	private String devDt	;  // 발생 일자
	private String logTyp	;  // 로그 유형
	private String logTypNm	;  // 로그 유형명
	private String loginId ; // 로그인 id
	private String loginNm ; // 로그인 명
	private String loginIp ; // 로그인 ip
	private String errYn	;  // 오류Yn
	private String errRsn	;  // 오류 사유

}
