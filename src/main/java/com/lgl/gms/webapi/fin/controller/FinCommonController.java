package com.lgl.gms.webapi.fin.controller;

import java.util.HashMap;
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

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.FinCommonRequest;
import com.lgl.gms.webapi.fin.service.FinCommonService;

/**
 * 재무공통 컨트롤러
 * @author jokim
 * @date 2022.04.13
 */
@CrossOrigin
@RequestMapping("/fin")
@RestController
public class FinCommonController {
	
	@Autowired
	public FinCommonService finService;
	
	/**
	 * 양식정보리스트(콤보용)
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/frm-infos" }, method = RequestMethod.GET)
	public BaseResponse selectFrmInfoList(HttpServletRequest request, @ModelAttribute FinCommonRequest param) {
		
		return finService.selectFrmInfoList(param);
		
	}
	
	/**
	 * 엑셀시트명 리스트
	 * @param request
	 * @param file
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/frm-infos/xls/sheets-name" }, method = RequestMethod.POST)
	public BaseResponse getExcelSheetNames(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file, FinCommonRequest param) {
		
		return finService.getExcelSheetNames(file, param);
	}
	
	/**
	 * 법인양식관리 리스트(법인목록, 법인양식 목록)
	 * @param request
	 * @param param
	 * @param pos
	 * @return
	 */
	@RequestMapping(value = { "/frm-infos/bo-mgmt/{pos}" }, method = RequestMethod.GET)
	public BaseResponse selectFinBoMgmtList(HttpServletRequest request, @ModelAttribute FinCommonRequest param, @PathVariable String pos) {
		
		
		return finService.selectFinBoMgmtList(param, pos);
		
	}
	
	/**
	 * 법인양식 관리 저장
	 * @param request
	 * @param frmBoInfo
	 * @return
	 */
	@RequestMapping(value = { "/frm-infos" }, method = RequestMethod.POST)
	public BaseResponse saveBsSheets(HttpServletRequest request, @RequestBody HashMap<String, Object> frmBoInfo) {
		
		return finService.saveFinBoMgmtList(frmBoInfo);

	}	

}
