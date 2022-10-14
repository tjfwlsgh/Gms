package com.lgl.gms.webapi.cmm.service;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface ExcelService {
	
	/**
	 * 엑셀 Sheets 명 리스트 가지고 오기
	 * @param file
	 * @param body
	 * @return
	 */
	public BaseResponse getExcelSheetNames(MultipartFile file, Object body);

}
