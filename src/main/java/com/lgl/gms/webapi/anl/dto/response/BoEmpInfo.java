package com.lgl.gms.webapi.anl.dto.response;

import lombok.Data;

/*
 * 법인 KPI - 거래처별 매출액 정보 ==> 제거 예정
 */
@Data
public class BoEmpInfo {
	
	private Integer empResiCnt  ; // 주재원
	private Integer empLocalCnt  ; // 현채인(현채인+현채한국인)
	private Integer empTotCnt  ; // 총인원

}
