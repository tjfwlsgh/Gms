package com.lgl.gms.webapi.inc.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncCurrMonPerfRequest;


/**
 * 손익당월실적 조회
 * @author jokim
 *@Date : 2022.03.31
 */
public interface IncCurrMonPerfService {

	public static final String PGM_ID = "BOINC008";	// 프로그램 아이디(손익 당월실적 조회)
	
	/**
	 * 손익당월실적 리스트
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncCurrMonPerfList(BoIncCurrMonPerfRequest param);
	
	/**
	 * 조회용 항목리스트
	 * @return
	 */
	public BaseResponse selectItemInfoList();
	
}
