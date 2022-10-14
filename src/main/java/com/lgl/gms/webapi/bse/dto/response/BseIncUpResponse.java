package com.lgl.gms.webapi.bse.dto.response;

import lombok.Data;

/**
 * 손익 데이터 업로드 현황 Response
 * @author hj.Chung
 * @Date 2022.07.18
 */
@Data
public class BseIncUpResponse {
	
	private String trrtId;	// 지역 id
	private String trrtNm;	// 지역 명
	private Integer boId;	// 법인 id
	private String boNmH;	// 법인 명 (boCl = 'H')
	private String subBoNm;	// 지사 명 
	private Integer lstIncYymm;	// 최종 년월
	private String lstDefCl;	// 확정 구분
	private Integer cnt1;	// 건수 : 손익
	private Integer cnt2;	// 건수 : 손익 외
	private String lstRegDt1;	// 최종 갱신일 : 손익
	private String lstRegDt2;	// 최종 갱신일 : 손익 외

	
}
