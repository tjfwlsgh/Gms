package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;
import com.lgl.gms.webapi.inc.service.IncRetService;

import lombok.extern.slf4j.Slf4j;

/**
 * 실적 공통컨트롤러
 * @author jokim
 *
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
@Slf4j
public class IncRetController {

	@Autowired
	public IncRetService incRetService;

	/**
	 * 손익실적 헤더정보 조회
	 * @param request
	 * @param body
	 * @return BaseResponse
	 */
	@RequestMapping(value = { "/inc-ret" }, method = RequestMethod.GET)
	public BaseResponse selectIncPlan(HttpServletRequest request, BoIncRetRequest param) {
		
		return incRetService.selectIncRet(param);
		
	}
	
	/**
	 * 손익실적 헤더정보 저장
	 * @param request
	 * @param body
	 * @return BaseResponse
	 */
	@RequestMapping(value = { "/inc-ret" }, method = RequestMethod.POST)
	public BaseResponse insertIncPlan(HttpServletRequest request, @RequestBody BoIncRetModel paramModel) {

		return incRetService.insertIncRet(paramModel, null);
		
	}


}
