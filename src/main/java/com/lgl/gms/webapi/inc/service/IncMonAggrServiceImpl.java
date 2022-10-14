package com.lgl.gms.webapi.inc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncMonAggrRequest;
import com.lgl.gms.webapi.inc.persistence.dao.IncMonAggrDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 손익집계조회 Impl
 * @author jokim
 * @date 2022.03.25
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncMonAggrServiceImpl implements IncMonAggrService {
	
	@Autowired
	private IncMonAggrDao incMonAggrDao;

	/**
	 * 비교손익 목록 조회
	 */
	@Override
	public BaseResponse selectIncMonAggrList(BoIncMonAggrRequest param) {
		try {	
			
			return new BaseResponse(ResponseCode.C200, incMonAggrDao.selectIncMonAggrList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncMonAggrList error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}


	
}
