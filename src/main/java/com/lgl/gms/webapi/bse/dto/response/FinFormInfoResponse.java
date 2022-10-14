package com.lgl.gms.webapi.bse.dto.response;

import java.util.Date;

import lombok.Data;

/**
 * 재무양식정보 Response
 * @author jokim
 * @Date 2022.05.09
 */
@Data
public class FinFormInfoResponse {
	
	private Integer frmId;		// 양식 ID
	private Integer frmCdId;	// 양식 코드 ID
	private String frmNm;		// 양식 명
	private String frmNmKr;		// 양식 명 한글
	private String frmNmEng;	// 양식 명 영어
	private Integer frmTyp;		// 양식유형
	private String frmTypNm;	// 양식유형명
	
	private String useYn;
	private String regNo;	
	private String updNo;
	private Date regDt;
	private Date updDt;
	private String workIp;
	
}
