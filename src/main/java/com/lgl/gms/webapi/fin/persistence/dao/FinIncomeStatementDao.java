package com.lgl.gms.webapi.fin.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.fin.dto.request.FinIncomeStatementRequest;
import com.lgl.gms.webapi.fin.dto.response.BoPlInfoResponse;
import com.lgl.gms.webapi.fin.dto.response.FinIncomeStatementResponse;
import com.lgl.gms.webapi.fin.persistence.model.BoPlDetModel;
import com.lgl.gms.webapi.fin.persistence.model.BoPlInfoModel;

public interface FinIncomeStatementDao {
	
	/**
	 * 법인 PL 정보 insert
	 * @param plInfoMap
	 * @return
	 */
	public int insertBoPlInfo(Map<String, Object> plInfoMap);
	
	/**
	 * 법인 PL 정보 update
	 * @param boIncPlnSalModel
	 * @return
	 */
	public int updateBoPlInfo(BoPlInfoModel plModel);
	
	/**
	 * 법인 PL 정보 조회
	 * @param param
	 * @return
	 */
	public List<BoPlInfoResponse> selectBoPlInfoList(FinIncomeStatementRequest param);
	
	/**
	 * 손익계산서 헤더 리스트 조회
	 * @param param
	 * @return
	 */
	public List<FinIncomeStatementResponse> selectFinBoPlInfoList(FinIncomeStatementRequest param);
	
	/**
	 * 손익계산서 리스트 조회
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> selectFinIncStatementList(FinIncomeStatementRequest param);
	
	/**
	 * 법인 PL 상세 원화 저장
	 * @param boPlDetModel
	 * @return
	 */
	public int insertBoPlDetKrw(List<BoPlDetModel> boPlDetModel);
	
	/**
	 * 법인 PL 상세 외화 저장
	 * @param boPlDetModel
	 * @return
	 */
	public int insertBoPlDetLocCrcy(List<BoPlDetModel> boPlDetModel);
	
	/**
	 * 법인 PL 상세 원화 삭제
	 * @param FinIncomeStatementRequest
	 * @return
	 */
	public int deleteBoPlDetKrw(FinIncomeStatementRequest param);
	
	/**
	 * 법인 PL 상세 외화 삭제
	 * @param FinIncomeStatementRequest
	 * @return
	 */
	public int deleteBoPlDetLocCrcy(FinIncomeStatementRequest param);
}
