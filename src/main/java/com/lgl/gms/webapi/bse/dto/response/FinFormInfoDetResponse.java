package com.lgl.gms.webapi.bse.dto.response;

import lombok.Data;

/**
 * 재무양식정보 Response
 * @author jokim
 * @Date 2022.05.09
 */
@Data
public class FinFormInfoDetResponse {
	
	private Integer frmId;		// 양식 ID
	private Integer rowSeq;		// ROW 순서
	private String frmNm;		// 양식 명
	private String frmNmKr;		// 양식 명 한글
	private String frmNmEng;	// 양식 명 영어
	private Integer lvCl;		// Level 구분	
	private Integer accId;		// 계정 ID

	
}
