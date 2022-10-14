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

import com.lgl.gms.config.Auth;
import com.lgl.gms.config.Auth.Role;
import com.lgl.gms.webapi.bse.dto.request.BseExchgRateInqRequest;
import com.lgl.gms.webapi.bse.dto.request.BsePlnExchgRateInqRequest;
import com.lgl.gms.webapi.bse.service.BseExchgRateInqService;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseExchgRateInqController.java
 * @Date        : 22.03.30
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 환율 정보 Controller
 */
@Slf4j 
@RequestMapping("/bse")
@CrossOrigin
@RestController
public class BseExchgRateInqController {
	
	@Autowired
	public BseExchgRateInqService beriService;
	
	
	/**
	 * 환율 정보 조회
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/exchg-rate-inqs"}, method = RequestMethod.GET)
	public BaseResponse getExchgRateInqList(HttpServletRequest request, @ModelAttribute BseExchgRateInqRequest paramDto) {
		
		return beriService.getExchgRateInqList(paramDto);
		
	}
	/**
	 * 계획 환율 정보 조회
	 *  
	 * @param request
	 * @param paramDto
	 * @return
	 */
	@Auth(role = {Role.USER})
	@RequestMapping(value= {"/pln-exchg-rate-inqs"}, method = RequestMethod.GET)
	public BaseResponse getPlnExchgRateInqList(HttpServletRequest request, @ModelAttribute BsePlnExchgRateInqRequest paramDto) {
		
		return beriService.getPlnExchgRateInqList(paramDto);
		
	}
	
	/**
	 * 엑셀업로드
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = { "/exchg-rate-inqs/xls/{yymm}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
									, BseExchgRateInqRequest body, @PathVariable String yymm) {
		body.setYymm(yymm);
		return beriService.excelUpload(file, body);
	}
	
	/**
	 * 환율정보 저장
	 * @param request
	 * @param incPlnSals
	 * @return
	 */
	@RequestMapping(value = { "/exchg-rate-inqs" }, method = RequestMethod.POST)
	public BaseResponse saveExchgRate(HttpServletRequest request,
			@RequestBody Map<String, Object> IudObj) {
		
		return beriService.saveExchgRate(IudObj);
	}
	
}
