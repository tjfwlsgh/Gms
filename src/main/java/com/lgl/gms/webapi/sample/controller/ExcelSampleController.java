package com.lgl.gms.webapi.sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.service.UserSampleService;

@CrossOrigin
@RestController
public class ExcelSampleController {

	@Autowired
	@Qualifier("excelUploadSampleServiceImpl")
	public ExcelUploadService excelUploadService;
	
	@Autowired
	public UserSampleService userService;
	
	/**
	 * 엑셀업로드
	 * @param request
	 * @param file
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = { "/v1/sample/excelUpload" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
						, @RequestPart(value = "body", required = false) FileRequest body) {
		return excelUploadService.excelUpload(file, body);
	}
	

}
