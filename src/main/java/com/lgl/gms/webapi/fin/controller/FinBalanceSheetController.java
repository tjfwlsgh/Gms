package com.lgl.gms.webapi.fin.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.FinBalanceSheetRequest;
import com.lgl.gms.webapi.fin.service.FinBalanceSheetService;
import com.lgl.gms.webapi.fin.service.FinCommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 재무상태표 컨트롤러
 * @author jokim
 * @date 2022.04.13
 */
@CrossOrigin
@Slf4j
@RequestMapping("/fin")
@RestController
public class FinBalanceSheetController {
	
	@Autowired
	public FinCommonService finService;
	
	@Autowired
	public FinBalanceSheetService bsSheetService;
	
	/**
	 * 재무상태표 목록
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/bs-sheets" }, method = RequestMethod.GET)
	public BaseResponse selectBsSheetList(HttpServletRequest request, @ModelAttribute FinBalanceSheetRequest param) {
		
		return bsSheetService.selectFinBsSheetList(param);
		
	}
	
	/**
	 * 엑셀업로드
	 * @param request
	 * @param file
	 * @param body
	 * @param frmId
	 * @param incYy
	 * @return
	 */
	@RequestMapping(value = { "/bs-sheets/xls/{sheetNum}/{frmId}/{crncyType}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file, 
				FinBalanceSheetRequest body, @PathVariable Integer sheetNum, @PathVariable Integer frmId, @PathVariable String crncyType) {
		
		body.setSheetNum(sheetNum);
		body.setFrmId(frmId);
		body.setCrncyType(crncyType);
		
		return bsSheetService.excelUpload(file, body);
	}
	
	/**
	 * 재무상태표 저장
	 * @param request
	 * @param saveFinBalanceMap
	 * @return
	 */
	@RequestMapping(value = { "/bs-sheets" }, method = RequestMethod.POST)
	public BaseResponse saveBsSheets(HttpServletRequest request, @RequestBody HashMap<String, Object> saveFinBalanceMap) {
		
		return bsSheetService.saveFinBalanceSheet(saveFinBalanceMap);

	}	
	

}
