package com.lgl.gms.webapi.anl.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BoSummaryRequest {
	
	private Integer boId; 	// 법인id 
	private String incYy; 	// 년도(ex. 2022)
	private String incMm; 	// 선택된 월(ex. 01)
	private String sumryCl; 	// 집계구분(당월(1)/누계(2))
	
	private Integer compId = UserInfo.getCompId();
	private String lang = UserInfo.getLocale();
}
