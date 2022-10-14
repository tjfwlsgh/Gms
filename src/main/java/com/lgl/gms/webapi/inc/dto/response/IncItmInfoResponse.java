package com.lgl.gms.webapi.inc.dto.response;

import lombok.Data;

/**
 * 손익 항목 정보
 * @author jokim
 * 2022.04.04
 */
@Data
public class IncItmInfoResponse {
	
	private Integer incItmId;	// 손익 항목 ID
	private String incItm1;	// 손익 항목1
	private String incItm2;	// 손익 항목2
	private String incItm3;	// 손익 항목3
	private Integer lvCl;		// Level 구분
	private String itmNm;		// 항목명
	
	
}