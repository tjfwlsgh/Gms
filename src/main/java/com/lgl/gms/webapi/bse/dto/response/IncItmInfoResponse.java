package com.lgl.gms.webapi.bse.dto.response;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

@Data
public class IncItmInfoResponse {
	
	private Integer incItmId;	// 손익 항목 ID
	private Integer compId;		// 회사 ID
	private String incItm1;		// 손익 항목 1
	private String incItm2;		// 손익 항목 2
	private String incItm3;		// 손익 항목 3
	private String incItm4;		// 손익 항목 4
	private BigDecimal lvCl;	// level 구분
	private String itmNm;		// 항목 명
	private String aggYn;		// 집계 Yn
	private String wrkFrml1;	// 작업 공식 1
	private String wrkFrml2;	// 작업 공식 2
	private BigDecimal viewSeq;	// view 순서
	private String delYn;		// 삭제 Yn
	private String custUseYn;	// 거래처 사용 여부
	private String salAggYn;	// 매출 집계 여부
	private String krwYn;	// 원화 여부
	private Integer maxViewSeq;	// view 순서 최대값
	private Integer incItmGrp1;	// 손익 항목 그룹 1
	
	public IncItmInfoResponse() {}
}
