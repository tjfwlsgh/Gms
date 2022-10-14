package com.lgl.gms.webapi.inc.persistence.dao;

import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncRetResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

public interface IncRetDao {

	/**
	 * 손익 실적 헤더정보조회
	 * @param BoIncRetRequest
	 * @return 
	 */
	public BoIncRetResponse selectIncRet(BoIncRetRequest param);
	
	/**
	 * 손익 실적 헤더정보 저장
	 * @param boIncPlnModel
	 * @return
	 */
	public int insertIncRet(BoIncRetModel boIncRetModel);
	
	/**
	 * 손익실적 마감 프로시저호출
	 * @param BoIncRetRequest(sp_set_cls_mgmt)
	 */
	public void procIncRetCls(BoIncRetRequest param);
	
	/**
	 * 손익 실적 집계 프로시저 호출
	 * @param param
	 */
	public void procIncUpdAggRet(BoIncRetRequest param);
	
	/**
	 * 손익마감 초기화 프로시저
	 * @param param
	 */
	public void procIncRetClsInt(BoIncRetRequest param);
	
	
}
