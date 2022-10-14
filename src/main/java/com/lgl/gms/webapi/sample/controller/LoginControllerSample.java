package com.lgl.gms.webapi.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.LoginRequestSample;
import com.lgl.gms.webapi.sample.service.LoginServiceSample;

@CrossOrigin
@RestController
public class LoginControllerSample {
	@Autowired
	public LoginServiceSample loginServiceSample;

	@RequestMapping(value = { "/v1/user/login" }, method = RequestMethod.POST)
	public BaseResponse loginUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginRequestSample body) throws java.lang.Exception {
		return loginServiceSample.loginUser(body, request, response);
	}

	@Auth(role = { Role.USER, Role.ADMIN })
	@RequestMapping(value = { "/v1/user/logout" }, method = RequestMethod.POST)
	public BaseResponse logoutUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginRequestSample body) throws java.lang.Exception {
		return loginServiceSample.logout(request.getHeader("Authorization"), response);
	}

	@RequestMapping(value = { "/v1/user/refreshToken" }, method = RequestMethod.POST)
	public BaseResponse refreshToken(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginRequestSample body) throws java.lang.Exception {
		return loginServiceSample.refreshToken(request, response);

	}
}
