package com.lgl.gms.webapi.common.service;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;


public interface ExcelUploadService {
	
	public BaseResponse excelUpload(MultipartFile file, Object body);
	

}
