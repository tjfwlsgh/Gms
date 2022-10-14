package com.lgl.gms.webapi.inc.service;

import java.util.List;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpRetModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

/**
 * 비용실적
 * @author jokim
 *@Date : 2022.03.14
 */
public interface IncExpRetService extends ExcelUploadService {

	public static final String PGM_ID = "BOINC004";		// 프로그램 아이디(수익/비용 등록(실적))
	public static final String JOB_CL = "RET_CLS2";		// 집계구분(비용실적)
	public static final String CLS_INT_TASK_NM = "RET"; // 마감의 초기화 작업(sp_int_cls_mgmt) - ( PLN(계획), RET(실적) )
	
	/**
	 * 비용실적 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncExpRetList(BoIncRetRequest param);
	
	/**
	 * 비용실적 저장
	 * @param List<BoIncExpRetModel>
	 * @param user
	 * @return
	 */
	public BaseResponse saveIncExpRet(List<BoIncExpRetModel> incExpRets, BoIncRetRequest param);
	
	/**
	 * 비용실적 삭제
	 * @param param
	 * @return
	 */
	public BaseResponse deleteIncExpRet(BoIncRetRequest param);
	
	/**
	 * 마감
	 * @param BoIncRetModel
	 * @param user
	 * @return
	 */
	public BaseResponse procIncExpRetCls(BoIncRetModel paramModel, String pgmId);
	
	/**
	 * 매출조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncSalRetByExpList(BoIncRetRequest param);

	
}
