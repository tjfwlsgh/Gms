package com.lgl.gms.webapi.bse.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

@Data
public class IncItmDetResponse {
	
	private Integer incItmDetId;	// 손익 항목 상세 ID
	private Integer compId;			// 회사 ID
	private Integer incTypId;		// 손익 유형 ID
	private Integer incCl1Id;		// 손익 구분1 ID
	private Integer incCl2Id;		// 손익 구분2 ID
	private Integer incCl3Id;		// 손익 구분3 ID
	private String rvnYn;			// 수익 YN
	private String expYn;			// 비용 YN
	private String delYn;			// 삭제 YN
	private Integer grp1Id;			// 그룹1 ID
	private Integer incItmId;		// 손익 항목 ID
	
	private String incTypNm;		// 손익 유형 명
	private String incCl1Nm;		// 손익 구분1 명
	private String incCl2Nm;		// 손익 구분2 명
	private String incCl3Nm;		// 손익 구분3 명
	private String grp1Nm;			// 그룹1 명
	
	private String aggYn;			// 손익 집계 구분
	
	public IncItmDetResponse() {}
}
