package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.bse.dto.request.FinFormRequest;
import com.lgl.gms.webapi.bse.dto.response.AccMgmtResponse;
import com.lgl.gms.webapi.bse.dto.response.FinFormInfoDetResponse;
import com.lgl.gms.webapi.bse.dto.response.FinFormInfoResponse;
import com.lgl.gms.webapi.bse.persistence.model.FrmInfoModel;

/**
 * 재무양식관리 DAO
 * @author jokim
 * @Date 2022.05.09
 */
public interface FinFormDao {
	
	/**
	 * 재무양식정보 조회
	 * @param paramDto
	 * @return
	 */
	public List<FinFormInfoResponse> selectFrmInfoList(FinFormRequest paramDto);

	/**
	 * 재무양식상세 조회
	 * @param paramDto
	 * @return
	 */
	public List<FinFormInfoDetResponse> selectFrmInfoDetList(FinFormRequest paramDto);

	/**
	 * 재무양식 추가
	 * @param FrmInfoModel
	 * @return
	 */
	public int insertFrmInfo(FrmInfoModel frmInfoModel);
	
	/**
	 * 재무양식 수정
	 * @param FrmInfoModel
	 * @return
	 */
	public int updateFrmInfo(FrmInfoModel frmInfoModel);
	
	/**
	 * 재무양식 정보 상세 삭제 
	 * @param param
	 * @return
	 */
	public int deleteFrmInfoDetList(FinFormRequest param);
	
	/**
	 * 재무양식 정보 상세 추가 
	 * @param paramMap
	 * @return
	 */
	public int insertFrmInfoDetList(Map<String, Object> paramMap);
	
	/**
	 * 계정 관리 조회
	 * @param paramDto
	 * @return
	 */
	public List<AccMgmtResponse> selectAccMgmtList(FinFormRequest paramDto);
	
}
