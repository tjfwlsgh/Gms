package com.lgl.gms.webapi.fin.controller;

import java.math.BigDecimal;
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

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.AccountsReceivableReuqest;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.service.AccountsReceivableService;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : AccountsReceivableController.java
 * @Date        : 22.04.19
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 미수채권 정보 등록 Controller
 */
@Slf4j 
@RequestMapping("/fin")
@CrossOrigin
@RestController
public class AccountsReceivableController {
	
	@Autowired
	public AccountsReceivableService arService;
	
	/**
	 * 엑셀업로드 (상선포함)
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = { "/accounts-receivable/xls/including/{arYymm}/{incclCd}/{sheetNum}" }, method = RequestMethod.POST)
	public BaseResponse includingUploadExcel(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
								  , @PathVariable String arYymm, @PathVariable String incclCd
								  , @PathVariable Integer sheetNum) {
		
		AccountsReceivableReuqest body = new AccountsReceivableReuqest();
		body.setArYymm(arYymm);
		body.setIncclCd(incclCd);
		body.setSheetNum(sheetNum);
		
		log.debug("includingUploadExcel ===> {}", body);
		
		return arService.excelUpload(file, body);
	}
	
	/**
	 * 엑셀업로드 (상선제외)
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = { "/accounts-receivable/xls/excluding/{arYymm}/{incclCd}/{sheetNum}" }, method = RequestMethod.POST)
	public BaseResponse excludingUploadExcel(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
								  , @PathVariable String arYymm, @PathVariable String incclCd
								  , @PathVariable Integer sheetNum) {
		
		AccountsReceivableReuqest body = new AccountsReceivableReuqest();
		body.setArYymm(arYymm);
		body.setIncclCd(incclCd);
		body.setSheetNum(sheetNum);
		
		log.debug("excludingUploadExcel ===> {}", body);
		
		return arService.excelUpload(file, body);
	}
	
	/**
	 * 미수채권 정보 등록 list 조회 (현대 상선 포함)
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/accounts-receivable/including"}, method = RequestMethod.GET)
	public BaseResponse getIncludingMarineList(HttpServletRequest request, @ModelAttribute AccountsReceivableReuqest paramDto) {
		
		log.debug("arService.getIncludingMarineList(paramDto) ==> {}", arService.getIncludingMarineList(paramDto));
		return arService.getIncludingMarineList(paramDto);
		
	}
	
	/**
	 * 미수채권 정보 등록 list 조회 (현대 상선 제외)
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/accounts-receivable/excluding"}, method = RequestMethod.GET)
	public BaseResponse getExcludingMarineList(HttpServletRequest request, @ModelAttribute AccountsReceivableReuqest paramDto) {
		
		return arService.getExcludingMarineList(paramDto);
		
	}
	
	/**
	 * 미수채권 정보 등록 엑셀 저장 (상선 제외)
	 * @param request
	 * @param IUDObj
	 * @return
	 */
	@RequestMapping(value= {"/accounts-receivable/excluding"}, method = RequestMethod.POST)
	public BaseResponse doSaveExcludingExcel(HttpServletRequest request, @RequestBody Map<String, Object> exIUDObj) {
		log.debug("doSaveArExcel > arList ===> {}", exIUDObj);
		// TODO 로그인 되면 수정
		return arService.doSaveExcludingExcel(exIUDObj);
	}
	
	/**
	 * 미수채권 정보 등록 엑셀 저장 (상선포함)
	 * @param request
	 * @param IUDObj
	 * @return
	 */
	@RequestMapping(value= {"/accounts-receivable/including"}, method = RequestMethod.POST)
	public BaseResponse doSaveIncludingExcel(HttpServletRequest request, @RequestBody Map<String, Object> inIUDObj) {
		log.debug("doSaveArExcel > arList ===> {}", inIUDObj);
		// TODO 로그인 되면 수정
		return arService.doSaveIncludingExcel(inIUDObj);
	}
	
	/**
	 * 미수채권 정보 등록 row 삭제
	 * @param request
	 * @param IUDObj
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/accounts-receivable/{yymm}/{id}/{cd}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteSelectRow(HttpServletRequest request
			, @PathVariable String yymm, @PathVariable Integer id, @PathVariable String cd) {

		AccountsReceivableReuqest paramDto = new AccountsReceivableReuqest();
		paramDto.setArYymm(yymm);
		paramDto.setBoId(id);
		paramDto.setIncclCd(cd);
		
		return arService.delectAccountsRow(paramDto);
		
	}
	

}
