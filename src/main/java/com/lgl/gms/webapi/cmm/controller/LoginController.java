package com.lgl.gms.webapi.cmm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.cmm.dto.request.LoginRequest;
import com.lgl.gms.webapi.cmm.service.LoginService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;

/**
 * 로그인/로그아웃 처리 Controller
 */
@CrossOrigin
@RequestMapping("/cmm")
@RestController
public class LoginController {
	@Autowired
	public LoginService loginService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public BaseResponse loginUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginRequest body) throws java.lang.Exception {
//			System.out.println("===>>> LoginController > loginUser() exec.. ");
		return loginService.loginUser(body, request, response);
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
	public BaseResponse logoutUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginRequest body) throws java.lang.Exception {
		return loginService.logout(request.getHeader("Authorization"), response);
	}

	@RequestMapping(value = { "/ref-token" }, method = RequestMethod.POST)
	public BaseResponse refreshToken(HttpServletRequest request, HttpServletResponse response) 
			throws java.lang.Exception {
		return loginService.refreshToken(request, response);
	}

//	@RequestMapping(value = { "/pwd" }, method = RequestMethod.POST)
//	public BaseResponse changeUserPwd(HttpServletRequest request, HttpServletResponse response,
//			@RequestBody PwdChangeRequest body) throws java.lang.Exception {
////			System.out.println("===>>> LoginController > changeUserPwd() exec.. ");
//		return loginService.changeUserPwd(body, request, response);
//	}
//
//	@RequestMapping(value = { "/pwd-delay" }, method = RequestMethod.POST)
//	public BaseResponse delayUserPwd(HttpServletRequest request, HttpServletResponse response,
//			@RequestBody PwdChangeRequest body) throws java.lang.Exception {
//			System.out.println("===>>> LoginController > delayUserPwd() exec.. ");
//		return loginService.delayUserPwd(body, request, response);
//	}

}
