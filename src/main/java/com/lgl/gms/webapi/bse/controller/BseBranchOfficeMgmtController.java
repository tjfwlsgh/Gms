package com.lgl.gms.webapi.bse.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.bse.dto.request.BseBranchOfficeMgmtRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoModel;
import com.lgl.gms.webapi.bse.service.BseBranchOfficeMgmtService;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.service.BoService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseBranchOfficeController.java
 * @Date        : 22.03.14
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 마스터 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class BseBranchOfficeMgmtController {
	
	@Autowired
	public BseBranchOfficeMgmtService bbomService;
	
	@Autowired
	public BoService boService;
	
	
	/**
	 * 법인 관리 조회 (검색)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/branch-offices"}, method = RequestMethod.GET)
	public BaseResponse getBranchOfficeMgmtList(HttpServletRequest request, @ModelAttribute BseBranchOfficeMgmtRequest paramDto) {
		
		return bbomService.getBranchOfficeMgmt(paramDto);
		
	}
	
	/**
	 * 법인 관리 추가
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/branch-offices"}, method = RequestMethod.POST)
	public BaseResponse addBranchOfficeMgmt(HttpServletRequest request, 
			@RequestBody BseBranchOfficeMgmtRequest paramDto) {
		
		return bbomService.addBranchOfficeMgmt(paramDto);
		
	}
	
	/**
	 * 법인코드 존재여부 체크
	 * @param request
	 * @param boCd
	 * @param boId
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/branch-offices/check" }, method = RequestMethod.GET)
	public BaseResponse checkBoCd(HttpServletRequest request, 
			@RequestParam(defaultValue = "") String boCd,
			@RequestParam(defaultValue="", required = false) Integer boId) {
		
		BseBoModel param = new BseBoModel();
		param.setBoCd(boCd);
		param.setBoId(boId);
		
		return bbomService.checkBoCd(param);
	}
	
	/**
	 * 법인 관리 삭제 
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/branch-offices/{id}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteBranchOfficeMgmt(HttpServletRequest request, @PathVariable Integer id) {
		
		BseBranchOfficeMgmtRequest paramDto = new BseBranchOfficeMgmtRequest();
		paramDto.setBoId(id);
		return bbomService.deleteBranchOfficeMgmt(paramDto);
		
	}
	
	
	/**
	 * 법인 관리 수정 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/branch-offices/{id}"}, method = RequestMethod.PUT)
	public BaseResponse modifyBranchOfficeMgmt(HttpServletRequest request, @PathVariable Integer id, @RequestBody BseBoModel paramDto) {
		paramDto.setBoId(id);
		return bbomService.modifyBranchOfficeMgmt(paramDto);
	}
	
	/**
	 * 법인 목록 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/branch-offices/bos"}, method = RequestMethod.GET)
	public BaseResponse getBoList(HttpServletRequest request) {
		BoRequest param = new BoRequest();
		param.setCompId(100);
		return boService.selectBoList(param);
	}
	
	/**
	 * 지사 목록 조회
	 * @param request
	 * @param boId
	 * @return
	 */
	@RequestMapping(value = {"/branch-offices/{boId}/count"}, method = RequestMethod.GET)
	public BaseResponse getPboCount(HttpServletRequest request, @PathVariable Integer boId) {
		BseBoModel param = new BseBoModel();
		param.setBoId(boId);
		return bbomService.selectPboCount(param);
	}
	
	
}
