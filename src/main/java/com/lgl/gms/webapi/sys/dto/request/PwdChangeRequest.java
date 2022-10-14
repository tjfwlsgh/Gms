package com.lgl.gms.webapi.sys.dto.request;

import lombok.Data;

@Data
public class PwdChangeRequest {

	private String userId;
	private Integer compId;
	
	private String userPwd;	// 현재 비밀번호
	private String newPwd;	// 새 비밀번호
	
	private Integer userNo;	// 사용자 No
	
}
