package com.lgl.gms.webapi.bse.service;

import java.util.Map;

import com.lgl.gms.webapi.bse.dto.request.FinFormRequest;
import com.lgl.gms.webapi.bse.persistence.model.FrmInfoModel;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;

public interface FinFormService extends ExcelUploadService {

	/**
	 * 재무양식정보 가져오기
	 * @param paramDto
	 * @return
	 */
	public BaseResponse getFrmInfoList(FinFormRequest paramDto);
	
	/**
	 * 재무양식상세 정보 가져오기
	 * @param paramDto
	 * @return
	 */
	public BaseResponse getFrmInfoDetList(FinFormRequest paramDto);
	
	/**
	 * 재무양식정보 추가
	 * @param paramDto
	 * @return
	 */
	public BaseResponse insertFrmInfo(FrmInfoModel paramDto);

	/**
	 * 재무양식 수정
	 * @param paramDto
	 * @return
	 */
	public BaseResponse updateFrmInfo(FrmInfoModel paramDto);
	
	/**
	 * 계정관리조회
	 * @param paramDto
	 * @return
	 */
	public BaseResponse getAccMgmtList(FinFormRequest paramDto);
	
	/**
	 * 저장
	 * @param saveFinPlMap
	 * @return
	 */
	public BaseResponse saveFrmInfoDet(Map<String, Object> frmDetMap);


}
