package com.lgl.gms.webapi.bse.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class FinFormRequest {
	
	// 검색 조건	
	private Integer frmTyp;		// 양식 유형
	private String frmNm;		// 양식명
	
	private Integer frmId;		// 양식 ID
	
	private String frmCd;		// 재무양식 STD_CD (1 -> 재무제표, 2 -> 손익계산서)
	private String locale = UserInfo.getLocale();
	private Integer compId = UserInfo.getCompId(); 	// 회사 ID
		
	private String useYn;
	
	// 엑셀업로드 
	private Integer sheetNum;		// sheet 번호
	private String includeFrmYn; 	// 양식포함
	
}
