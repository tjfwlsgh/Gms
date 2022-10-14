package com.lgl.gms.webapi.inc.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncDataInqRequest;


/**
 * 손익데이터 조회
 * @author jokim
 *@Date : 2022.04.06
 */
public interface IncDataInqService {

	public static final String PGM_ID = "BOINC009";	// 프로그램 아이디(손익 데이터 검색)
	
	/**
	 * 손익데이터 리스트
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncDataInqList(BoIncDataInqRequest param);
	

}
