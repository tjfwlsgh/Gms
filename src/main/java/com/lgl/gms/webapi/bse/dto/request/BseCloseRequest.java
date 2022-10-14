package com.lgl.gms.webapi.bse.dto.request;

import com.lgl.gms.webapi.bse.persistence.model.BseBoClsModel;
import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BseCloseRequest {
	
	private Integer boId; // 법인 id
	private String yy; // 년도
	private String yymm;	// 년월
	private String incYymm;	// 프로시저 년월
	private String pgm; // 마감 프로그램
	private String clsNo;
	private String clsFlg;
	private String rtn;
	
	private Integer compId = UserInfo.getCompId(); 	// 회사 ID
	private String locale = UserInfo.getLocale();
	
	private String clsPgm; // 마감 프로그램 (유형)
	private String mon01; // 월 01
	private String mon02; // 월 02
	private String mon03; // 월 03
	private String mon04; // 월 04
	private String mon05; // 월 05
	private String mon06; // 월 06
	private String mon07; // 월 07
	private String mon08; // 월 08
	private String mon09; // 월 09
	private String mon10; // 월 10
	private String mon11; // 월 11
	private String mon12; // 월 12
	private String delYn; // 삭제Yn
	
	private String workIp = UserInfo.getWorkIp();		// 작업자 IP
	private String updNo = UserInfo.getUserId();		// 사용자 NO
	private String pgmId;		// 프로그램ID
	private String taskNm; 		// 작업구분
	
	private String defCl;		// 확정 구분 (8일, 15일) : Q1 / Q2
	private String plnRetCl;		// 계획실적구분, Plan=P, Result=R
	private String retCls1;		// 실적마감1(매출)
	private String retCls2;		// 실적마감2(비용)
	private String retCls3;		// 실적마감3(본사)
	
	private String jobCl;	 


}
