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
import com.lgl.gms.webapi.inf.dto.request.InfEquipmentMgmtRequest;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;
import com.lgl.gms.webapi.inf.service.InfEquipmentMgmtService;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : InfEquipmentMgmtController.java
 * @Date        : 22.03.11
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 장비현황 관리 Controller
 */
@Slf4j 
@RequestMapping("/inf")
@CrossOrigin
@RestController
public class InfEquipmentMgmtController {
	
	@Autowired
	public InfEquipmentMgmtService iemService;
	
	/**
	 * 법인 장비현황 관리 조회 (검색)
	 * 
	 * @param request
	 * @param body
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/equipments"}, method = RequestMethod.GET)
	public BaseResponse getEquipmentMgmt(HttpServletRequest request, @ModelAttribute InfEquipmentMgmtRequest paramDto) {
		
		return iemService.getEquipmentMgmtList(paramDto);
		
	}
	
	/**
	 * 법인 장비현황 관리 추가
	 * @param request
	 * @param body
	 * @return
	 */
	
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/equipments" }, method = RequestMethod.POST)
	public BaseResponse addEquipmentMgmt(HttpServletRequest request, @Valid @RequestBody InfEquipmentMgmtRequest paramDto) {
		return iemService.addEquipmentMgmt(paramDto);
	}
	
	
	
	/**
	 * 법인 장비현황 관리 삭제
	 * @param request
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/equipments/{id}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteEquipmentMgmt(HttpServletRequest request, @PathVariable Integer id) {

		InfEquipmentMgmtRequest paramDto = new InfEquipmentMgmtRequest();
		paramDto.setBoRntId(id);
		return iemService.delete(paramDto);
		
	}
	
	
	/**
	 * 법인 장비현황 관리 수정
	 * @param request
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/equipments/{boRntId}"}, method = RequestMethod.PUT)
	public BaseResponse modifyEquipmentMgmt(HttpServletRequest request, @PathVariable Integer boRntId, @Valid @RequestBody InfraRntModel paramDto) {
		
		paramDto.setBoRntId(boRntId);
		
		return iemService.modifyEquipmentMgmt(paramDto);
	}

}
