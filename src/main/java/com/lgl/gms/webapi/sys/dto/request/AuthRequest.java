package com.lgl.gms.webapi.sys.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class AuthRequest {
	
	private String authCd	;	// 권한 코드
	private String authNm	;	// 권한 명
	private String authNmEng;	// 권한 명 영어
	private String delYn	;	// 삭제 yn

	private String regNo	;	// 등록자 no
	private String updNo	;	// 갱신자 no
	private Date regDt	;	// 등록일자
	private Date updDt	;	// 수정일자
	private String workIp	;	// 작업자 ip
	
	
}
