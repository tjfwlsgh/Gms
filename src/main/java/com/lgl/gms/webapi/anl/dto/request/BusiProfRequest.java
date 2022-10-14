package com.lgl.gms.webapi.anl.dto.request;

import java.util.List;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BusiProfRequest {
	
	private String incYy; 			// 년도(ex. 2022)
	private String incMm; 			// 월(ex. 04)
	private Integer boId;			// 법인 ID
	private Integer ct; 			// 원단위(기본 만원)
	
	private String incItm1; 		// 손익항목1(매출액, 영업이익, 영업비용 등)
	private String incItm12; 		// 손익항목1(일반관리비, -- 등)
	private String incItm2; 		// 손익항목2(기존사업, 협력사업 등) => 추후의 사용을 위한 용도
	
	private Integer compId = UserInfo.getCompId();
	private String lang = UserInfo.getLocale();
}
