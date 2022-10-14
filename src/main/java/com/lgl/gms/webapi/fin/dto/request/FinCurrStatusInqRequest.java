package com.lgl.gms.webapi.fin.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class FinCurrStatusInqRequest {

	// 검색조건
	private Integer boId;	// 법인 Id
	private String yymm;	// 년월
	private Integer ct;		// 100만원

	private Integer compId = UserInfo.getCompId();
}
