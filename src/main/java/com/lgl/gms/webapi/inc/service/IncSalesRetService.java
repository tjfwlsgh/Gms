package com.lgl.gms.webapi.inc.service;

import java.util.List;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetSalModel;

/**
 * 매출실적
 * @author jokim
 *@Date : 2022.03.10
 */
public interface IncSalesRetService extends ExcelUploadService {
	
	public static final String PGM_ID = "BOINC003";		// 프로그램 아이디(매출등록(실적))
	public static final String JOB_CL = "RET_CLS1";		// 집계구분(매출실적) - (실적)영업손익
	public static final String CLS_INT_TASK_NM = "RET"; // 마감의 초기화 작업(sp_int_cls_mgmt) - ( PLN(계획), RET(실적) )
	
	/**
	 * 매출실적 조회
	 * @param boIncPlanRequest
	 * @return BaseResponse
	 */
	public BaseResponse selectIncSaleRetList(BoIncRetRequest param);
	
	/**
	 * 매출실적 저장
	 * @param List<BoIncRetSalModel>
	 * @param user
	 * @return
	 */
	public BaseResponse saveIncSalesRet(List<BoIncRetSalModel> retSales, Object user);
	
	/**
	 * 매출실적 마감
	 * @param BoIncRetModel
	 * @param user
	 * @return
	 */
	public BaseResponse procIncSalRetCls(BoIncRetModel paramModel, String pgmId);
	
	/**
	 * 매출실적 삭제
	 * @param param
	 * @return BaseResponse
	 */
	public BaseResponse deleteIncSalesRet(BoIncRetRequest param);
	
}
