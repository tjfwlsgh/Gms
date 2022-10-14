package com.lgl.gms.webapi.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.PgmRequest;
import com.lgl.gms.webapi.sys.service.PgmService;

/**
 *  프로그램관리 Controller
 */
@CrossOrigin
@RequestMapping("/sys")
@RestController
public class PgmController {

	// MyBatis 사용
	@Autowired
	public PgmService pgmService;

	// POST/PUT/GET/DELETE method 방식

	// 프로그램/메뉴 리스트 조회(n건)
	@RequestMapping(value = { "/pgms" }, method = RequestMethod.GET)
	public BaseResponse getPgms(HttpServletRequest request,
					@RequestParam(defaultValue = "") String pgmTyp,
					@RequestParam(defaultValue = "") String topMenuCd,
					@RequestParam(defaultValue = "") String pgmId,
					@RequestParam(defaultValue = "") String pgmNm
			) throws java.lang.Exception {

		PgmRequest body = new PgmRequest();
		body.setPgmTyp(pgmTyp);
		body.setTopMenuCd(topMenuCd);
		body.setPgmId(pgmId);
		body.setPgmNm(pgmNm);
		
		return pgmService.getPgmList(body);
	}
	
	// 프로그램 조회(1건)
	@RequestMapping(value = { "/pgms/{pgmId}/check" }, method = RequestMethod.GET)
	public BaseResponse getPgmOne(HttpServletRequest request,
					@PathVariable String pgmId
			) throws java.lang.Exception {

		PgmRequest body = new PgmRequest();
		
		body.setPgmId(pgmId);
		
		return pgmService.getPgmOne(body);
	}
	
	// 대메뉴 리스트 조회
	@RequestMapping(value = { "/menu-codes" }, method = RequestMethod.GET)
	public BaseResponse getMenuCodes(HttpServletRequest request,
					@RequestParam(defaultValue = "") String pgmTyp,
					@RequestParam(defaultValue = "") String delYn,
					@RequestParam(defaultValue = "") String pgmId
			) throws java.lang.Exception {

		PgmRequest body = new PgmRequest();
		body.setPgmTyp(pgmTyp);
		body.setDelYn(delYn);
		body.setPgmId(pgmId);	// 특정 대메뉴를 선택하는 경우 사용됨(권한관리)
		
		return pgmService.getMenuCodeList(body);
	}

	// 프로그램 추가
	@RequestMapping(value = { "/pgms" }, method = RequestMethod.POST)
	public BaseResponse addPgm(HttpServletRequest request, 
				@RequestBody PgmRequest body) 
		throws java.lang.Exception {
		
		return pgmService.addPgm(body);
	}
	
	// 프로그램 수정
	@RequestMapping(value = { "/pgms/{pgmId}" }, method = RequestMethod.PUT)
	public BaseResponse modifyPgm(HttpServletRequest request, 
				@PathVariable String pgmId, @RequestBody PgmRequest body)	
					throws java.lang.Exception {
		
		// 이미 있지만 path variable로 받은 tccId를 다시 명확하게 설정
		body.setPgmId(pgmId);
		
		return pgmService.modifyPgm(body);
	}

	// 프로그램 삭제
	@RequestMapping(value = { "/pgms/{pgmId}" }, method = RequestMethod.DELETE)
	public BaseResponse deletePgm(HttpServletRequest request, 
				@PathVariable String pgmId)
			throws java.lang.Exception {
		
		PgmRequest body = new PgmRequest();
		body.setPgmId(pgmId);
		
		return pgmService.deletePgm(body);
	}

}
