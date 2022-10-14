package com.lgl.gms.webapi.bse.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class FormRequest {
	
	// 검색 조건
	private String delYn;		// 삭제 여부
	private String selIncItm1;	// 항목 1
	private String selIncCl1Id;	// 구분 1
	
	private String incItm1;
	private Integer compId = UserInfo.getCompId(); 	// 회사 ID
	private String locale = UserInfo.getLocale();
	
}
