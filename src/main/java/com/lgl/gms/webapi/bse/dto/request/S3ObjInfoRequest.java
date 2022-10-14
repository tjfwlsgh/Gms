package com.lgl.gms.webapi.bse.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class S3ObjInfoRequest {
	
	private Integer objId;		// 오프젝트 ID
	private Integer compId = UserInfo.getCompId(); 	// 회사 ID
	private String locale = UserInfo.getLocale();
	
}
