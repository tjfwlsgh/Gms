package com.lgl.gms.webapi.bse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.bse.dto.request.BseIncUpRequest;
import com.lgl.gms.webapi.bse.dto.response.BseIncUpResponse;
import com.lgl.gms.webapi.bse.persistence.dao.BseIncUpDao;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseIncUpServiceImpl.java
 * @Date        : 22.03.30
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 손익 데이터 업로드 현황 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class BseIncUpServiceImpl implements BseIncUpService {
	
	@Autowired
	public BseIncUpDao biuDao;

	@Override
	public BaseResponse getIncRetList(BseIncUpRequest paramDto) {
		try {

			List<BseIncUpResponse> list = (List<BseIncUpResponse>)biuDao.selectIncRetList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			log.debug("list ===> {}", list);
			
			return res;
			
		} catch (Exception e) {
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse getIncPlnList(BseIncUpRequest paramDto) {
		try {

			List<BseIncUpResponse> list = (List<BseIncUpResponse>)biuDao.selectIncPlnList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			log.debug("list ===> {}", list);
			
			return res;
			
		} catch (Exception e) {
			return new BaseResponse(ResponseCode.C589);
		}
	}


}
