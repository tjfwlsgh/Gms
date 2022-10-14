package com.lgl.gms.webapi.sample.service;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.persistence.model.FileModel;

public interface FileService {

	BaseResponse upload(MultipartFile file, FileRequest body);

	FileModel saveFile(MultipartFile file);

	Workbook getExcelWorkbook(MultipartFile file);

	ResponseEntity<?> downloadFile(String id, HttpServletResponse request);

}
