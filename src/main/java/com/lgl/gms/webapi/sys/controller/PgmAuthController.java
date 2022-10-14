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
import com.lgl.gms.webapi.sys.dto.request.AuthRequest;
import com.lgl.gms.webapi.sys.dto.request.PgmAuthRequest;
import com.lgl.gms.webapi.sys.service.PgmAuthService;

/**
 * 권한관리 Controller
 */
@CrossOrigin
@RequestMapping("/sys")
@RestController
public class PgmAuthController {

	// MyBatis 사용
	@Autowired
	public PgmAuthService pgmAuthService;

	// POST/PUT/GET/DELETE method 방식

	// 권한 리스트 조회(n건)
	@RequestMapping(value = { "/auths" }, method = RequestMethod.GET)
	public BaseResponse getAuths(HttpServletRequest request,
					@RequestParam(defaultValue = "") String authCd
			) throws java.lang.Exception {

		AuthRequest body = new AuthRequest();
		body.setAuthCd(authCd);

		return pgmAuthService.getAuthList(body);
	}
	
	// 권한 조회(1건)
	@RequestMapping(value = { "/auths/{authCd}/check" }, method = RequestMethod.GET)
	public BaseResponse getAuthOne(HttpServletRequest request,
					@PathVariable String authCd
			) throws java.lang.Exception {

		AuthRequest body = new AuthRequest();
		
		body.setAuthCd(authCd);
		
		return pgmAuthService.getAuthOne(body);
	}
	
	// 권한 추가
	@RequestMapping(value = { "/auths" }, method = RequestMethod.POST)
	public BaseResponse addAuth(HttpServletRequest request, 
				@RequestBody AuthRequest body) 
		throws java.lang.Exception {
		
		return pgmAuthService.addAuth(body);
	}
	
	// 권한 수정
	@RequestMapping(value = { "/auths/{authCd}" }, method = RequestMethod.PUT)
	public BaseResponse modifyAuth(HttpServletRequest request, 
				@PathVariable String authCd, 
				@RequestBody AuthRequest body)	
					throws java.lang.Exception {
		
		body.setAuthCd(authCd);
		
		return pgmAuthService.modifyAuth(body);
	}

	// 권한 삭제
	@RequestMapping(value = { "/auths/{authCd}" }, method = RequestMethod.DELETE)
	public BaseResponse deleteAuth(HttpServletRequest request, 
				@PathVariable String authCd)
			throws java.lang.Exception {
		
		AuthRequest body = new AuthRequest();
		body.setAuthCd(authCd);
		
		return pgmAuthService.deleteAuth(body);
	}

	// 프로그램-권한 리스트 조회(n건)
	@RequestMapping(value = { "/pgm-auths" }, method = RequestMethod.GET)
	public BaseResponse getPgmAuths(HttpServletRequest request,
					@RequestParam(defaultValue = "") String authCd,
					@RequestParam(defaultValue = "") String topMenuCd
			) throws java.lang.Exception {

		PgmAuthRequest body = new PgmAuthRequest();
		body.setAuthCd(authCd);
		body.setTopMenuCd(topMenuCd);

		return pgmAuthService.getPgmAuthList(body);
	}

	// 프로그램-권한 추가
	@RequestMapping(value = { "/pgm-auths/{authCd}/{topMenuCd}"}, method = RequestMethod.POST)
	public BaseResponse addPgmAuth(HttpServletRequest request, 
			@PathVariable String authCd,
			@PathVariable String topMenuCd)
		throws java.lang.Exception {
		
		PgmAuthRequest body = new PgmAuthRequest();
		body.setAuthCd(authCd);
		body.setTopMenuCd(topMenuCd);
		
		return pgmAuthService.addPgmAuth(body);
	}
	
	// 프로그램-권한 수정
	// pk키가 3개라서 url에는 대표키 하나만 표시
	@RequestMapping(value = { "/pgm-auths/{pgmId}" }, method = RequestMethod.PUT)
	public BaseResponse modifyPgmAuth(HttpServletRequest request, 
				@PathVariable String pgmId, 
				@RequestBody PgmAuthRequest body)	
					throws java.lang.Exception {
		
		body.setPgmId(pgmId);
		System.out.println("===>>> PgmAuthController > modifyPgmAuth() > body : "+ body);

		return pgmAuthService.modifyPgmAuth(body);
	}

	// 프로그램-권한 삭제
	@RequestMapping(value = { "/pgm-auths/{authCd}/{topMenuCd}" }, method = RequestMethod.DELETE)
	public BaseResponse deletePgmAuth(HttpServletRequest request, 
				@PathVariable String authCd,
				@PathVariable String topMenuCd)
			throws java.lang.Exception {
		
		PgmAuthRequest body = new PgmAuthRequest();
		body.setAuthCd(authCd);
		body.setTopMenuCd(topMenuCd);
		
		
		return pgmAuthService.deletePgmAuth(body);
	}

}
