package com.lgl.gms.webapi.inf.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @FileName    : InfEquipmentMgmtResponse.java
 * @Date        : 22.03.11
 * @Author      : hj.Chung
 * @Description : Response
 * @History     : 
 */
@Data
public class InfEquipmentMgmtResponse {
	
	@JsonInclude(Include.NON_NULL)
	private Integer boRntId;	// 법인 임대 ID
	@JsonInclude(Include.NON_NULL)
	private Integer boId;		// 법인 ID
	private String boNm;		// 법인 명 (법인 + 지사)
	private String office;		// 사무실
	private Integer rntTypId;	// 임대 유형 ID
	private Integer locClId;	// 구분 ID
	private String locNm;		// 위치 명
	private String rntNm;		// 임대 명
	private Integer rntClId;	// 소유 ID
	
	private BigDecimal qty;		// 수량
	private String unit;		// 단위
	private BigDecimal rntPrd;	// 임대 기간
	private String ltcYn;		// 장기계약 YN
	
	private String contPrd;		// 계약기간
	private String contDate;	// 계약일 (계약 시작일 ~ 계약 종료일)
	private String contStd;		// 계약 시작일
	private String contEtd;		// 계약 종료일
	
	private String pnltyNote;	// 위약금 비고
	private String crncyCd;		// 통화코드
	private BigDecimal hre;		// 임차료
	private String hrePrd;		// 임차료 기간
	private String dpamEndYn;	// 감가상각 종료 여부
	private String note;		// 비고
	private String delYn;		// 삭제_YN
	
	private String locClNm;		// 위치 구분명
	private String rntClNm;		// 임대 구분명
	
	public String boCl;			// 법인 구분
	private Integer subBoId;	// 지사 ID
	private String subBoNm;		// 지사 명 (법인 코드 'B'만)
	private String boNmH;		// 법인 명 (법인 코드 'H'만)
	
	
	
	public InfEquipmentMgmtResponse() {}

}
