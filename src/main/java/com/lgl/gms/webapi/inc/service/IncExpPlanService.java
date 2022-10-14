package com.lgl.gms.webapi.inc.service;

import java.util.List;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpPlanModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

/**
 * 비용계획
 * @author jokim
 *@Date : 2022.02.21
 */
public interface IncExpPlanService extends ExcelUploadService {

	public static final String PGM_ID = "BOINC002";		// 프로그램 아이디(수익/비용 등록(계획))
	public static final String TASK_NM = "PLN_EXP";		// 작업구분(비용계획)
	public static final String AGG_TASK_NM = "PLN_CLS2";	// 손익계획(비용)
	public static final String CLS_INT_TASK_NM = "PLN";		// 마감초기화용 - 작업구분(계획)
	
	/**
	 * 비용계획 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncExpPlanList(BoIncPlanRequest param);
	
	/**
	 * 비용계획 저장
	 * @param incExpPlanModel
	 * @param user
	 * @return
	 */
	public BaseResponse saveIncExpPlan(List<BoIncExpPlanModel> incExpPlans, BoIncPlanRequest param);
	
	/**
	 * 비용계획 삭제
	 * @param param
	 * @return
	 */
	public BaseResponse deleteIncExpPlan(BoIncPlanRequest param);
	
	/**
	 * 마감
	 * @param boIncPlnSalModel
	 * @param user
	 * @return
	 */
	public BaseResponse procIncExpPlnCls(BoIncPlnModel paramModel, String pgmId);
	
	/**
	 * 해당 Row의 비용계획 의 매출조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncSalPlnDelByExpList(BoIncPlanRequest param);
	
	
}
