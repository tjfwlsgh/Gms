package com.lgl.gms.webapi.cmm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.cmm.dto.request.CommonCodeRequest;
import com.lgl.gms.webapi.cmm.service.CommonCodeService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

/**
 * 공통코드 조회
 */
@CrossOrigin
@RequestMapping("/cmm")
@RestController
public class CommonCodeController {

	@Autowired
	public CommonCodeService commonCodeService;

		// 공통코드 목록 조회
	 	@RequestMapping(value = { "/common-codes" }, method = RequestMethod.GET)
	 	public BaseResponse getCommonCodeList(HttpServletRequest request,
	 			@Valid @ModelAttribute CommonCodeRequest commonCodeRequest)
	 			throws java.lang.Exception {
	 //		System.out.println("codeType : " + codeType);
	 		System.out.println("commonCodeRequest : " + commonCodeRequest);
	 		return commonCodeService.selectCommonCodelist(commonCodeRequest);
	 	}
	 
	 	// 권한코드 목록 조회
	 	@RequestMapping(value = { "/auth-codes" }, method = RequestMethod.GET)
		public BaseResponse getAuthCodeList(HttpServletRequest request)
	 			throws java.lang.Exception {
	 		return commonCodeService.selectAuthCodelist();
	 	}

	 	// 통화코드 목록 조회
	 	@RequestMapping(value = { "/currency-codes" }, method = RequestMethod.GET)
		public BaseResponse getCommonCodeList(HttpServletRequest request)
	 			throws java.lang.Exception {
	 		return commonCodeService.selectCurrencyCodelist();
	 	}

	 	// 국가코드 목록 조회
	 	@RequestMapping(value = { "/country-codes" }, method = RequestMethod.GET)
		public BaseResponse getCcountryCodeList(HttpServletRequest request)
	 			throws java.lang.Exception {
	 		return commonCodeService.selectCountryCodelist();
	 	}
	 	
	 	// 지역 언어코드 목록 조회
	 	@RequestMapping(value = { "/locale-codes" }, method = RequestMethod.GET)
		public BaseResponse getLocaleCodeList(HttpServletRequest request)
	 			throws java.lang.Exception {
	 		return commonCodeService.selectLocaleCodelist();
	 	}
}
