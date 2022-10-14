package com.lgl.gms.webapi.fin.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.fin.dto.request.FinCommonRequest;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoBoResponse;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoDetResponse;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoResponse;

/**
 * 재무공통 DAO
 * @author jokim
 * @date 2022.04.13
 */
public interface FinCommonDao {
	
	/**
	 * 양식정보 리스트 조회
	 * @param param
	 * @return
	 */
	public List<FrmInfoResponse> selectFrmInfoList(FinCommonRequest param);

	/**
	 * 양식정보 법인 리스트 조회
	 * @param ExcelRequest
	 * @return 
	 */
	public List<FrmInfoBoResponse> selectFrmInfoBoList(FinCommonRequest param);
	
	/**
	 * 양식정보 상세 리스트 조회
	 * @param param
	 * @return
	 */
	public List<FrmInfoDetResponse> selectFrmInfoDetList(FinCommonRequest param);
	
	
	/**
	 * 재무제표 법인 리스트(법인리스트) - 법인목록관리 왼쪽 리스트
	 * @param param
	 * @return
	 */
	public List<FrmInfoBoResponse> selectFrmSourceBoList(FinCommonRequest param);
	
	
	/**
	 * 재무제표 법인양식 리스트(법인양식 법인리스트) - 법인목록관리 오른쪽 리스트
	 * @param param
	 * @return
	 */
	public List<FrmInfoBoResponse> selectFrmTargetBoList(FinCommonRequest param);
	
	/**
	 * 법인양식 Insert
	 * @param frmBoInfo
	 * @return
	 */
	public int insertFrmInfoBos(HashMap<String, Object> frmBoInfo);
	
	/**
	 * 법인양식관리 삭제
	 * @param param
	 * @return
	 */
	public int deleteFrmInfoBos(FinCommonRequest param);
	
	
}
