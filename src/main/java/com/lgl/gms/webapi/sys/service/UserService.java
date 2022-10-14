package com.lgl.gms.webapi.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;
import com.lgl.gms.webapi.sys.dto.request.UserRequest;

public interface UserService {

	BaseResponse getUserList(UserRequest body);

	BaseResponse getUserOne(String id);

	BaseResponse addUser(UserRequest body);
	
	BaseResponse checkUserId(String id);
	
	BaseResponse modifyUser(UserRequest body);

	BaseResponse deleteUser(UserRequest body);

	// 비밀번호 변경 관련 추가
	BaseResponse changeUserPwd(PwdChangeRequest body, HttpServletRequest request, HttpServletResponse response);

	BaseResponse delayUserPwd(PwdChangeRequest body, HttpServletRequest request, HttpServletResponse response);

//	BaseResponse getUser(UserRequest body);
//	
//	BaseResponse modifyWithTransaction(UserRequest body);
//
//	BaseResponse upload(MultipartFile file, FileRequest body);
//	/**
//	 * 파일 저장 후 엑셀 파싱
//	 * 파싱한 엑셀 DB에 저장
//	 */
//	BaseResponse uploadExcel(MultipartFile file, FileRequest body);

}
