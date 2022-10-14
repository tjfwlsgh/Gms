package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.service.IncPlanService;

/**
 * 손익계획 등록 공통 컨트롤러
 * @author jokim
 * @date 2022.02.18
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
public class IncPlanController {

	@Autowired
	public IncPlanService incPlanService;

	/**
	 * 손익계획 헤더정보 조회
	 * @param request
	 * @param body
	 * @return BaseResponse
	 */
	@RequestMapping(value = { "/inc-plan" }, method = RequestMethod.GET)
	public BaseResponse selectIncPlan(HttpServletRequest request, BoIncPlanRequest body) {

		return incPlanService.selectBoIncPlan(body);
		
	}
	
	/**
	 * 손익계획 헤더정보 저장
	 * @param request
	 * @param body
	 * @return BaseResponse
	 */
	@RequestMapping(value = { "/inc-plan" }, method = RequestMethod.POST)
	public BaseResponse insertIncPlan(HttpServletRequest request, @RequestBody BoIncPlnModel body) {
		
		return incPlanService.insertIncPlan(body, null);
		
	}


}
