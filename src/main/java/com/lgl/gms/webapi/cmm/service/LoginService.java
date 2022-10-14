package com.lgl.gms.webapi.cmm.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgl.gms.webapi.cmm.dto.request.LoginRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;


public interface LoginService {

	BaseResponse loginUser(LoginRequest body, HttpServletRequest request, HttpServletResponse response);

	BaseResponse logout(String auth, HttpServletResponse response);

	BaseResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

//	BaseResponse changeUserPwd(PwdChangeRequest body, HttpServletRequest request, HttpServletResponse response);
//
//	BaseResponse delayUserPwd(PwdChangeRequest body, HttpServletRequest request, HttpServletResponse response);

}
