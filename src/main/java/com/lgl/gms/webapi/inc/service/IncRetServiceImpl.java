package com.lgl.gms.webapi.inc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.dao.IncRetDao;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 매출계획 공통서비스 Impl
 * @author jokim
 * @date 2022.02.22
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncRetServiceImpl implements IncRetService {
	
	@Autowired
	private IncRetDao incRetDao;
	
	/**
	 * 손익실적 헤더정보 조회
	 */
	@Override
	public BaseResponse selectIncRet(BoIncRetRequest param) {
		
		try {	
			
			return new BaseResponse(ResponseCode.C200, incRetDao.selectIncRet(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncRet error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	
	/**
	 * 손익실적 HEADER 데이터 등록
	 */
	@Override
	public BaseResponse insertIncRet(BoIncRetModel boIncRetModel, Object user) {
		
		try {
			
			incRetDao.insertIncRet(boIncRetModel);
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("insertIncRet error!! => {}", e.getMessage());			
			
			return new BaseResponse(ResponseCode.C589);
		}	
		
	}
	
}
