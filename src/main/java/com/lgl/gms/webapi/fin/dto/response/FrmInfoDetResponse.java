package com.lgl.gms.webapi.fin.dto.response;

import lombok.Data;

/**
 * 양식정보 상세
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class FrmInfoDetResponse {
	
	private Integer frmId;		// 양식 ID
	private Integer rowSeq;		// ROW 순서
	private String frmNm;		// 양식 명
	private Integer lvCl;		// Level 구분
	private Integer accId;		// 계정 ID
}