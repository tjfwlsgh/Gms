package com.lgl.gms.webapi.sys.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class AccessHistRequest {
	
	private String startDate;	// 시작일자
	private String endDate;	// 종료일자
	private String logTyp;	// 로그유형
		
}
