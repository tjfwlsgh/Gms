package com.lgl.gms.webapi.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.AccessHistRequest;
import com.lgl.gms.webapi.sys.service.AccessHistService;

/**
 * 접근이력 Controller
 */
@CrossOrigin
@RequestMapping("/sys")
@RestController
public class AccessHistController {

	// MyBatis 사용
	@Autowired
	public AccessHistService accessHistService;

	// POST/PUT/GET/DELETE method 방식

	/**
	 * 접근이력 목록 조회
	 * @param request
	 * @param startDate
	 * @param endDate
	 * @param logTyp
	 * @return
	 * @throws java.lang.Exception
	 */
	@RequestMapping(value = { "/access-hists" }, method = RequestMethod.GET)
	public BaseResponse getPgms(HttpServletRequest request,
					@RequestParam(defaultValue = "") String startDate,
					@RequestParam(defaultValue = "") String endDate,
					@RequestParam(defaultValue = "") String logTyp
			) throws java.lang.Exception {

		AccessHistRequest body = new AccessHistRequest();
		body.setStartDate(startDate);
		body.setEndDate(endDate);
		body.setLogTyp(logTyp);
		
		return accessHistService.getAccessHistList(body);
	}
	


}
