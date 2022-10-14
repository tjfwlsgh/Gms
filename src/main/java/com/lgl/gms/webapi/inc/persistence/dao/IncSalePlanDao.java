package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncSalePlanResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnSalModel;

public interface IncSalePlanDao {

	/**
	 * 매출계획 리스트 조회
	 * @param FinCommonRequest
	 * @return
	 */
	public List<BoIncSalePlanResponse> selectIncSalePlanList(BoIncPlanRequest boIncPlanRequest);
		
	/**
	 * 매출계획 상세 갯수
	 * @param boIncPlanRequest
	 * @return
	 */
	public Integer selectIncSalePlanDetCount(BoIncPlanRequest boIncPlanRequest);	
	
	/**
	 * 매출계획 저장
	 * @param boIncPlnSalModel
	 * @return
	 */
	public int insertIncPlanSal(List<BoIncPlnSalModel> boIncPlnSalModel);
		
	/**
	 * 매출계획 저장시 계획상세 프로시저 호출( sp_crt_inc_pln_sal_det
	 * @param boIncPlanRequest
	 */
	public void procIncSalDet(BoIncPlanRequest boIncPlanRequest);
	
	/**
	 * 해당 매출계획 삭제 (boId, incYy 필수)
	 * @param boIncPlanRequest
	 * @return int
	 */
	public int deleteIncPlanSal(BoIncPlanRequest boIncPlanRequest);	
	
	/**
	 * 매출계획 상세 삭제
	 * @param boIncPlanRequest
	 * @return
	 */
	public int deleteIncPlanSalDet(BoIncPlanRequest boIncPlanRequest);		
	
	/**
	 * 매출계획 마감
	 * @param paramModel
	 * @return int
	 */
	public int updateIncSalPlanCls(BoIncPlnModel paramModel);	
	
	
}
