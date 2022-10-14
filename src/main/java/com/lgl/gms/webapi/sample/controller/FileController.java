package com.lgl.gms.webapi.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.FileRequest;
import com.lgl.gms.webapi.sample.service.FileService;

@CrossOrigin
@RestController
public class FileController {
	@Autowired
	public FileService fileService;

	@RequestMapping(value = { "/v1/files/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<?> downloadFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String id)
			throws java.lang.Exception {
		return fileService.downloadFile(id, response);
	}

	@RequestMapping(value = { "/v1/files/upload" }, method = RequestMethod.POST)
	public BaseResponse upload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file,
			@RequestPart(value = "body", required = false) FileRequest body) throws java.lang.Exception {
		return fileService.upload(file, body);
	}
}
