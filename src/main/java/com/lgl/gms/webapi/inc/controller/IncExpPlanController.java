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
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpPlanModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.service.IncExpPlanService;
import com.lgl.gms.webapi.inc.service.IncPlanService;

/**
 * (계획)영업외손익/판관비 등록 컨트롤러
 * @author jokim
 * @date 2022.03.5
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
public class IncExpPlanController {

	@Autowired
	public IncPlanService incPlanService;
	
	@Autowired
	public IncExpPlanService incExpPlanService;

	/**
	 * (계획)영업외손익/판관비 목록 조회
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/exp-plans" }, method = RequestMethod.GET)
	public BaseResponse selectIncSalesPlanList(HttpServletRequest request, @ModelAttribute BoIncPlanRequest param) {
		
		return incExpPlanService.selectIncExpPlanList(param);
		
	}
	
	/**
	 * 엑셀업로드
	 * @param request
	 * @param file
	 * @param body
	 * @param boId
	 * @param incYy
	 * @return
	 */
	@RequestMapping(value = { "/exp-plans/xls/{boId}/{incYy}/{typCd}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
									, BoIncPlanRequest body, @PathVariable Integer boId, @PathVariable String incYy, @PathVariable String typCd) {
		body.setBoId(boId);
		body.setIncYy(incYy);
		body.setTypCd(typCd);
		return incExpPlanService.excelUpload(file, body);
	}
	
	/**
	 * (계획)영업외손익/판관비 저장
	 * @param request
	 * @param incPlnSals
	 * @return
	 */
	@RequestMapping(value = { "/exp-plans/{typCdNm}" }, method = RequestMethod.POST)
	public BaseResponse saveIncExpPlan(HttpServletRequest request, @RequestBody List<BoIncExpPlanModel> incExpPlans, @PathVariable String typCdNm) {
		BoIncPlanRequest param = new BoIncPlanRequest();
		param.setTypCdNm(typCdNm);
		return incExpPlanService.saveIncExpPlan(incExpPlans, param);
	}	
	
	/**
	 * (계획)영업외손익/판관비 마감
	 * @param request
	 * @param paramModel
	 * @return
	 */
	@RequestMapping(value = { "/exp-plans/cls" }, method = RequestMethod.POST)
	public BaseResponse procIncExpPlnCls(HttpServletRequest request, @RequestBody BoIncPlnModel paramModel) {

		return incExpPlanService.procIncExpPlnCls(paramModel, null);
		
	}
	
	/**
	 * (계획)영업외손익/판관비 삭제
	 * @param request
	 * @param boId
	 * @param incYy
	 * @param incMon
	 * @param seq
	 * @return
	 */
	@RequestMapping(value = { "/exp-plans/{boId}/{incYy}/{incMon}/{seq}/{typCdNm}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncSalPln(HttpServletRequest request, @PathVariable Integer boId, @PathVariable String incYy
					, @PathVariable String incMon, @PathVariable Integer seq, @PathVariable String typCdNm) {
		
		BoIncPlanRequest param = new BoIncPlanRequest();
		param.setBoId(boId);
		param.setIncYy(incYy);
		param.setIncMon(incMon);
		param.setSeq(seq);
		param.setTypCdNm(typCdNm);
		return incExpPlanService.deleteIncExpPlan(param);
		
	}	
	
	/**
	 * 해당 (계획)영업외손익/판관비에 대한 매출조회
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/exp-plans/sales" }, method = RequestMethod.GET)
	public BaseResponse selectIncSalPlnDelByExpList(HttpServletRequest request, @ModelAttribute BoIncPlanRequest param) {
		
		return incExpPlanService.selectIncSalPlnDelByExpList(param);
		
	}

}
