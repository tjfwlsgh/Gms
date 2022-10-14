package com.lgl.gms.webapi.bse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.bse.dto.request.S3ObjInfoRequest;
import com.lgl.gms.webapi.bse.persistence.dao.S3ObjectInfoDao;
import com.lgl.gms.webapi.bse.persistence.model.S3ObjInfoModel;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : S3ObjInfoServiceImpl.java
 * @Date        : 2022.06.21
 * @Author      : jokim
 * @Description : ServiceImpl
 * @History     : S3오브젝트 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class S3ObjInfoServiceImpl implements S3ObjInfoService {
	
	@Autowired
	public S3ObjectInfoDao s3ObjectDao;
	
	/**
	 * 파일목록 조회
	 */
	@Override
	public BaseResponse selectS3ObjInfoList(S3ObjInfoRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, s3ObjectDao.selectS3ObjInfoList(param));
			
		} catch (Exception e) {
			log.error("getS3ObjInfoList error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	/**
	 * S3 오브젝트 정보 저장
	 */
	@Override
	public BaseResponse saveS3ObjInfo(S3ObjInfoModel objModel) {
		
		try {
			
			s3ObjectDao.mergeS3ObjInfo(objModel);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveS3ObjInfo error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * S3 오브젝트 정보 삭제
	 */
	@Override
	public BaseResponse deleteS3ObjInfo(S3ObjInfoRequest param) {
		
		try {
			
			s3ObjectDao.deleteS3ObjInfo(param);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("deleteS3ObjInfo error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	

}
