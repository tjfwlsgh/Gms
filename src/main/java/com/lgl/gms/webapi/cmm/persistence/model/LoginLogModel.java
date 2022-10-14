package com.lgl.gms.webapi.cmm.persistence.model;

import java.util.Date;

import lombok.Data;

@Data
public class LoginLogModel {
	
	// 시스템공통에서 사용되는 Model로 BaseModel 상속 없이 사용됨.
	
	private Integer logId	;  // 로그 id
	private Date devDt	;  // 발생 일자
	private String logTyp	;  // 로그 유형(I:로그인, O:로그아웃)
	private String loginId ; // 로그인 id
	private String loginIp ; // 로그인 ip
	private String errYn	;  // 오류Yn
	private String errRsn	;  // 오류 사유

}
