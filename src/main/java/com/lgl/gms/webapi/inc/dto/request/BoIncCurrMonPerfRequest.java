package com.lgl.gms.webapi.inc.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * 손익당월실적 조회 Request
 * @author jokim
 * @date 2022.03.31
 */
@Data
public class BoIncCurrMonPerfRequest {

	private Integer incItmId;		// 항목
	private String incYymm;			// 손익 년월
	private String untDp;			// 화폐
	private String currColExr;		// 선택월(당월) 환율컬럼
	private String currColNm;		// 선택월(당월) 실적 컬럼
	private String currColNmLy;		// 선택월(전월월) 실적 컬럼	
	private String defCl;			// 확정구분
	private String incItm1;			// 항목명1
	private Integer compId = UserInfo.getCompId();	// 회사ID
	private String locale = UserInfo.getLocale();
}
