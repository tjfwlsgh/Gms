package com.lgl.gms.webapi.inc.dto.response;

import java.math.BigDecimal;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 법인 손익 실적 본사지급
 * @author jokim
 * @date 2022.03.15
 */
@Data
public class BoIncRetHopayResponse {
	
	
	private Integer	boId;			// 법인 ID
	private Integer	pboId;			// 부모법인 ID
	private String	boSubNm;		// 법인/지점명
	private String 	defCl;			// 확정 구분 (8일, 15일)
	private String incYymm;			// 년월
	private Integer	seq;			// SEQ
	private String trrtNm;			// 지역
	private String cntryCd;			// 국가
	private String crncyCd = "KRW";	// 통화
	private BigDecimal payment; 	// 급여
	private BigDecimal rentAmt; 	// 주택임차료
	private BigDecimal tuition; 		// 자녀학자금
	private BigDecimal etcAmt; 		// 기타
	private String clsYn;					// 마감_YN
	private String seqStr;				// seq 연결
	private BigDecimal ptSum;		// 합계

}
