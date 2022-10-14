package com.lgl.gms.webapi.inc.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

/**
 * 손익 실적 공통 서비스
 * @author noname
 *@Date : 2022.02.21
 */
public interface IncRetService {

	/**
	 * 손익 실적 HEADER 데이터 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncRet(BoIncRetRequest param);

	/**
	 * 손익 실적 HEADER 데이터 저장
	 * @param boIncRetModel
	 * @param user
	 * @return
	 */
	public BaseResponse insertIncRet(BoIncRetModel boIncRetModel, Object user);
	
	
	
}
