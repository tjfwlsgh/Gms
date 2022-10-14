package com.lgl.gms.webapi.bse.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.bse.dto.request.S3ObjInfoRequest;
import com.lgl.gms.webapi.bse.persistence.model.S3ObjInfoModel;
import com.lgl.gms.webapi.bse.service.S3ObjInfoService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : FileMgmtController.java
 * @Date        : 2022.06.21
 * @Author      : jokim
 * @Description : Controller
 * @History     : 파일관리 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class S3ObjectInfoController {
	
	@Autowired
	public S3ObjInfoService s3ObjInfoService;
	
	/**
	 * 파일관리 목록 조회
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping(value= {"/s3-obj-infos"}, method = RequestMethod.GET)
	public BaseResponse getIncInfoList(HttpServletRequest request, @ModelAttribute S3ObjInfoRequest paramDto) {
		
		return s3ObjInfoService.selectS3ObjInfoList(paramDto);
		
	}
	
	/**
	 * 파일정보 저장(추가/수정)
	 * @param request
	 * @param objModel
	 * @return
	 */
	@RequestMapping(value = {"/s3-obj-infos"}, method = RequestMethod.PUT)
	public BaseResponse updateIncInfo(HttpServletRequest request, @RequestBody S3ObjInfoModel objModel) {
		
		return s3ObjInfoService.saveS3ObjInfo(objModel);
	}
	
	/**
	 * 파일정보 삭제
	 * @param request
	 * @param objId
	 * @return
	 */
	@RequestMapping(value = {"/s3-obj-infos/{objId}"}, method = RequestMethod.DELETE)
	public BaseResponse updateIncInfo(HttpServletRequest request, @PathVariable Integer objId) {
		
		S3ObjInfoRequest param = new S3ObjInfoRequest();
		param.setObjId(objId);
		return s3ObjInfoService.deleteS3ObjInfo(param);
		
	}

}
