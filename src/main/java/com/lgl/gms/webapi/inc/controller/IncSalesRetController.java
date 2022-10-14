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
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetSalModel;
import com.lgl.gms.webapi.inc.service.IncRetService;
import com.lgl.gms.webapi.inc.service.IncSalesRetService;

/**
 * (실적)영업손익등록 컨트롤러
 * @author jokim
 * 2022.02.18
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
public class IncSalesRetController {

	@Autowired
	public IncRetService incRetService;
	
	@Autowired
	public IncSalesRetService incSalesRetService;

	/**
	 * (실적)영업손익등록 목록 조회
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/sales-rets" }, method = RequestMethod.GET)
	public BaseResponse selectIncSaleRetList(HttpServletRequest request, @ModelAttribute BoIncRetRequest param) {
		
		return incSalesRetService.selectIncSaleRetList(param);
		
	}
	
	/**
	 * (실적)영업손익등록 저장
	 * @param request
	 * @param incRetSals
	 * @return
	 */
	@RequestMapping(value = { "/sales-rets" }, method = RequestMethod.POST)
	public BaseResponse saveIncPlnSal(HttpServletRequest request, @RequestBody List<BoIncRetSalModel> incRetSals) {

		return incSalesRetService.saveIncSalesRet(incRetSals, null);
		
	}	
	
	
	/**
	 * (실적)영업손익등록 엑셀업로드
	 * @param request
	 * @param file
	 * @return BaseResponse
	 */
	@RequestMapping(value = { "/sales-rets/xls/{sheetNum}/{boId}/{incYymm}/{defCl}" }, method = RequestMethod.POST)
	public BaseResponse excelUpload(HttpServletRequest request, @RequestPart(value = "file") MultipartFile file
									, BoIncRetRequest body, @PathVariable Integer sheetNum, @PathVariable Integer boId, @PathVariable String incYymm, @PathVariable String defCl) {
		
		body.setSheetNum(sheetNum);
		body.setBoId(boId);
		body.setIncYymm(incYymm);
		body.setDefCl(defCl);
		
		return incSalesRetService.excelUpload(file, body);
	}

	/**
	 * (실적)영업손익등록 마감
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/sales-rets/cls" }, method = RequestMethod.POST)
	public BaseResponse procIncSalPlnCls(HttpServletRequest request, @RequestBody BoIncRetModel paramModel) {

		return incSalesRetService.procIncSalRetCls(paramModel, null);
		
	}	
	
	/**
	 * (실적)영업손익등록 삭제
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/sales-rets/{boId}/{incYymm}/{defCl}/{seq}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncSalPln(HttpServletRequest request, @PathVariable Integer boId, @PathVariable String incYymm, @PathVariable String defCl, @PathVariable Integer seq) {
		
		BoIncRetRequest param = new BoIncRetRequest();
		param.setBoId(boId);
		param.setIncYymm(incYymm);
		param.setDefCl(defCl);
		param.setSeq(seq);
		return incSalesRetService.deleteIncSalesRet(param);
		
	}	
	

}
