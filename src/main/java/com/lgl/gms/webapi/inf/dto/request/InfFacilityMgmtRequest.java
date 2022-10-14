package com.lgl.gms.webapi.inf.dto.request;

import java.math.BigDecimal;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * @FileName    : InfFacilityMgmtRequest.java
 * @Date        : 22.02.21
 * @Author      : hj.Chung
 * @Description : Request
 * @History     : 검색 창을 통해 요청 받은 조회 파라미터
 */

@Data
public class InfFacilityMgmtRequest {
	
	private Integer boRntId; // tb_infra_rnt의 id 값 
	
	// 조회파라미터
	private Integer boId; 		// 법인 ID	
	private Integer subBoId;	// 지사 ID
	private Integer rntTypId;	// 임대 유형 ID
	private Integer locClId;	// 위치 구분 ID
	private Integer rntClId;	// 임대 구분 ID
	private Integer compId = UserInfo.getCompId();
	
	private String boNm; 		// 법인 또는 지사명
	private String office;		// 사무실
	private String locTypNm;	// 위치유형 명
	private String locNm;		// 위치명
	private String rntClNm;		// 임대구분 명
//	private String rntNm;		// 임대명
	
	private BigDecimal qty;		// 수량
	private String unit;		// 단위
	private BigDecimal rntPrd;	// 임대기간(월)
//	private String ltcYn;		// 장기계약여부
	private String contStd;		// 계약 시작일
	private String contEtd;		// 계약 종료일
	private String crncyCd;		// 통화코드
	private BigDecimal hre;		// 임차료
	private String dpamEndYn;	// 감각상각 완료 여부
	private String addr;		// 주소 
	
	private String pnltyNote;	// 위약금 비고
	private String hrePrd;		// 임차료 기간
	private String note;			// 비고
	private BigDecimal dpst;	// 보증금
	
	private String boCl;			// 법인 구분
	
	private String locale = UserInfo.getLocale();
}
