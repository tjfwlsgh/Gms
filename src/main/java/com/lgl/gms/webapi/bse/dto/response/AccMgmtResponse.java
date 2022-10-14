package com.lgl.gms.webapi.bse.dto.response;

import lombok.Data;

/**
 * 계정 관리 Response
 * @author jokim
 * @Date 2022.05.09
 */
@Data
public class AccMgmtResponse {
	
	private Integer accId;		// 계정 ID	
	private String accCd;		// 계정 코드
	private Integer accGrpCd;	// 계정 그룹 코드
	private String accCdNm;		// 계정 코드 명
	private String accStr;		// 계정코드(계정코드 명)
	private String accCdNmKr;	// 계정 코드 명 한글
	private String accCdNmEng;	// 계정 코드 명 영어
	private String accClssCd;	// 계정 분류 코드
	private Integer lvCl;		// Level 구분
	private String useYn;		// 사용 YN
	
	private String accItmNm;

	
}
