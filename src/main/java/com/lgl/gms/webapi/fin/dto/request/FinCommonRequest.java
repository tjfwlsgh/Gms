package com.lgl.gms.webapi.fin.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * 재무공통 Request
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class FinCommonRequest {
	
	private Integer frmCd;	// 양식 구분(1->재무제표, 2->손익계산서)
	private Integer frmId;	// 양식 ID
	private Integer compId = UserInfo.getCompId();	// 회사 ID
	private String locale =  UserInfo.getLocale();	// 언어
}
