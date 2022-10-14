package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncPlanResponse;
import com.lgl.gms.webapi.inc.dto.response.IncItmDetResponse;
import com.lgl.gms.webapi.inc.dto.response.IncSalePlanDetResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpPlanModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

public interface IncExpPlanDao {

	/**
	 * 비용계획 리스트 조회
	 * @param FinCommonRequest
	 * @return
	*/
	public List<BoIncPlanResponse> selectIncExpPlanList(BoIncPlanRequest param);
	
	/**
	 * 손익 항목 상세 조회
	 * @param compId
	 * @return
	*/
	public List<IncItmDetResponse> selectIncItmDetList(BoIncPlanRequest param);	
	
	/**
	 * 비용계획 삭제
	 * @param FinCommonRequest
	 * @return
	*/
	public int deleteIncExpPlan(BoIncPlanRequest param);	
	
	/**
	 * 손익 비용계획 저장
	 * @param boIncExpPlanModel
	 * @return
	*/
	public int insertIncExpPlan(List<BoIncExpPlanModel> boIncExpPlanModel);
	
	/**
	 * 손익 비용계획 마감
	 * @param boIncExpPlanModel
	 * @return
	 */
	public int updateIncExpPlnCls(BoIncPlnModel paramModel);
	
	/**
	 * 법인 손익 수익/비용 계획에서 법인 손익 매출 계획 상세 데이터 : 해당 비용계획 seq 에 해당하는 매출계획 상세를 조회
	 * @param param
	 * @return
	 */
	public List<IncSalePlanDetResponse> selectIncSalPlnDelByExpList(BoIncPlanRequest param);	
	
	/**
	 * 비용계획 등록시(영업실적) 금액 검증용 
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectType1SaleAmt(Map<String, Object> param);	
	
}
