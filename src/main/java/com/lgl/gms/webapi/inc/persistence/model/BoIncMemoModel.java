package com.lgl.gms.webapi.inc.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import com.lgl.gms.webapi.common.model.BaseModel;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 법인 손익 메모
 * @author jokim
 * @date 2022.03.20
 */
@Data
public class BoIncMemoModel extends BaseModel {
	
	private Integer	memoId;		// 메모 ID
	private Integer	boId;			// 법인 ID
	private String 	incYy;			// 손익_년도
	private String 	defCl;			// 확정 구분 (8일, 15일)
	private String	plnRetCl;		// 계획_실적 구분(P:계획, R:실적)
	private Integer incItmId;		// 손익 항목 ID
	
	private BigDecimal memoMon;		// 메모 월
	private String memo;					// 메모
	private String memoVer;				// 메모 버전
	private String chgDt;					// 수정 일자

}