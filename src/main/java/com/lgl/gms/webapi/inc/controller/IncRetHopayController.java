package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetHopayRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetHopayModel;
import com.lgl.gms.webapi.inc.service.IncRetHopayService;
import com.lgl.gms.webapi.inc.service.IncRetService;

/**
 * (실적)본사지급분 관리 컨트롤러
 * @author jokim
 * @date 2022.03.15
 */
@CrossOrigin
@RequestMapping("/inc")
@RestController
public class IncRetHopayController {

	@Autowired
	public IncRetService incRetService;
	
	@Autowired
	public IncRetHopayService incRetHopayService;

	/**	
	 * (실적)본사지급분 관리 목록
	 * @param request
	 * @param body
	 * @return
	 */
	@RequestMapping(value = { "/ret-hopays" }, method = RequestMethod.GET)
	public BaseResponse selectIncSalesPlanList(HttpServletRequest request, @ModelAttribute BoIncRetRequest param) {
		
		return incRetHopayService.selectIncRetHopayList(param);
		
	}
	
	/**
	 * (실적)본사지급분 추가
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/ret-hopays" }, method = RequestMethod.POST)
	public BaseResponse saveIncHopayMgmts(HttpServletRequest request, @RequestBody BoIncRetHopayRequest param) {

		return incRetHopayService.saveIncRetHopay(param, null);
		
	}
	
	/**
	 * (실적)본사지급분 수정
	 * @param request
	 * @param param
	 * @return
	 */	
	@RequestMapping(value = { "/ret-hopays" }, method = RequestMethod.PUT)
	public BaseResponse updateIncHopayMgmts(HttpServletRequest request, @RequestBody BoIncRetHopayRequest param) {

		return incRetHopayService.updateIncRetHopay(param, null);
		
	}

	/**
	 * (실적)본사지급분 삭제
	 * @param request
	 * @param boId
	 * @param incYymm
	 * @param defCl
	 * @return
	 */
	@RequestMapping(value = { "/ret-hopays/{boId}/{incYymm}/{defCl}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncHopayMgmts(HttpServletRequest request, @PathVariable Integer boId, @PathVariable String incYymm, @PathVariable String defCl) {
		
		BoIncRetHopayRequest param = new BoIncRetHopayRequest();
		param.setBoId(boId);
		param.setIncYymm(incYymm);
		param.setDefCl(defCl);
		
		return  incRetHopayService.deleteIncRetHopay(param);
		
	}
	
	/**
	 * (실적)본사지급분 마감
	 * @param request
	 * @param paramModel
	 * @return
	 */
	@RequestMapping(value = { "/ret-hopays/cls" }, method = RequestMethod.POST)
	public BaseResponse procIncExpRetCls(HttpServletRequest request, @RequestBody BoIncRetHopayModel paramModel) {

		return incRetHopayService.procIncRetHopayCls(paramModel, null);
		
	}

}
