package com.lgl.gms.webapi.inc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncCurrMonPerfRequest;
import com.lgl.gms.webapi.inc.persistence.dao.IncCurrMonPerfDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 손익리포트 Impl
 * @author jokim
 * @date 2022.03.31
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncCurrMonPerfServiceImpl implements IncCurrMonPerfService {
	
	@Autowired
	private IncCurrMonPerfDao currMonPerfDao;

	/**
	 * 손익리포트 목록
	 */
	@Override
	public BaseResponse selectIncCurrMonPerfList(BoIncCurrMonPerfRequest param) {
		try {	
			param.setCurrColExr("m"+param.getIncYymm().substring(4)+"_exr");
			param.setCurrColNm("mon_"+param.getIncYymm().substring(4)+"_amt");
			param.setCurrColNmLy("mon_"+param.getIncYymm().substring(4)+"_amt_ly");
			return new BaseResponse(ResponseCode.C200, currMonPerfDao.selectIncCurrMonPerfList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncCurrMonPerfList error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}

	/**
	 * 손익리포트 조회용 콤보목록
	 */
	@Override
	public BaseResponse selectItemInfoList() {
		try {	
			
			return new BaseResponse(ResponseCode.C200, currMonPerfDao.selectItemInfoList());		
			
		} catch (Exception e) {
			
			log.error("selectItemComboList error!! => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}


	
}
