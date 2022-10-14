package com.lgl.gms.webapi.fin.service;

import java.util.HashMap;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.fin.dto.request.FinIncomeStatementRequest;

/**
 * 재무 > 손익계산서
 * @author jokim
 * @Date : 2022.04.13
 */
public interface FinIncomeStatementService extends ExcelUploadService {
	
	public static final String PGM_ID = "BOFIN002";		// 프로그램 아이디(손익계산서 등록)
	
	/**
	 * 손익계산서 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectFinPlStatementList(FinIncomeStatementRequest param);
	
	/**
	 * 저장
	 * @param saveFinPlMap
	 * @return
	 */
	public BaseResponse saveFinIncomeStatement(HashMap<String, Object> saveFinPlMap);
	
}
