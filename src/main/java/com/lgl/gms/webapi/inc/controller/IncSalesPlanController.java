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
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnSalModel;
import com.lgl.gms.webapi.inc.service.IncPlanService;
import com.lgl.gms.webapi.inc.service.IncSalesPlanService;

/**
 * (계획)영업손익등록 컨트롤러
 * @author jokim
 * @date 2022.02.01
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
public class IncSalesPlanController {

	@Autowired
	public IncPlanService incPlanService;
	
	@Autowired
	public IncSalesPlanService incSalesPlanService;

	/**
	 * (계획)영업손익등록 목록 조회
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/sales-plans" }, method = RequestMethod.GET)
	public BaseResponse selectIncSalesPlanList(HttpServletRequest request, @ModelAttribute BoIncPlanRequest param) {
		
		return incSalesPlanService.selectIncSalesPlanList(param);
		
	}
	
	/**
	 * (계획)영업손익등록 저장
	 * @param request
	 * @param incPlnSals
	 * @return
	 */
	@RequestMapping(value = { "/sales-plans" }, method = RequestMethod.POST)
	public BaseResponse saveIncPlnSal(HttpServletRequest request, @RequestBody List<BoIncPlnSalModel> incPlnSals) {

		return incSalesPlanService.saveIncSalesPlan(incPlnSals, null);
		
	}	
	
	
	/**
	 * (계획)영업손익등록 엑셀업로드
	 * @param request
	 * @param file
	 * @return BaseResponse
	 */
	@RequestMapping(value = { "/sales-plans/xls/{boId}/{incYy}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
									, BoIncPlanRequest body, @PathVariable Integer boId, @PathVariable String incYy) {
		body.setBoId(boId);
		body.setIncYy(incYy);
		return incSalesPlanService.excelUpload(file, body);
	}
	
	/**
	 * (계획)영업손익등록 마감
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/sales-plans/cls" }, method = RequestMethod.POST)
	public BaseResponse procIncSalPlnCls(HttpServletRequest request, @RequestBody BoIncPlnModel paramModel) {

		return incSalesPlanService.procIncSalesPlanCls(paramModel, null);
		
	}	
	
	/**
	 * (계획)영업손익등록 삭제
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/sales-plans/{boId}/{incYy}/{incMon}/{seq}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncSalPln(HttpServletRequest request, @PathVariable Integer boId, @PathVariable String incYy, @PathVariable String incMon, @PathVariable Integer seq) {
		
		BoIncPlanRequest param = new BoIncPlanRequest();
		param.setBoId(boId);
		param.setIncYy(incYy);
		param.setIncMon(incMon);
		param.setSeq(seq);
		return incSalesPlanService.deleteIncSalesPlan(param);
		
	}	
	

}
