package com.lgl.gms.webapi.bse.service;

import com.lgl.gms.webapi.bse.dto.request.S3ObjInfoRequest;
import com.lgl.gms.webapi.bse.persistence.model.S3ObjInfoModel;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface S3ObjInfoService {

	/**
	 * S3 ObjectInfoList 가져오기
	 * @param param
	 * @return
	 */
	public BaseResponse selectS3ObjInfoList(S3ObjInfoRequest param);
	
	/**
	 * S3 오브젝트 파일 정보 저장
	 * @param objModel
	 * @return
	 */
	public BaseResponse saveS3ObjInfo(S3ObjInfoModel objModel);
	
	/**
	 * S3 오브젝트 파일정보 삭제
	 * @param param
	 * @return
	 */
	public BaseResponse deleteS3ObjInfo(S3ObjInfoRequest param);

}
