package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncMonAggrRequest;
import com.lgl.gms.webapi.inc.service.IncMonAggrService;

import lombok.extern.slf4j.Slf4j;

/**
 * 비교손익 컨트롤러
 * @author jokim
 * @date 2022.03.25
 */
@CrossOrigin
@RequestMapping("/inc")
@Slf4j
@RestController
public class IncMonAggrController {

	@Autowired
	public IncMonAggrService incMonAggrService;

	/**
	 * 비교손익 목록 조회
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/mon-aggrs" }, method = RequestMethod.GET)
	public BaseResponse selectIncMonAggrList(HttpServletRequest request, @ModelAttribute BoIncMonAggrRequest param) {

		return incMonAggrService.selectIncMonAggrList(param);
		
	}



}
