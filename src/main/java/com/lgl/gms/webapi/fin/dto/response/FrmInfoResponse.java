package com.lgl.gms.webapi.fin.dto.response;

import lombok.Data;

/**
 * 양식정보 법인
 * @author jokim
 * @date 2022.04.13
 */
@Data
public class FrmInfoResponse {
	
	private Integer frmId;		// 양식 ID
	private Integer frmCdId;	// 양식 코드 ID
	private String frmNm;		// 양식 명
	private Integer frmTyp;		// 양식유형

}