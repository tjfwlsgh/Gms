package com.lgl.gms.webapi.inc.persistence.model;

import java.math.BigDecimal;

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
 * 법인 손익 계획 매출
 * @author jokim
 * @date 2022.02.22
 */
@Data
public class BoIncPlnSalModel extends BaseModel {
	
	private Integer	boId;			// 법인 ID
	private String	incYy;		// 법인손익년도
	private String	incMon;		// 손익 월
	private Integer	seq;				// SEQ
	private String 	boCustCd;	// 법인 거래처 코드
	private String	custNm;		// 거래처 명
	private String	svcTyp;			// 서비스 유형
	private String	grp1Cd;		// 그룹1 코드
	private String	grp2Cd;		// 그룹2 코드
	private String	grp3Cd;		// 그룹3 코드
	private BigDecimal	salAmt;		// 매출_금액
	private BigDecimal	salCst;		// 매출_원가
	private BigDecimal	seaeAmt;	// 판매관리비 금액
	private BigDecimal	lbrCost;	// 인건비
	private BigDecimal	expAmt;	// 경비 금액
	private BigDecimal	ifoAmt;		// 영업이익 금액
	
	private String regNo = UserInfo.getUserId();
	private String updNo = UserInfo.getUserId();
	private String workIp = UserInfo.getWorkIp();	
}