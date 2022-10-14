package com.lgl.gms.webapi.inc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncDataInqRequest;
import com.lgl.gms.webapi.inc.persistence.dao.IncDataInqDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 손익데이터조회 Impl
 * @author jokim
 * @date 2022.04.06
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncDataInqServiceImpl implements IncDataInqService {
	
	@Autowired
	private IncDataInqDao dtaInqDao;


	/**
	 * 손익데이터조회
	 */
	@Override
	public BaseResponse selectIncDataInqList(BoIncDataInqRequest param) {
		
		try {	
			
			return new BaseResponse(ResponseCode.C200, dtaInqDao.selectIncDataInqList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncDataInqList error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}


	
}
