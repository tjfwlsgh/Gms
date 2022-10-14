package com.lgl.gms.webapi.inc.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetHopayRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetHopayModel;

/**
 * 본사 지급분 관리(실적)
 * @author jokim
 *@Date : 2022.03.14
 */
public interface IncRetHopayService {

	public static final String PGM_ID = "BOINC005";			// 프로그램 아이디(본사 지급분 관리(실적))
	public static final String AGG_TASK_NM = "RET_CLS3";	// 본사지급분
	
	/**
	 * 본사 지급분 관리 실적 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncRetHopayList(BoIncRetRequest param);
	
	/**
	 * 본사 지급분 실적 저장
	 * @param user
	 * @return BaseResponse
	 */
	public BaseResponse saveIncRetHopay(BoIncRetHopayRequest param, Object user);
	
	/**
	 * 본사지급분 실적 업데이트
	 * @param paramModel
	 * @return
	 */
	public BaseResponse updateIncRetHopay(BoIncRetHopayRequest param, Object user);
	
	/**
	 * 본사지급분 삭제
	 * @param seqs
	 * @param user
	 * @return
	 */
	public BaseResponse deleteIncRetHopay(BoIncRetHopayRequest param);
	
	/**
	 * 본사지급분 마감
	 * @param paramModel
	 * @param user
	 * @return
	 */
	public BaseResponse procIncRetHopayCls(BoIncRetHopayModel paramModel, String pgmId);
	
	
}
