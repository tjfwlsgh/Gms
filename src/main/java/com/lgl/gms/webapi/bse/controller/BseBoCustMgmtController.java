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
import com.lgl.gms.webapi.bse.dto.request.BseBoCustMgmtRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoCustModel;
import com.lgl.gms.webapi.bse.service.BseBoCustMgmtService;
import com.lgl.gms.webapi.cmm.service.BoService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseBoCustMgmtController.java
 * @Date        : 22.03.28
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 거래처 마스터 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class BseBoCustMgmtController {
	
	@Autowired
	public BseBoCustMgmtService bbcmService;
	
	@Autowired
	public BoService boService;
	
	/**
	 * 법인 거래처 관리 조회 (검색)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/bo-custs"}, method = RequestMethod.GET)
	public BaseResponse getBoCustMgmtList(HttpServletRequest request, @ModelAttribute BseBoCustMgmtRequest paramDto) {
		
		return bbcmService.getBoCustMgmt(paramDto);
		
	}
	
	/**
	 * 법인 거래처 관리 추가
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/bo-custs"}, method = RequestMethod.POST)
	public BaseResponse addBoCustMgmt(HttpServletRequest request, @RequestBody BseBoCustMgmtRequest paramDto) {
		
		return bbcmService.addBoCustMgmt(paramDto);
		
	}
	
	
	/**
	 * 법인 거래처 코드 중복 체크
	 * 
	 * @param request
	 * @param boCustCd
	 * @param boId
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/bo-custs/check"}, method = RequestMethod.GET)
	public BaseResponse checkBoCustCd(HttpServletRequest request, 
			@RequestParam(defaultValue="") String boCustCd,
			@RequestParam(defaultValue="") Integer boId) {
		
		BseBoCustModel paramDto = new BseBoCustModel();
		paramDto.setBoCustCd(boCustCd);
		paramDto.setBoId(boId);
		
		return bbcmService.checkBoCustCd(paramDto);
		
	}
	
	/**
	 * 법인 거래처 관리 삭제
	 * 
	 * @param request
	 * @param boId
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/bo-custs/{id}/{cd}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteBoCustMgmt(HttpServletRequest request, 
			@PathVariable Integer id, 
			@PathVariable String cd) {
		
		BseBoCustModel paramDto = new BseBoCustModel();
		paramDto.setBoId(id);
		paramDto.setBoCustCd(cd);
		return bbcmService.deleteBoCustMgmt(paramDto);
		
	}
	
	/**
	 * 법인 거래처 관리 수정 
	 * @param request
	 * @param id
	 * @param paramDto
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/bo-custs/{id}/{cd}"}, method = RequestMethod.PUT)
	public BaseResponse updateBoCustMgmt(HttpServletRequest request, @PathVariable Integer id,
			@PathVariable String cd, @RequestBody BseBoCustModel paramDto) {
		
		paramDto.setBoId(id);
		paramDto.setBoCustCd(cd);
		return bbcmService.updateBoCustMgmt(paramDto);
	}
	
	

}
