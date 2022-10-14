package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncSaleRetResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

public interface IncSaleRetDao {

	/**
	 * 매출실적 리스트 조회
	 * @param BoIncRetRequest
	 * @return
	 */
	public List<BoIncSaleRetResponse> selectIncSaleRetList(BoIncRetRequest param);
	
	/**
	 * 매출계획 상세 갯수
	 * @param boIncPlanRequest
	 * @return
	 */
	public Integer selectIncSaleRetDetCount(BoIncRetRequest param);
	
	/**
	 * 매출실적 저장
	 * @param boIncPlnSalModel
	 * @return
	 */
	public int insertIncRetSal(Map<String, Object> incRetSalMap);
		
	/**
	 * 매출계획 저장시 계획상세 프로시저 호출( sp_crt_inc_pln_sal_det )
	 * @param boIncRetRequest
	 */
	public void procIncSalRetDet(BoIncRetRequest boIncRetRequest);
	
	/**
	 * 해당 매출실적 삭제 (boId, incYymm,  defCl 필수)
	 * @param BoIncRetRequest
	 * @return int
	 */
	public int deleteIncRetSal(BoIncRetRequest param);	
	
	/**
	 * 매출실적 상세 삭제
	 * @param boIncPlanRequest
	 * @return
	 */
	public int deleteIncRetSalDet(BoIncRetRequest param);		
	
	/**
	 * 매출실적 마감
	 * @param paramModel
	 * @return int
	 */
	public int updateIncSalRetCls(BoIncRetModel paramModel);	
	
	
}
