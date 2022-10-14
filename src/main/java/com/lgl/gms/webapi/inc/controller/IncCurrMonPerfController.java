package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncCurrMonPerfRequest;
import com.lgl.gms.webapi.inc.service.IncCurrMonPerfService;

import lombok.extern.slf4j.Slf4j;

/**
 * 손익리포트
 * @author jokim
 * @date 2022.03.31
 */
@CrossOrigin
@RequestMapping("/inc")
@Slf4j
@RestController
public class IncCurrMonPerfController {

	@Autowired
	public IncCurrMonPerfService currMonPerfService;

	/**
	 * 손익리포트 목록 조회
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/curr-mon-perfs" }, method = RequestMethod.GET)
	public BaseResponse selectIncMonAggrList(HttpServletRequest request, @ModelAttribute BoIncCurrMonPerfRequest param) {

		return currMonPerfService.selectIncCurrMonPerfList(param);
		
	}
	
	/**
	 * 손익항목 조회용 콤포목록 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/curr-mon-perfs/item" }, method = RequestMethod.GET)
	public BaseResponse selectItemInfoList(HttpServletRequest request) {

		return currMonPerfService.selectItemInfoList();
		
	}

	

}
