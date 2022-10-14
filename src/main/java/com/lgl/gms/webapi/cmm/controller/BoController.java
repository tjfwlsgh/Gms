package com.lgl.gms.webapi.cmm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.service.BoService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse; 

/**
 * 법인컨트롤러
 */
@CrossOrigin
@RequestMapping("/cmm")
@RestController
public class BoController {

	@Autowired
	public BoService boService;
	
	/**
	 * 법인 목록 조회
	 */
	@RequestMapping(value = { "/bos" }, method = RequestMethod.GET)
	public BaseResponse getBoList(HttpServletRequest request) {
		
		BoRequest param = new BoRequest();
		param.setUseYn("Y");
		return boService.selectBoList(param);
		
	}
}
