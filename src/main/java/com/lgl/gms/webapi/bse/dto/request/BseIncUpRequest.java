package com.lgl.gms.webapi.bse.dto.request;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

/**
 * 손익 데이터 업로드 현황 Request
 * @author hj.Chung
 * @Date 2022.07.18
 */
@Data
public class BseIncUpRequest {
	
	// 검색 조건
	private String trrtId;	// 지역 id
	private Integer boId;	// 법인 id
	private String yymm;	// 년월

	private String locale = UserInfo.getLocale();
	
}
