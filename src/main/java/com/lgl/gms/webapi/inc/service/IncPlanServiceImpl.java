package com.lgl.gms.webapi.inc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.persistence.dao.IncPlanDao;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 매출계획 공통서비스 Impl
 * @author noname
 * @date 2022.02.22
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncPlanServiceImpl implements IncPlanService {
	
	@Autowired
	private IncPlanDao incPlanDao;
	
	/**
	 * 손익계획 HEADER 데이터 조회
	 */
	@Override
	public BaseResponse selectBoIncPlan(BoIncPlanRequest boIncPlanRequest) {
		
		try {	
			
			return new BaseResponse(ResponseCode.C200, incPlanDao.selectIncPlan(boIncPlanRequest));		
			
		} catch (Exception e) {
			
			log.error("selectBoIncPln error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	
	/**
	 * 손익계획 HEADER 데이터 등록
	 */
	@Override
	public BaseResponse insertIncPlan(BoIncPlnModel boIncPlnModel, Object user) {
		
		try {
			
			incPlanDao.insertIncPlan(boIncPlnModel);
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("insertIncPlan error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}	
		
	}
	
}
