package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncCurrMonPerfRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncCurrMonPerfResponse;
import com.lgl.gms.webapi.inc.dto.response.IncItmInfoResponse;

/**
 * 손익 당월실적
 * @author jokim
 *@date 2022.03.31
 */
public interface IncCurrMonPerfDao {

	/**
	 * 손익당월리스트조회
	 * @param BoIncCurrMonPerfRequest
	 * @return List<BoIncCurrMonPerfResponse>
	*/
	public List<BoIncCurrMonPerfResponse> selectIncCurrMonPerfList(BoIncCurrMonPerfRequest param);
	
	/**
	 * 손익당월실적 조회용 항목 리스트
	 * @return List<IncItmInfoResponse>
	 */
	public List<IncItmInfoResponse> selectItemInfoList();

}
