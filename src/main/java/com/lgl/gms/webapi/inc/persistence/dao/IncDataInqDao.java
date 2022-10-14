package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncDataInqRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncDataInqResponse;

/**
 * 손익 데이터 조회
 * @author jokim
 *@date 2022.04.06
 */
public interface IncDataInqDao {

	/**
	 * 손익데이터조회
	 * @param BoIncCurrMonPerfRequest
	 * @return List<BoIncCurrMonPerfResponse>
	*/
	public List<BoIncDataInqResponse> selectIncDataInqList(BoIncDataInqRequest param);
	
}
