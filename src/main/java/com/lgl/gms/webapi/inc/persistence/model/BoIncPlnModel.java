package com.lgl.gms.webapi.inc.persistence.model;

import java.util.Date;

import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

/**
 * 법인 손익 계획 헤더
 * @author jokim
 * 2022.02.21
 */
@Data
public class BoIncPlnModel extends BaseModel {
	
	private Integer boId;				// 법인 ID
	private String incYy;		// 법인손익년도	
	private Integer finlVer;			// 최종 버전
	private String plnCls1;		// 계획마감1(매출)
	private String plnCls2;		// 계획마감2(비용)
	private String plnCls3;		// 계획마감3(본사)
	private String crncyCd;		// 통화코드
	private String cntryCd;		// 국가코드


}