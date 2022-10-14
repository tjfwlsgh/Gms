package com.lgl.gms.webapi.cmm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.cmm.dto.request.CommonRequest;
import com.lgl.gms.webapi.cmm.persistence.dao.CommonDao;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public BaseResponse getLastExchgRateYm(CommonRequest param) {

		try {
			
			return new BaseResponse(ResponseCode.C200, commonDao.getLastExchgRateYm(param));

		} catch (Exception e) {
			
			log.error("getLastExchgRateYm error ==> {} ", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}
	

}
