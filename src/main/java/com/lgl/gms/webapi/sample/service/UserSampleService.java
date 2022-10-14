package com.lgl.gms.webapi.sample.service;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.dto.request.UserSampleRequest;

public interface UserSampleService {

	BaseResponse add(UserSampleRequest body);

	BaseResponse delete(UserSampleRequest body);

	BaseResponse modify(UserSampleRequest body);

	BaseResponse modifyWithTransaction(UserSampleRequest body);

	BaseResponse get(UserSampleRequest body);

	BaseResponse getUserById(String id);

	BaseResponse list(UserSampleRequest body);

	BaseResponse upload(MultipartFile file, FileRequest body);

	/**
	 * 파일 저장 후 엑셀 파싱
	 * 파싱한 엑셀 DB에 저장
	 * 
	 * @param file
	 * @param body
	 * @return
	 */
	BaseResponse uploadExcel(MultipartFile file, FileRequest body);

}
