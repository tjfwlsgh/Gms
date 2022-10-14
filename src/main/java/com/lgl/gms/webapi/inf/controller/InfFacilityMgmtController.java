package com.lgl.gms.webapi.inf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inf.dto.request.InfFacilityMgmtRequest;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;
import com.lgl.gms.webapi.inf.service.InfFacilityMgmtService;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : InfFacilityMgmtController.java
 * @Date        : 22.02.18
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 부동산현황 관리 Controller
 */
@Slf4j
@RequestMapping("/inf")
@CrossOrigin
@RestController
public class InfFacilityMgmtController {
	
	@Autowired
	public InfFacilityMgmtService ifmService;
	
	/**
	 * 법인 부동산현황 관리 조회 (검색)
	 * 
	 * @param request
	 * @param body
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/facilities"}, method = RequestMethod.GET)
	public BaseResponse getFacilityMgmt(HttpServletRequest request, @ModelAttribute InfFacilityMgmtRequest paramDto) {
		return ifmService.getFacilityMgmtList(paramDto);
		
	}
	
	/**
	 * 법인 부동산현황 관리 추가
	 * @param request
	 * @param body
	 * @return
	 */
	
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/facilities" }, method = RequestMethod.POST)
	public BaseResponse addFacilityMgmt(HttpServletRequest request, @Valid @RequestBody InfFacilityMgmtRequest paramDto) {
		return ifmService.addFacilityMgmt(paramDto);
	}
	
	
	
	/**
	 * 법인 부동산현황 관리 삭제
	 * @param request
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/facilities/{id}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteFacilityMgmt(HttpServletRequest request, @PathVariable Integer id) {

		InfFacilityMgmtRequest paramDto = new InfFacilityMgmtRequest();
		paramDto.setBoRntId(id);
		return ifmService.delete(paramDto);
		
	}
	
	
	/**
	 * 법인 부동산현황 관리 수정
	 * @param request
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/facilities/{boRntId}"}, method = RequestMethod.PUT)
	public BaseResponse modifyFacilityMgmt(HttpServletRequest request, @PathVariable Integer boRntId, @Valid @RequestBody InfraRntModel paramDto) {
		paramDto.setBoRntId(boRntId);
		return ifmService.modifyFacilityMgmt(paramDto);
	}
	
	
	

}
