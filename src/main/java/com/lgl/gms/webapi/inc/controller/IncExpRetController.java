package com.lgl.gms.webapi.inc.controller;

import java.util.List;

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
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpRetModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;
import com.lgl.gms.webapi.inc.service.IncExpRetService;
import com.lgl.gms.webapi.inc.service.IncRetService;

/**
 * (실적)영업외손익/판관비상세 등록 컨트롤러
 * @author jokim
 * @date 2022.03.14
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
public class IncExpRetController {

	@Autowired
	public IncRetService incRetService;
	
	@Autowired
	public IncExpRetService incExpRetService;

	/**
	 * (실적)영업외손익/판관비상세 목록 조회
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/exp-perfs" }, method = RequestMethod.GET)
	public BaseResponse selectIncSalesPlanList(HttpServletRequest request, @ModelAttribute BoIncRetRequest param) {
		
		return incExpRetService.selectIncExpRetList(param);
		
	}
	
	/**
	 * (실적)영업외손익/판관비상세 엑셀업로드
	 * @param request
	 * @param file
	 * @param body
	 * @param boId
	 * @param incYy
	 * @return
	 */
	@RequestMapping(value = { "/exp-perfs/xls/{sheetNum}/{boId}/{incYymm}/{defCl}/{typCd}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
									, BoIncRetRequest body, @PathVariable Integer sheetNum, @PathVariable Integer boId, @PathVariable String incYymm, @PathVariable String defCl, @PathVariable String typCd) {
		body.setSheetNum(sheetNum);
		body.setBoId(boId);
		body.setIncYymm(incYymm);
		body.setDefCl(defCl);
		body.setTypCd(typCd);
		
		return incExpRetService.excelUpload(file, body);
	}
	
	/**
	 * (실적)영업외손익/판관비상세 저장
	 * @param request
	 * @param incPlnSals
	 * @return
	 */
	@RequestMapping(value = { "/exp-perfs/{typCdNm}" }, method = RequestMethod.POST)
	public BaseResponse saveIncExpPlan(HttpServletRequest request, @RequestBody List<BoIncExpRetModel> incExpRets, @PathVariable String typCdNm) {
		BoIncRetRequest param = new BoIncRetRequest();
		param.setTypCdNm(typCdNm);
		return incExpRetService.saveIncExpRet(incExpRets, param);
	}
	
	/**
	 * (실적)영업외손익/판관비상세 마감
	 * @param request
	 * @param paramModel
	 * @return
	 */
	@RequestMapping(value = { "/exp-perfs/cls" }, method = RequestMethod.POST)
	public BaseResponse procIncExpRetCls(HttpServletRequest request, @RequestBody BoIncRetModel paramModel) {

		return incExpRetService.procIncExpRetCls(paramModel, null);
		
	}
	
	/**
	 * (실적)영업외손익/판관비상세 삭제
	 * @param request
	 * @param boId
	 * @param incYymm
	 * @param defCl
	 * @param seq
	 * @return
	 */
	@RequestMapping(value = { "/exp-perfs/{boId}/{incYymm}/{defCl}/{seq}/{typCdNm}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncExpRet(HttpServletRequest request, @PathVariable Integer boId, @PathVariable String incYymm
			, @PathVariable String defCl, @PathVariable Integer seq, @PathVariable String typCdNm) {
		
		BoIncRetRequest param = new BoIncRetRequest();
		param.setBoId(boId);
		param.setIncYymm(incYymm);
		param.setDefCl(defCl);
		param.setSeq(seq);
		param.setTypCdNm(typCdNm);
		return incExpRetService.deleteIncExpRet(param);
		
	}	
	
	/**
	 * 해당 (실적)판관비에 대한 매출조회
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/exp-perfs/sales" }, method = RequestMethod.GET)
	public BaseResponse selectIncSalPlnDelByExpList(HttpServletRequest request, @ModelAttribute BoIncRetRequest param) {
		
		return incExpRetService.selectIncSalRetByExpList(param);
		
	}
	

}
