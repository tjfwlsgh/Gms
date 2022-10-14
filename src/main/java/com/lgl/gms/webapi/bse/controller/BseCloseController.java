package com.lgl.gms.webapi.bse.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.bse.dto.request.BseCloseRequest;
import com.lgl.gms.webapi.bse.service.BseCloseService;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseCloseController.java
 * @Date        : 22.04.01
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 마감 현황 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class BseCloseController {
	
	@Autowired
	public BseCloseService bcService;
	
	/**
	 * 마감 현황 조회 (검색)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/closes"}, method = RequestMethod.GET)
	public BaseResponse getCloseList(HttpServletRequest request, @ModelAttribute BseCloseRequest paramDto) {
		return bcService.getCloseList(paramDto);
		
	}
	
	/**
	 * 법인 목록 조회 (법인 + 지사)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/closes/bos"}, method = RequestMethod.GET)
	public BaseResponse getBoList(HttpServletRequest request) {
		BoRequest param = new BoRequest();
		param.setCompId(100);
		param.setUseYn("Y");
		
		return bcService.selectBoList(param);
	}
	
	/**
	 * 마감 현황 수정 (해제)
	 * @param request
	 * @param modelList
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/clear"}, method = RequestMethod.PUT)
	public BaseResponse updateBoClear(HttpServletRequest request, @RequestBody Map<String, Object> IUDObj) {
		
		return bcService.updateBoClear(IUDObj);
	}
	
	/**
	 * 마감 현황 수정 (마감)
	 * @param request
	 * @param modelList
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/closure"}, method = RequestMethod.PUT)
	public BaseResponse updateCheckedCloser(HttpServletRequest request
						, @RequestBody Map<String, Object> IUDObj) {
		
		return bcService.updateCheckedCloser(IUDObj);
	}
	

}
