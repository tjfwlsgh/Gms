package com.lgl.gms.webapi.inc.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.model.BaseModel;

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
public class BoIncRetHopayModel extends BaseModel {
	
	private Integer	boId;			// 법인 ID
	private String 	incYymm;		// 손익 실적 년월
	private String 	defCl;			// 확정 구분 (8일, 15일)
	private Long		seq;				// SEQ
	private Integer incItmId;		// 손익 항목 ID
	
	private String typCd;			// 유형 코드
	private String cl1Cd;				// 구분1 코드
	private String cl2Cd;				// 구분2 코드
	private String cl3Cd;				// 구분3 코드
	private BigDecimal ptAmt;			// 지급분 금액
	private String clsYn;				// 마감_YN
	private String retDt;				// 실적 일자
	private String aggYn;			// 집계	
	private Integer incItmDetId;	// 손익 항목 상세 ID
	
	private String regNo = UserInfo.getUserId();
	private String updNo = UserInfo.getUserId();
	private String workIp = UserInfo.getWorkIp();
	
}