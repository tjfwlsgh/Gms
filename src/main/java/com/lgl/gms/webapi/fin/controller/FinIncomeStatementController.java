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
import com.lgl.gms.webapi.fin.dto.request.FinIncomeStatementRequest;
import com.lgl.gms.webapi.fin.service.FinCommonService;
import com.lgl.gms.webapi.fin.service.FinIncomeStatementService;

import lombok.extern.slf4j.Slf4j;

/**
 * 재무 > 손익계산서 컨트롤러
 * @author jokim
 * @date 2022.04.13
 */
@CrossOrigin
@Slf4j
@RequestMapping("/fin")
@RestController
public class FinIncomeStatementController {
	
	@Autowired
	public FinCommonService finService;
	
	@Autowired
	public FinIncomeStatementService plStatementService;
	
	/**
	 * 손익계산서 리스트
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/pl-statements" }, method = RequestMethod.GET)
	public BaseResponse selectPlStatementList(HttpServletRequest request, @ModelAttribute FinIncomeStatementRequest param) {
		
		return plStatementService.selectFinPlStatementList(param);
		
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
	@RequestMapping(value = { "/pl-statements/xls/{sheetNum}/{frmId}/{crncyType}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file, 
			FinIncomeStatementRequest body, @PathVariable Integer sheetNum, @PathVariable Integer frmId, @PathVariable String crncyType) {
		
		body.setSheetNum(sheetNum);
		body.setFrmId(frmId);
		body.setCrncyType(crncyType);
		
		return plStatementService.excelUpload(file, body);
	}
	
	/**
	 * 손익계산서 저장
	 * @param request
	 * @param incPlnSals
	 * @return
	 */
	@RequestMapping(value = { "/pl-statements" }, method = RequestMethod.POST)
	public BaseResponse savePlStatements(HttpServletRequest request, @RequestBody HashMap<String, Object> saveFinPlMap) {
		
		return plStatementService.saveFinIncomeStatement(saveFinPlMap);

	}	
	

}
