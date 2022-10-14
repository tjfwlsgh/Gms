package com.lgl.gms.webapi.inf.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inf.dto.request.InfEmployeeMgmtRequest;
import com.lgl.gms.webapi.inf.persistence.model.InfraBoEmpModel;
import com.lgl.gms.webapi.inf.service.InfEmployeeMgmtService;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : InfEmployeeMgmtController.java
 * @Date        : 22.03.14
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 인원현황 관리 Controller
 */
@Slf4j 
@RequestMapping("/inf")
@CrossOrigin
@RestController
public class InfEmployeeMgmtController {
	
	@Autowired
	public InfEmployeeMgmtService empService;
	
	/**
	 * 법인 인원 현황 관리 조회 (검색) (직원 현황)
	 * 
	 * @param request
	 * @param body
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/employees"}, method = RequestMethod.GET)
	public BaseResponse getEmployeeMgmt(HttpServletRequest request, @ModelAttribute InfEmployeeMgmtRequest paramDto) {
		
		return empService.getEmployeeMgmtList(paramDto);
		
	}
	
	/**
	 * 법인 인원 현황 관리 추가 (직원 현황)
	 * 
	 * @param request
	 * @param body
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/employees"}, method = RequestMethod.POST)
	public BaseResponse addEmployeeMgmt(HttpServletRequest request, @RequestBody InfEmployeeMgmtRequest paramDto) {
		
		return empService.addEmployeeMgmt(paramDto);
		
	}

	
	/**
	 * 법인 인원 현황 관리 삭제 (직원 현황 / 주재원)
	 * 
	 * @param request
	 * @param body
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/employees/{id}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteEmployeeMgmt(HttpServletRequest request, @PathVariable Integer id) {
		
		InfEmployeeMgmtRequest paramDto = new InfEmployeeMgmtRequest();
		paramDto.setBoEmpId(id);
		return empService.deleteEmployeeMgmt(paramDto);
		
	}
	
	
	/**
	 * 법인 인원 현황 관리 수정 (직원 현황)
	 * @param request
	 * @return
	 */
	@Auth(role = { Role.USER })
	@RequestMapping(value = {"/employees/{id}"}, method = RequestMethod.PUT)
	public BaseResponse modifyEmployeeMgmt(HttpServletRequest request, @PathVariable Integer id, @RequestBody InfraBoEmpModel paramDto) {
		paramDto.setBoEmpId(id);
		return empService.modifyEmployeeMgmt(paramDto);
	}
	
	/**
	 * 법인 인원 현황 관리 조회 (검색) (주재원)
	 * 
	 * @param request
	 * @param body
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/employees/residents"}, method = RequestMethod.GET)
	public BaseResponse getRegidentMgmt(HttpServletRequest request, @ModelAttribute InfEmployeeMgmtRequest paramDto) {
		
		return empService.getRegidentMgmtList(paramDto);
		
	}
	
	/**
	 * 엑셀업로드
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = { "/employees/xls/{sheetNum}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request
								  , @RequestPart(value = "file") MultipartFile file
								  , @PathVariable Integer sheetNum) {
		
		InfEmployeeMgmtRequest body = new InfEmployeeMgmtRequest();
		body.setSheetNum(sheetNum);
		
		return empService.excelUpload(file, body);
	}
	
	/**
	 * 인원현황관리 엑셀 저장
	 * @param request
	 * @param IUDObj
	 * @return
	 */
	@RequestMapping(value= {"/employees/xls-saved"}, method = RequestMethod.POST)
	public BaseResponse doSaveArExcel(HttpServletRequest request, @RequestBody Map<String, Object> IUDObj) {
		
//		log.debug("doSaveArExcel > empList ===> {}", IUDObj);
		
		return empService.doSaveEmployeeExcel(IUDObj);
		
	}
	

}
