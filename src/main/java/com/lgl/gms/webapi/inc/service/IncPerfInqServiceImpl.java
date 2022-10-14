package com.lgl.gms.webapi.inc.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.excel.ExcelFile;
import com.lgl.gms.webapi.common.excel.SXSSFExcelFile;
import com.lgl.gms.webapi.inc.dto.request.BoIncPerfInqRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncPerfInqExcelDownResponse;
import com.lgl.gms.webapi.inc.persistence.dao.IncPerfInqDao;
import com.lgl.gms.webapi.inc.persistence.model.BoIncMemoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 손익조회 Impl
 * @author jokim
 * @date 2022.03.18
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncPerfInqServiceImpl implements IncPerfInqService {
	
	@Autowired
	private IncPerfInqDao incPerfInqDao;
	
	/**
	 * 월별손익 목록 조회
	 */
	@Override
	public BaseResponse selectIncPerfInqList(BoIncPerfInqRequest param) {
		
		try {	
			
			return new BaseResponse(ResponseCode.C200, incPerfInqDao.selectIncPerfInqList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncPerfInqList error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}

	/**
	 * 월별손익 메모 저장
	 * 메모 수정시 버전업하여 등록한다.
	 */
	@Override
	public BaseResponse saveIncMemo(BoIncMemoModel paramModel) {

		try {	
			
			return new BaseResponse(ResponseCode.C200, incPerfInqDao.insertIncMemo(paramModel));		
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveIncMemo error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}

	/**
	 * 월별손익 메모 삭제
	 */
	@Override
	public BaseResponse deleteIncMemo(BoIncMemoModel paramModel) {

		try {	
			
			return new BaseResponse(ResponseCode.C200, incPerfInqDao.deleteIncMemo(paramModel));		
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("deleteIncMemo error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}

	/**
	 * 엑셀 다운로드
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEntity excelDown(BoIncPerfInqRequest param) {
		
		try {
			
			List<BoIncPerfInqExcelDownResponse> incPerfInqList =  incPerfInqDao.selectIncPerfInqExcelList(param);
			ExcelFile<BoIncPerfInqExcelDownResponse> excelFile = new SXSSFExcelFile<>(incPerfInqList, BoIncPerfInqExcelDownResponse.class, Integer.parseInt(param.getEndYymm().substring(4)));
			return excelFile.write();
			
		} catch (IOException e) {
			
			log.error("excelDown error!! => {}", e.getMessage());
			return null;
			
		}
	}


	
}
