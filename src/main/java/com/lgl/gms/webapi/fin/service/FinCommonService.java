package com.lgl.gms.webapi.fin.service;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.FinCommonRequest;

/**
 * 재무공통 서비스
 * @author jokim
 * @Date : 2022.04.13
 */
public interface FinCommonService {
	
	/**
	 * 양식 정보 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectFrmInfoList(FinCommonRequest param);

	/**
	 * 양식 정보 법인 리스트 조회
	 * @param param
	 * @return
	 */
	public BaseResponse selectFrmBoInfoList(FinCommonRequest param);
	
	/**
	 * 엑셀 Sheets 명 리스트 가지고 오기
	 * @param file
	 * @param body
	 * @return
	 */
	public BaseResponse getExcelSheetNames(MultipartFile file, Object body);
	
	/**
	 * 법인목록 리스트(법인리스트) - 법인목록관리
	 * @param param
	 * @return
	 */
	public BaseResponse selectFinBoMgmtList(FinCommonRequest param, String pos);
	
	/**
	 * 양식법인 저장
	 * @param param
	 * @return
	 */
	public BaseResponse saveFinBoMgmtList(HashMap<String, Object> frmBoInfo);
	
}
