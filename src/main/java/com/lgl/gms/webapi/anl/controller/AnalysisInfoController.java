package com.lgl.gms.webapi.anl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.anl.dto.request.BoSummaryRequest;
import com.lgl.gms.webapi.anl.dto.request.BusiProfRequest;
import com.lgl.gms.webapi.anl.dto.request.CustPerfRequest;
import com.lgl.gms.webapi.anl.service.AnalysisInfoService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;


/**
 * 분석관리 Controller
 */
@Slf4j
@CrossOrigin
@RequestMapping("/anl")
@RestController
public class AnalysisInfoController {

	@Autowired
	public AnalysisInfoService analysisInfoService;

	// 법인 요약정보 조회
	@RequestMapping(value = { "/bo-summary" }, method = RequestMethod.GET)
	 	public BaseResponse getBoSummaryInfo(HttpServletRequest request,
	 			@Valid @ModelAttribute BoSummaryRequest boSummaryRequest)
	 			throws java.lang.Exception {

	 		log.debug("AnalysisInfoController > getBoSummaryInfo > boSummaryRequest : " + boSummaryRequest);
	 		return analysisInfoService.selectBoSummaryInfo(boSummaryRequest);
	 	}

	// 영업이익 추이
	@RequestMapping(value = { "/busi-profs" }, method = RequestMethod.GET)
	 	public BaseResponse getBusiProfList(HttpServletRequest request,
	 			@Valid @ModelAttribute BusiProfRequest busiProfRequest)
	 			throws java.lang.Exception {

	 		log.debug("AnalysisInfoController > getCustPerfList > busiProfRequest : " + busiProfRequest);
	 		return analysisInfoService.selectBusiProfList(busiProfRequest);
	 	}

	// 거래처 매출분석 조회
	@RequestMapping(value = { "/cust-perfs" }, method = RequestMethod.GET)
	 	public BaseResponse getCustPerfList(HttpServletRequest request,
	 			@Valid @ModelAttribute CustPerfRequest custPerfRequest)
	 			throws java.lang.Exception {

	 		log.debug("AnalysisInfoController > getCustPerfList > custPerfRequest : " + custPerfRequest);
	 		return analysisInfoService.selectCustPerfList(custPerfRequest);
	 	}

}
