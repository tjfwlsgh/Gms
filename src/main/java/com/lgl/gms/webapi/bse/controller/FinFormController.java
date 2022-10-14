package com.lgl.gms.webapi.bse.controller;

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

import com.lgl.gms.webapi.bse.dto.request.FinFormRequest;
import com.lgl.gms.webapi.bse.persistence.model.FrmInfoModel;
import com.lgl.gms.webapi.bse.service.FinFormService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : FinFormController.java
 * @Date        : 2022.05.09
 * @Author      : jinok
 * @Description : Controller
 * @History     : 재무양식관리 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class FinFormController {
	
	@Autowired
	public FinFormService finFormService;
	
	/**
	 * 양식조회 (재무)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping(value= {"/fin-frm-info"}, method = RequestMethod.GET)
	public BaseResponse getFinFrmList(HttpServletRequest request, @ModelAttribute FinFormRequest paramDto) {
		
		return finFormService.getFrmInfoList(paramDto);
		
	}
	
	/**
	 * 양식저장(추가) (재무)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping(value= {"/fin-frm-info"}, method = RequestMethod.POST)
	public BaseResponse insertFinFrmList(HttpServletRequest request, @RequestBody FrmInfoModel paramModel) {
		
		return finFormService.insertFrmInfo(paramModel);
		
	}
	
	/**
	 * 양식저장(수정) (재무)
	 * 
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping(value= {"/fin-frm-info"}, method = RequestMethod.PUT)
	public BaseResponse updateFinFrmList(HttpServletRequest request, @RequestBody FrmInfoModel paramModel) {
		
		return finFormService.updateFrmInfo(paramModel);
		
	}
	
	/**
	 * 재무양식상세 조회
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping(value= {"/fin-frm-info-det"}, method = RequestMethod.GET)
	public BaseResponse getFinFrmDetList(HttpServletRequest request, @ModelAttribute FinFormRequest paramDto) {
		
		return finFormService.getFrmInfoDetList(paramDto);
		
	}
	
	/**
	 * 양식상세 upload
	 * @param request
	 * @param file
	 * @param body
	 * @param sheetNum
	 * @param frmId
	 * @return
	 */
	@RequestMapping(value = { "/fin-frm-info-det/xls/{includeFrmYn}/{sheetNum}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file, 
				FinFormRequest body, @PathVariable String includeFrmYn, @PathVariable Integer sheetNum) {
		
		body.setSheetNum(sheetNum);
		body.setIncludeFrmYn(includeFrmYn);
		
		return finFormService.excelUpload(file, body);
	}
	
	/**
	 * 재무 계정관리 조회
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@RequestMapping(value= {"/acc-mgmt"}, method = RequestMethod.GET)
	public BaseResponse getAccMgmtList(HttpServletRequest request, @ModelAttribute FinFormRequest paramDto) {
		
		return finFormService.getAccMgmtList(paramDto);
		
	}
	
	/**
	 * 재무양식 상세 목록 조회
	 * @param request
	 * @param frmDetMap
	 * @return
	 */
	@RequestMapping(value = { "/fin-frm-info-det" }, method = RequestMethod.POST)
	public BaseResponse saveExchgRate(HttpServletRequest request, @RequestBody Map<String, Object> frmDetMap) {
		
		return finFormService.saveFrmInfoDet(frmDetMap);
	}
	

}
