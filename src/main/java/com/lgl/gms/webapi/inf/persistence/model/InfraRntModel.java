package com.lgl.gms.webapi.inf.persistence.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * @FileName    : InfraRntModel.java
 * @Date        : 22.02.21
 * @Author      : hj.Chung
 * @Description : Model vo
 * @History     : 인프라 임대
 */

@Data
public class InfraRntModel extends BaseModel {
	
	private Integer boRntId;	// 법인 임대 ID
	private Integer boId;		// 법인 ID
	private String boNm;		// 법인 명
	private String office;		// 사무실
	private Integer rntTypId;	// 임대 유형 ID
	private Integer locClId;	// 위치 구분 ID
	private String locNm;		// 위치 명
	private String rntNm;		// 임대 명
	private Integer rntClId;	// 임대 구분 ID
	private BigDecimal qty;		// 수량
	private String unit;		// 단위
	private BigDecimal rntPrd;	// 임대 기간
	private String ltcYn;		// 장기계약 YN
	private String contStd;		// 계약 시작일
	private String contEtd;		// 계약 종료일
	private String pnltyNote;	// 위약금 비고
	private String crncyCd;		// 통화코드
	private BigDecimal hre;		// 임차료
	private String hrePrd;		// 임차료 기간
	private String dpamEndYn;	// 감가상각 종료 여부
	private String addr;		//주소
	private String note;		// 비고
	private String delYn;		// 삭제_YN
	private BigDecimal dpst;	// 보증금
	
	private String boCl;			// 법인 구분
	private Integer subBoId;		// 지사 ID
	


	
}
