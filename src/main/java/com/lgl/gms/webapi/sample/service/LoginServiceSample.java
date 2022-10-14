package com.lgl.gms.webapi.sample.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.LoginRequestSample;

public interface LoginServiceSample {

	BaseResponse logout(String auth, HttpServletResponse response);

	BaseResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

	BaseResponse loginUser(LoginRequestSample req, HttpServletRequest request, HttpServletResponse response);

}
