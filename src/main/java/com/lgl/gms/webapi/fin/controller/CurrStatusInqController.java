package com.lgl.gms.webapi.fin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.request.FinCurrStatusInqRequest;
import com.lgl.gms.webapi.fin.service.CurrStatusInqService;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : CurrStatusInqController.java
 * @Date        : 22.05.06
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 재무제표 리포트 Controller
 */
@Slf4j 
@RequestMapping("/fin")
@CrossOrigin
@RestController
public class CurrStatusInqController {
	
	@Autowired
	public CurrStatusInqService csiService;
	
	/**
	 * 재무제표 list 조회
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/curr-status-inq/balance-sheets"}, method = RequestMethod.GET)
	public BaseResponse getBalanSheets(HttpServletRequest request, @ModelAttribute FinCurrStatusInqRequest paramDto) {
		
		paramDto.setCt(1000000);
		return csiService.getBalanSheetList(paramDto);
		
	}
	
	/**
	 * 법인 list 
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/curr-status-inq/bos"}, method = RequestMethod.GET)
	public BaseResponse getBoList(HttpServletRequest request) {
		
		BoRequest param = new BoRequest();
		param.setCompId(UserInfo.getCompId());
		param.setUseYn("Y");
		
		return csiService.getBoList(param);
		
	}
	
	/**
	 * 손익 계산서 list 조회
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/curr-status-inq/inc-statements"}, method = RequestMethod.GET)
	public BaseResponse getIncStatement(HttpServletRequest request, @ModelAttribute FinCurrStatusInqRequest paramDto) {
		
		paramDto.setCt(1000000);
		
		log.debug("CurrStatusInqController > paramDto =====> {}", paramDto);
		return csiService.getIncStatementList(paramDto);
		
	}
	
}
