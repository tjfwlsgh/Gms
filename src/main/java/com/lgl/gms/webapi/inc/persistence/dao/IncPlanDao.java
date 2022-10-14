package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncPlanResponse;
import com.lgl.gms.webapi.inc.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.inc.dto.response.TccResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoCustModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

public interface IncPlanDao {

	/**
	 * 법인매출계획 헤더정보조회
	 * @param FinCommonRequest
	 * @return 
	 */
	public BoIncPlanResponse selectIncPlan(BoIncPlanRequest param);
	
	/**
	 * 법인매출계획 헤더정보 저장
	 * @param boIncPlnModel
	 * @return
	 */
	public int insertIncPlan(BoIncPlnModel boIncPlnModel);
	
	/**
	 * 법인거래처조회
	 * @param boId
	 * @return
	 */
	public List<BoCustModel> selectBoCustList(Integer boId);	
	
	/**
	 * 그룹1,2,3 공통코드리스트 조회
	 * @return
	 */
	public List<TccResponse> selectIncPlanTccList();
	
	/**
	 * 서비스유형 조회
	 * @return
	 */
	public List<SvcTypResponse> selectIncSvcTypList();
	
	/**
	 * 손익계획 마감 프로시저호출
	 * @param BoIncPlanRequest(sp_set_cls_mgmt)
	 */
	public void procIncCls(BoIncPlanRequest param);
	
	/**
	 * 손익계획 버전업데이트
	 * @param paramModel
	 * @return
	 */
	public int updateIncPlanFinlVer(BoIncPlnModel paramModel);	
	
	/**
	 * 히스토리저장
	 * @param FinCommonRequest
	 */
	public void procIncHist(BoIncPlanRequest param);
	
	/**
	 * 손익집계 프로시저
	 * @param param
	 */
	public void procIncUpdAggProc(BoIncPlanRequest param);
	
	/**
	 * 손익마감 초기화 프로시저
	 * @param param
	 */
	public void procIncPlnClsInt(BoIncPlanRequest param);
	
	
}
