package com.lgl.gms.webapi.cmm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.cmm.dto.request.ExcelRequest;
import com.lgl.gms.webapi.cmm.service.ExcelService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse; 

/**
 * 엑셀공통 컨트롤러
 */
@CrossOrigin
@RequestMapping("/excel")
@RestController
public class ExcelController {

	@Autowired
	public ExcelService excelService;
	
	/**
	 * 엑셀시트명 리스트
	 * @param request
	 * @param file
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/sheets-name" }, method = RequestMethod.POST)
	public BaseResponse getExcelSheetNames(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file, ExcelRequest param) {
		
		return excelService.getExcelSheetNames(file, param);
	}

}
