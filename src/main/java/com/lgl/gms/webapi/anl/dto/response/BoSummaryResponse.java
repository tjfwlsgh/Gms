package com.lgl.gms.webapi.anl.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class BoSummaryResponse {
		
	// -- 법인 - 인원 정보
	private Integer empResiCnt  ; // 주재원
	private Integer empLocalCnt  ; // 현채인(현채인+현채한국인)
	private Integer empTotCnt  ; // 총인원

	// -- 법인 - 전년/금년 매출액/영업이익 요약(총액/항목별 금액)
	List<?> boAmtInfoList; 	// 매출액/영업이익 리스트
	
}
