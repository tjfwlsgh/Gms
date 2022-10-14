package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncMonAggrRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncMonAggrResponse;
import com.lgl.gms.webapi.inc.dto.response.CommCodeResponse;

/**
 * 손익집계
 * @author jokim
 *@date 2022.03.24
 */
public interface IncMonAggrDao {

	/**
	 * 손익집계 리스트조회
	 * @param BoIncMonAggrRequest
	 * @return List<BoIncMonAggrResponse>
	*/
	public List<BoIncMonAggrResponse> selectIncMonAggrList(BoIncMonAggrRequest param);
	
	/**
	 * 검색관련 공통코드 조회
	 */
	List<CommCodeResponse> selectInc10sList(BoIncMonAggrRequest param);

}
