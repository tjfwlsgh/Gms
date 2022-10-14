package com.lgl.gms.webapi.bse.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.bse.dto.request.BseBranchOfficeMgmtRequest;
import com.lgl.gms.webapi.bse.dto.request.BseIncUpRequest;
import com.lgl.gms.webapi.bse.service.BseBranchOfficeMgmtService;
import com.lgl.gms.webapi.bse.service.BseIncUpService;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.service.BoService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseIncUpController.java
 * @Date        : 22.07.18
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 손익 데이터 업로드 현황 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class BseIncUpController {
	
	@Autowired
	public BseIncUpService biuService;
	
	/**
	 * 실적 현황 조회 (검색)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/inc-up-ret"}, method = RequestMethod.GET)
	public BaseResponse getIncRetList(HttpServletRequest request, @ModelAttribute BseIncUpRequest paramDto) {
		
		log.debug("BseIncUpRequest ===> {}", paramDto);
		
		return biuService.getIncRetList(paramDto);
		
	}
	
	/**
	 * 계획 현황 조회 (검색)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/inc-up-pln"}, method = RequestMethod.GET)
	public BaseResponse getIncPlnList(HttpServletRequest request, @ModelAttribute BseIncUpRequest paramDto) {
		
		log.debug("BseIncUpRequest ===> {}", paramDto);
		
		return biuService.getIncPlnList(paramDto);
		
	}
	
}
