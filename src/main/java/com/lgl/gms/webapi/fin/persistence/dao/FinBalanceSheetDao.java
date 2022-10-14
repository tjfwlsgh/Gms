package com.lgl.gms.webapi.fin.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.fin.dto.request.FinBalanceSheetRequest;
import com.lgl.gms.webapi.fin.dto.response.BoBsInfoResponse;
import com.lgl.gms.webapi.fin.dto.response.FinBalanceSheetResponse;
import com.lgl.gms.webapi.fin.persistence.model.BoBsDetModel;
import com.lgl.gms.webapi.fin.persistence.model.BoBsInfoModel;

public interface FinBalanceSheetDao {
	
	/**
	 * 법인 BS 정보 insert
	 * @param bsInfoMap
	 * @return
	 */
	public int insertBoBsInfo(Map<String, Object> bsInfoMap);
	
	/**
	 * 법인 BS 정보 update
	 * @param bsModel
	 * @return
	 */
	public int updateBoBsInfo(BoBsInfoModel bsModel);
	
	/**
	 * 법인 BS 정보 조회
	 * @param param
	 * @return
	 */
	public List<BoBsInfoResponse> selectBoBsInfoList(FinBalanceSheetRequest param);
	
	/**
	 * 재무제표 헤더 리스트 조회
	 * @param param
	 * @return
	 */
	public List<FinBalanceSheetResponse> selectFinBoBsInfoList(FinBalanceSheetRequest param);
	
	/**
	 * 재무제표 리스트 조회
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectFinBsSheetList(FinBalanceSheetRequest param);
	
	/**
	 * 법인 BS 상세 원화 저장
	 * @param boBsDetModel
	 * @return
	 */
	public int insertBoBsDetKrw(List<BoBsDetModel> boBsDetModel);
	
	/**
	 * 법인 BS 상세 외화 저장
	 * @param boBsDetModel
	 * @return
	 */
	public int insertBoBsDetLocCrcy(List<BoBsDetModel> boBsDetModel);
	
	/**
	 * 법인 BS 상세 원화 삭제
	 * @param FinBalanceSheetRequest
	 * @return
	 */
	public int deleteBoBsDetKrw(FinBalanceSheetRequest param);
	
	/**
	 * 법인 BS 상세 외화 삭제
	 * @param FinBalanceSheetRequest
	 * @return
	 */
	public int deleteBoBsDetLocCrcy(FinBalanceSheetRequest param);
}
