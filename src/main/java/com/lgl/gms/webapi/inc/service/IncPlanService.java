package com.lgl.gms.webapi.inc.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

/**
 * 매출계획 공통 서비스
 * @author noname
 *@Date : 2022.02.21
 */
public interface IncPlanService {

	/**
	 * 법인손인계획 HEADER 데이터 조회
	 * @param FinCommonRequest
	 * @return BaseResponse
	 */
	public BaseResponse selectBoIncPlan(BoIncPlanRequest param);
	
	/**
	 *  법인손인계획 HEADER 데이터 저장
	 * @param boIncPlnModel
	 * @param user
	 * @return
	 */
	public BaseResponse insertIncPlan(BoIncPlnModel boIncPlnModel, Object user);
	
	
	
}
