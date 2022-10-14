package com.lgl.gms.webapi.inc.persistence.model;

	import com.lgl.gms.webapi.common.model.BaseModel;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 법인 손익 실적 헤더
 * @author jokim
 * 2022.03.10
 */
@Data
public class BoIncRetModel extends BaseModel {
	
	private Integer boId;		// 법인 ID
	private String incYymm;	// 손익 실적 년월
	private String defCl;			// 확정 구분 (8일, 15일)
	private String retCls1;		// 실적마감1(매출)
	private String retCls2;		// 실적마감2(비용)
	private String retCls3;		// 실적마감3(본사)
	private String crncyCd;		// 통화코드
	private String cntryCd;		// 국가코드
	private String retStd;		// 실적시작일
	private String retEtd;		// 실적종료일

}