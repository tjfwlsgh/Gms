package com.lgl.gms.webapi.fin.service;

import java.util.HashMap;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.fin.dto.request.FinBalanceSheetRequest;

/**
 * 재무제표
 * @author jokim
 * @Date : 2022.04.13
 */
public interface FinBalanceSheetService extends ExcelUploadService {
	
	public static final String PGM_ID = "BOFIN001";		// 프로그램 아이디(재무제표 등록)
	
	/**
	 * 재무제표 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectFinBsSheetList(FinBalanceSheetRequest param);
	
	/**
	 * 저장
	 * @param saveFinBalanceMap
	 * @return
	 */
	public BaseResponse saveFinBalanceSheet(HashMap<String, Object> saveFinBalanceMap);
	
}
