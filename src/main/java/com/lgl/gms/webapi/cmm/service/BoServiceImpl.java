package com.lgl.gms.webapi.cmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.cmm.persistence.dao.BoDao;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class BoServiceImpl implements BoService {

	@Autowired
	private BoDao boDao;
	
	@Override
	public BaseResponse selectBoList(BoRequest param) {

		try {
			
			List<BoResponse> list = (List<BoResponse>) boDao.selectBoList(param);
			
			return new BaseResponse(ResponseCode.C200, list);

		} catch (Exception e) {
			log.error(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

}
