package com.lgl.gms.webapi.cmm.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * 재무공통 Request
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class ExcelRequest {

	private Integer compId = UserInfo.getCompId();	// 회사 ID
	private String locale =  UserInfo.getLocale();	// 언어
}
