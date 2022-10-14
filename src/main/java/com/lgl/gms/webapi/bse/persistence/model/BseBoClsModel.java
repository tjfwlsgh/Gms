package com.lgl.gms.webapi.bse.persistence.model;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.model.BaseModel;

import lombok.Data;

@Data
public class BseBoClsModel extends BaseModel {
	
	private Integer boId; // 법인 id
	private String yy; // 년도
	private String clsPgm; // 마감 프로그램 (유형)
	private String clsNo; // 마감 번호
	private Integer compId = UserInfo.getCompId();  // 회사 id
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
	private String locale = UserInfo.getLocale();	// 언어
	private String pgmId;		// 프로그램ID
	private String taskNm; 		// 작업구분
	private String rtn;			// 프로시저 RTN

	
//	private Date regDt; // 등록일자
//	private Date updDt; // 수정일자
//	private String workIp; // 작업자Ip
//	private String regNo; // 등록자 no
//	private String updNo; // 갱신자 no


}
