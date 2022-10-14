package com.lgl.gms.webapi.inc.service;

import org.springframework.http.ResponseEntity;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncPerfInqRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncMemoModel;


/**
 * 손익조회
 * @author jokim
 *@Date : 2022.03.18
 */
public interface IncPerfInqService {

	public static final String PGM_ID = "BOINC006";		// 프로그램 아이디(본사 지급분 관리(실적))
	
	/**
	 * 손익조회 리스트
	 * @param param
	 * @return
	 */
	public BaseResponse selectIncPerfInqList(BoIncPerfInqRequest param);
	
	/**
	 * 손익 메모 등록
	 * @param paramModel
	 * @return
	 */
	public BaseResponse saveIncMemo(BoIncMemoModel paramModel);
	
	/**
	 * 메모 삭제
	 * @param memoId
	 * @return
	 */
	public BaseResponse deleteIncMemo(BoIncMemoModel paramModel);
	
	/**
	 * 엑셀 다운로드
	 * @param param
	 * @return
	 */
	public ResponseEntity excelDown(BoIncPerfInqRequest param);
	
}
