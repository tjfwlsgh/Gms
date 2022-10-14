package com.lgl.gms.webapi.fin.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.response.ArIncreAnalysisResponse;
import com.lgl.gms.webapi.fin.service.ArIncreAnalysisService;

import lombok.extern.slf4j.Slf4j;



/**
 * @FileName    : ArIncreAnalysisController.java
 * @Date        : 22.04.12
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 미수채권 증감 현황 Controller
 */
@Slf4j 
@RequestMapping("/fin")
@CrossOrigin
@RestController
public class ArIncreAnalysisController {
	
	@Autowired
	public ArIncreAnalysisService aiaService;
	
	
	/**
	 * 미수채권 증감 현황 list 조회
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/ar-incre-analysis"}, method = RequestMethod.GET)
	public BaseResponse getArIncreAnalysisList(HttpServletRequest request, @ModelAttribute ArIncreAnalysisRequest paramDto) {
		
		return aiaService.getArIncreAnalysisList(paramDto);
		
	}
	
	/**
	 * 엑셀업로드
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = { "/ar-incre-analysis/xls/{arYymm}/{sheetNum}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
								  , @PathVariable String arYymm, @PathVariable Integer sheetNum) {
		
		ArIncreAnalysisRequest body = new ArIncreAnalysisRequest();
		body.setArYymm(arYymm);
		body.setSheetNum(sheetNum);
		
		return aiaService.excelUpload(file, body);
	}
	
	/**
	 * 미수채권 증감 현황 저장
	 * @param request
	 * @param IUDObj
	 * @return
	 */
	@RequestMapping(value= {"/ar-incre-analysis"}, method = RequestMethod.POST)
	public BaseResponse doSaveArExcel(HttpServletRequest request, @RequestBody Map<String, Object> IUDObj) {
		
		log.debug("doSaveArExcel > arList ===> {}", IUDObj);
		
		return aiaService.doSaveArExcel(IUDObj);
		
	}
	
	/**
	 * 미수채권 증감 현황 row 삭제
	 * @param request
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/ar-incre-analysis/{yymm}/{id}/{seq}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteArIncreAnalysis(HttpServletRequest request
			, @PathVariable String yymm, @PathVariable Integer id, @PathVariable BigDecimal seq) {

		ArIncreAnalysisRequest paramDto = new ArIncreAnalysisRequest();
		paramDto.setArYymm(yymm);
		paramDto.setBoId(id);
		paramDto.setArSeq(seq);
		
		return aiaService.deleteArIncreAnalysis(paramDto);
		
	}
	
}
