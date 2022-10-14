package com.lgl.gms.webapi.inc.dto.response;

import lombok.Data;

/**
 * 손익 항목 상세
 * @author jokim
 * 2022.03.07
 */
@Data
public class IncItmDetResponse {
	
	private Integer incItmDetId;		// 손익 항목 상세 ID
	private Integer incTypId;			// 손익 유형 ID
	private String incCl1Id;			// 손익 구분1 ID
	private String incCl2Id;			// 손익 구분2 ID
	private String incCl3Id;			// 손익 구분3 ID
	
	private String typCd;				// 유형코드
	private String cl1Cd;				// 구분1코드
	private String cl2Cd;				// 구분2코드
	private String cl3Cd;				// 구분3코드
	
	private Integer incItmId;		// 손익 항목 상세 ID
	private Integer grp1Id;			// 그룹ID
	private String grp1Cd;			// 그룹ID
	private String grp1IdNm;		// 그룹ID명
	private String grp1IdNmEng;		// 그룹ID명 영어
	
	private String typCdNm;			// 손익 유형명
	private String typCdNmEng;		// 손익 유형명 영어
	private String cl1CdNm;			// 구분1명
	private String cl1CdNmEng;		// 구분1명 영어
	private String cl2CdNm;			// 구분2명
	private String cl2CdNmEng;		// 구분2명 영어
	private String cl3CdNm;			// 구분2명
	private String cl3CdNmEng;		// 구분2명 영어
	private String rvnYn;					// 수익 YN
	private String expYn;				// 비용 YN
	private String aggYn;				// 집계 YN
	
}