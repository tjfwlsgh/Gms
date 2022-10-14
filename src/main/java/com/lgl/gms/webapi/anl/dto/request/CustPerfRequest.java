package com.lgl.gms.webapi.anl.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class CustPerfRequest {
	
	private String incYy; 	// 년도(ex. 2022)
	private String incMm; 	// 월(ex. 04)
	private Integer boId; 	// 법인id 
	private String defCl; 	// 확정구분(Q1:D+8, Q2:D+15)
	
	private Integer compId = UserInfo.getCompId();
	private String lang = UserInfo.getLocale();
}
