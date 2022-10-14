package com.lgl.gms.webapi.bse.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.lgl.gms.webapi.bse.dto.request.FormRequest;
import com.lgl.gms.webapi.bse.dto.request.IncItmDetRequest;
import com.lgl.gms.webapi.bse.dto.request.IncItmInfoRequest;
import com.lgl.gms.webapi.bse.persistence.model.IncItmDetModel;
import com.lgl.gms.webapi.bse.persistence.model.IncItmInfoModel;
import com.lgl.gms.webapi.bse.service.FormService;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : FormController.java
 * @Date        : 22.04.06
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 양식관리 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class FormController {
	
	@Autowired
	public FormService formService;
	
	/**
	 * 양식 관리 조회 (손익 항목)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-info"}, method = RequestMethod.GET)
	public BaseResponse getIncInfoList(HttpServletRequest request, @ModelAttribute FormRequest paramDto) {
		
		return formService.getIncInfoList(paramDto);
		
	}
	
	/**
	 * 손익 항목 추가
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-info"}, method = RequestMethod.POST)
	public BaseResponse addIncInfo(HttpServletRequest request, @RequestBody IncItmInfoRequest paramDto) {
		
		return formService.addIncInfo(paramDto);
		
	}
	

	/**
	 * 손익 항목 수정 
	 * @param request
	 * @param id
	 * @param paramDto
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/form-inc-itm-info/{id}"}, method = RequestMethod.PUT)
	public BaseResponse updateIncInfo(HttpServletRequest request, 
			@PathVariable Integer id,
			@RequestBody IncItmInfoModel paramDto) {
		
		paramDto.setIncItmId(id);
		
		return formService.updateIncInfo(paramDto);
	}
	
	/**
	 * 손익 항목 삭제
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-info/{id}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncInfo(HttpServletRequest request, @PathVariable Integer id) {
		
		IncItmInfoModel paramDto = new IncItmInfoModel();
		paramDto.setIncItmId(id);

		return formService.deleteIncInfo(paramDto);
		
	}
	
	
	/**
	 * 양식 관리 조회 (손익 항목 상세)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-detail"}, method = RequestMethod.GET)
	public BaseResponse getIncDetailList(HttpServletRequest request, @ModelAttribute FormRequest paramDto) {
		log.debug("FormController > getIncDetailList > paramDto ===> {}", paramDto);
		return formService.getIncDetailList(paramDto);
		
	}
	
	/**
	 * 손익 항목 상세 추가
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-detail"}, method = RequestMethod.POST)
	public BaseResponse addIncDetail(HttpServletRequest request, @RequestBody IncItmDetRequest paramDto) {
		
		return formService.addIncDetail(paramDto);
		
	}
	

	/**
	 * 손익 항목 상세 수정 
	 * @param request
	 * @param id
	 * @param paramDto
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/form-inc-itm-detail/{id}"}, method = RequestMethod.PUT)
	public BaseResponse updateIncDetail(HttpServletRequest request, 
			@PathVariable Integer id,
			@RequestBody IncItmDetModel paramDto) {
		
		paramDto.setIncItmDetId(id);
		
		return formService.updateIncItmDetail(paramDto);
	}
	
	/**
	 * 손익 항목 상세 삭제
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-detail/{id}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncDetail(HttpServletRequest request, @PathVariable Integer id) {
		
		IncItmDetModel paramDto = new IncItmDetModel();
		paramDto.setIncItmDetId(id);

		return formService.deleteIncItmDetail(paramDto);
		
	}
	
	/**
	 * 손익항목 그룹1 조회
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/form-inc-itm-info/grp1"}, method = RequestMethod.GET)
	public BaseResponse getGrp1List(HttpServletRequest request) {
		
		FormRequest param = new FormRequest();
		param.setDelYn("N");
		return formService.getGrp1List(param);
		
	}

}
