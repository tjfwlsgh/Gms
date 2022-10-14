package com.lgl.gms.webapi.inc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncPerfInqRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncMemoModel;
import com.lgl.gms.webapi.inc.service.IncPerfInqService;

import lombok.extern.slf4j.Slf4j;

/**
 * 월별손익 컨트롤러
 * @author jokim
 * @date 2022.03.18
 */
@CrossOrigin
@RequestMapping("/inc")
@Slf4j
@RestController
public class IncPerfInqController {

	@Autowired
	public IncPerfInqService incPerfInqService;

	/**
	 * 월별손익 목록 조회
	 * @param request
	 * @param param
	 * @return
	 */
	@RequestMapping(value = { "/perf-inqs" }, method = RequestMethod.GET)
	public BaseResponse selectIncPerfInqs(HttpServletRequest request, @ModelAttribute BoIncPerfInqRequest param) {
		
		return incPerfInqService.selectIncPerfInqList(param);
		
	}

	/**
	 * 월별손익 메모 저장
	 * @param request
	 * @param paramModel
	 * @return
	 */
	@RequestMapping(value = { "/perf-inqs/memo" }, method = RequestMethod.POST)
	public BaseResponse saveIncMemo(HttpServletRequest request, @RequestBody BoIncMemoModel paramModel) {
		
		return incPerfInqService.saveIncMemo(paramModel);
	}	
	
	/**
	 * 월별손익 메모 삭제
	 * @param request
	 * @param memoId
	 * @return
	 */
	@RequestMapping(value = { "/perf-inqs/memo/{memoId}"}, method = RequestMethod.DELETE)
	public BaseResponse deleteIncMemo(HttpServletRequest request, @PathVariable Integer memoId) {
		
		BoIncMemoModel paramModel = new BoIncMemoModel();
		paramModel.setMemoId(memoId);
		return incPerfInqService.deleteIncMemo(paramModel);
		
	}	
	
	/**
	 * 엑셀 다운로드
	 * @param request
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/perf-inqs/xls-down" }, method = RequestMethod.GET)
	public ResponseEntity excelDown(HttpServletRequest request, @ModelAttribute BoIncPerfInqRequest param) {

		return incPerfInqService.excelDown(param);
		
	}


}
