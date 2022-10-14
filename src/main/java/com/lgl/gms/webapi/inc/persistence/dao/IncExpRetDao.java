package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncExpRetResponse;
import com.lgl.gms.webapi.inc.dto.response.IncSaleRetDetResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

public interface IncExpRetDao {

	/**
	 * 비용실적 리스트 조회
	 * @param BoIncRetRequest
	 * @return
	*/
	public List<BoIncExpRetResponse> selectIncExpRetList(BoIncRetRequest param);
	
	
	/**
	 * 비용실적 삭제
	 * @param BoIncRetRequest
	 * @return
	*/
	public int deleteIncExpRet(BoIncRetRequest param);	
	
	/**
	 * 손익 비용실적 저장
	 * @param List<BoIncExpRetModel>
	 * @return
	*/
	public int insertIncExpRet(Map<String, Object> intExpMap);
	
	/**
	 * 손익 비용실적 마감
	 * @param BoIncRetModel
	 * @return
	 */
	public int updateIncExpRetCls(BoIncRetModel paramModel);
	
	/**
	 * 법인 손익 수익/비용실적에서 법인 손익 매출 실적 상세 데이터 : 해당 비용실적 seq 에 해당하는 매출실적 상세를 조회
	 * @param param
	 * @return
	 */
	public List<IncSaleRetDetResponse> selectIncSalRetByExpList(BoIncRetRequest param);	
	
	/**
	 * 비용실적 등록시(영업실적) 금액 검증용
	 * @param param
	 * @return
	 */
	public Map<String, Object> selectType1SaleRetAmt(Map<String, Object> param);	
	
}
