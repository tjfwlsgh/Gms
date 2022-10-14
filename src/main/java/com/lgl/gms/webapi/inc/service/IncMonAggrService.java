package com.lgl.gms.webapi.inc.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncMonAggrRequest;


/**
 * 손익집계 조회
 * @author jokim
 *@Date : 2022.03.25
 */
public interface IncMonAggrService {

	public static final String PGM_ID = "BOINC007";		// 프로그램 아이디(손익 월집계 조회))
	
	/**
	 * 손익집계조회 리스트
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncMonAggrList(BoIncMonAggrRequest param);
	

	
}
