package com.lgl.gms.webapi.cmm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.cmm.dto.request.CommonRequest;
import com.lgl.gms.webapi.cmm.service.CommonService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse; 

/**
 * 공통 컨트롤러
 * @author jokim
 * @date 2022.07.27
 */
@CrossOrigin
@RequestMapping("/cmm")
@RestController
public class CommonController {

	@Autowired
	public CommonService commonService;
	
	/**
	 * 최종환율 년월 조회
	 */
	@RequestMapping(value = { "/exchgrateym" }, method = RequestMethod.GET)
	public BaseResponse getExchgRateYm(HttpServletRequest request, @ModelAttribute CommonRequest param) {

		return commonService.getLastExchgRateYm(param);
		
	}
}
