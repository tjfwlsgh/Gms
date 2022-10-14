package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.bse.dto.request.S3ObjInfoRequest;
import com.lgl.gms.webapi.bse.dto.response.S3ObjInfoResponse;
import com.lgl.gms.webapi.bse.persistence.model.S3ObjInfoModel;

/**
 * S3 오프젝트 파일 관리 DAO
 * @author jokim
 * @Date 2022.06.22
 */
public interface S3ObjectInfoDao {
	
	/**
	 * S3 오브젝트 파일 목록 조회
	 * @param param
	 * @return
	 */
	public List<S3ObjInfoResponse> selectS3ObjInfoList(S3ObjInfoRequest param);
	
	/**
	 * S3 오브젝트 파일 정보 저장
	 * @param objModel
	 */
	public void mergeS3ObjInfo(S3ObjInfoModel objModel);	
	
	/**
	 * S3오브젝트 파일 삭제
	 * @param param
	 */
	public void deleteS3ObjInfo(S3ObjInfoRequest param);

}
