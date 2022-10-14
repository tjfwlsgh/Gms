package com.lgl.gms.webapi.cmm.persistence.model;

import lombok.Data;

@Data
public class BoModel {
	
	private Integer boId;	// 법인ID
	private String compId;	// 회사 ID
	private String boCd;		// 법인 코드
	private String boCl;			// 법인 구분
	private String boNm;		// 법인명
	private String boSnm;		// 법인 단축명
	private String boNmEng;	// 법인 명칭 영어
	private String boSnmEng;		// 법인 단축명 영어
	private String crncyCd;		// 통화코드
	private String cntryCd;		// 국가코드
	private String pboId;		// 상위 법인 ID
}
