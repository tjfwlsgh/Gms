package com.lgl.gms.webapi.inc.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inc.dto.request.BoIncPerfInqRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncPerfInqExcelDownResponse;
import com.lgl.gms.webapi.inc.dto.response.BoIncPerfInqResponse;
import com.lgl.gms.webapi.inc.persistence.model.BoIncMemoModel;

/**
 * 손익조회
 * @author jokim
 *@date 2022.03.18
 */
public interface IncPerfInqDao {

	/**
	 * 손익조회 리스트조회
	 * @param BoIncPerfInqRequest
	 * @return List<BoIncPerfInqResponse>
	*/
	public List<BoIncPerfInqResponse> selectIncPerfInqList(BoIncPerfInqRequest param);
	
	public List<BoIncPerfInqExcelDownResponse> selectIncPerfInqExcelList(BoIncPerfInqRequest param);
	
	/**
	 * 손익 실적 메모 등록
	 * @param paramModel
	 * @return
	 */
	public int insertIncMemo(BoIncMemoModel paramModel);
	
	/**
	 * 메모 삭제
	 * @param memoId
	 * @return
	 */
	public int deleteIncMemo(BoIncMemoModel paramModel);
	

}
