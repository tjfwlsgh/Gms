package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncDataInqRequest;
import com.lgl.gms.webapi.inc.service.IncDataInqService;

import lombok.extern.slf4j.Slf4j;

/**
 * 손익데이터 조회
 * @author jokim
 * @date 2022.04.06
 */
@CrossOrigin
@RequestMapping("/inc")
@Slf4j
@RestController
public class IncDataInqController {

	@Autowired
	public IncDataInqService dataInqService;

	/**
	 * 손익 데이터 조회 리스트
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/data-inqs" }, method = RequestMethod.GET)
	public BaseResponse selectIncMonAggrList(HttpServletRequest request, @ModelAttribute BoIncDataInqRequest param) {
		
		return dataInqService.selectIncDataInqList(param);
		
	}
	

}
