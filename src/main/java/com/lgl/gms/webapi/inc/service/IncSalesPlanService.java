package com.lgl.gms.webapi.inc.service;

import java.util.List;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnSalModel;

/**
 * 매출계획
 * @author jokim
 *@Date : 2022.02.21
 */
public interface IncSalesPlanService extends ExcelUploadService {
	
	public static final String PGM_ID = "BOINC001";		// 프로그램 아이디(매출등록(계획))
	public static final String TASK_NM = "PLN_SAL";		// 작업구분(매출계획)
	public static final String AGG_TASK_NM = "PLN_CLS1";	// 작업구분(매출계획)
	public static final String CLS_INT_TASK_NM = "PLN";		// 마감초기화용 - 작업구분(계획)
	
	/**
	 * 매출계획(매출)조회
	 * @param boIncPlanRequest
	 * @return BaseResponse
	 */
	public BaseResponse selectIncSalesPlanList(BoIncPlanRequest param);
	
	/**
	 * 매출계획(매출) 저장
	 * @param boIncPlnSalModel
	 * @param user
	 * @return
	 */
	public BaseResponse saveIncSalesPlan(List<BoIncPlnSalModel> salPlans, Object user);
	
	/**
	 * 매출계획 마감
	 * @param boIncPlnSalModel
	 * @param user
	 * @return
	 */
	public BaseResponse procIncSalesPlanCls(BoIncPlnModel paramModel, String pgmId);
	
	/**
	 * 매출계획 삭제
	 * @param param
	 * @return BaseResponse
	 */
	public BaseResponse deleteIncSalesPlan(BoIncPlanRequest param);
	
}
