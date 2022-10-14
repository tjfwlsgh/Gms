package com.lgl.gms.webapi.sys.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.cmm.persistence.dao.LoginLogDao;
import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sys.dto.request.AccessHistRequest;
import com.lgl.gms.webapi.sys.dto.response.AccessHistResponse;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class AccessHistServiceImpl implements AccessHistService {

	@Autowired
	private LoginLogDao loginLogDao;
	
	/**
	 * 접근이력 목록 조회
	 */
	@Override
	public BaseResponse getAccessHistList(AccessHistRequest body) {
		try {
			HashMap<String, Object> param = new HashMap<>();

			// param에 값이 없어도 키가 존재하면 mybatis 조건에서 구문이 제거 되지 않으므로 
			// 아래와 같이 값이 있는 경우에만 HashMap에 키값을 설정해야 함

			if (StringUtils.isNotBlank(body.getStartDate())) {	// 시작일자
				param.put("startDate", body.getStartDate());
			}
			if (StringUtils.isNotBlank(body.getEndDate())) {	// 종료일자
				param.put("endDate", body.getEndDate());
			}
			if (StringUtils.isNotBlank(body.getLogTyp())) {	//로그 유형
				param.put("logTyp", body.getLogTyp());
			}
	
			param.put("compId", UserInfo.getCompId());
			
			System.out.println("===>>> AccessHistServiceImpl > getAccessHistList() > param : "+ param);
			
			List<AccessHistResponse> list = (List<AccessHistResponse>) loginLogDao.selectLoginLogList(param);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}


}
