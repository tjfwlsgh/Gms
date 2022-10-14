package com.lgl.gms.webapi.fin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.request.FinCurrStatusInqRequest;
import com.lgl.gms.webapi.fin.persistence.dao.CurrStatusInqDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : CurrStatusInqServiceImpl.java
 * @Date        : 22.05.06
 * @Author      : hj.Chung
 * @Description : ServiceImpl
 * @History     : 재무제표 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class CurrStatusInqServiceImpl implements CurrStatusInqService {
	
	@Autowired
	public CurrStatusInqDao csiDao;

	@Override
	public BaseResponse getBalanSheetList(FinCurrStatusInqRequest paramDto) {
		
		try {
			List<FinCurrStatusInqRequest> list = (List<FinCurrStatusInqRequest>)csiDao.selectBalanSheetList(paramDto);
			
			log.debug("CurrStatusInqServiceImpl > getBalanSheetList =====> {}", list);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.error("getBalanSheetList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse getBoList(BoRequest param) {
		try {
			
			List<BoResponse> list = (List<BoResponse>) csiDao.selectBoListByCurr(param);
			
			return new BaseResponse(ResponseCode.C200, list);

		} catch (Exception e) {
			log.error(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse getIncStatementList(FinCurrStatusInqRequest paramDto) {
		try {
			List<FinCurrStatusInqRequest> list = (List<FinCurrStatusInqRequest>)csiDao.selectIncStatementList(paramDto);
			
			log.debug("CurrStatusInqServiceImpl > getIncStatementList =====> {}", list);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.error("getBalanSheetList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}

	
	

}
