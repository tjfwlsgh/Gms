package com.lgl.gms.webapi.cmm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 엑셀공통 서비스
 * @author jokim
 * @Date 2022.06.28
 *
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {
	
	/**
	 * 엑셀 시트명 가지고 오기
	 */
	@Override
	public BaseResponse getExcelSheetNames(MultipartFile file, Object body) {

		try {
			final Workbook workbook = WorkbookFactory.create(file.getInputStream());

			//sheet 갯수
			int sheetCnt = workbook.getNumberOfSheets();

			List<HashMap<String, Object>> sheets = new ArrayList<HashMap<String, Object>>();

			for(int idx = 0; idx < sheetCnt; idx++){
				if(!workbook.isSheetHidden(idx)) {
					Map<String, Object> sheetMap = new HashMap<String, Object>();
					
					sheetMap.put("value", idx);
					sheetMap.put("label", workbook.getSheetName(idx));
					sheets.add((HashMap<String, Object>) sheetMap);
				}				
			}
			
			return new BaseResponse(ResponseCode.C200, sheets);
			
		} catch (Exception e) {
			
			log.error("getExcelSheetNames error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
		
	}

}
