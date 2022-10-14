package com.lgl.gms.webapi.bse.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.bse.dto.request.BseBoCustMgmtRequest;
import com.lgl.gms.webapi.bse.dto.response.BseBoCustMgmtResponse;
import com.lgl.gms.webapi.bse.persistence.dao.BseBoCustMgmtDao;
import com.lgl.gms.webapi.bse.persistence.model.BseBoCustModel;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseBoCustMgmtServiceImpl.java
 * @Date        : 22.03.28
 * @Author      : hj.Chung
 * @Description : ServiceImpl
 * @History     : 거래처 마스터 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class BseBoCustMgmtServiceImpl implements BseBoCustMgmtService {
	
	@Autowired
	public BseBoCustMgmtDao bbcmDao;

	@Override
	public BaseResponse getBoCustMgmt(BseBoCustMgmtRequest paramDto) {
		try {
			// 법인 거래처 List
			List<BseBoCustMgmtRequest> list = (List<BseBoCustMgmtRequest>)bbcmDao.selectBoCustMgmtList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addBoCustMgmt(BseBoCustMgmtRequest paramDto) {
		try {
			
			BseBoCustModel bseBo = bbcmDao.selectBoCustByIdAndCd(paramDto);
			
			if (bseBo != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			BseBoCustModel param = new BseBoCustModel();
			
			param.setBoId(paramDto.getBoId());
			param.setBoCustCd(paramDto.getBoCustCd());
			param.setCustNm(paramDto.getCustNm());
			param.setCustSnm(paramDto.getCustSnm());
			param.setCustNmEng(paramDto.getCustNmEng());
			param.setCustSnmEng(paramDto.getCustSnmEng());
			param.setDelYn(paramDto.getDelYn());
			
			bbcmDao.insertBoCustMgmt(param);
			
			BseBoCustMgmtResponse bbcmResponse = new BseBoCustMgmtResponse();
			bbcmResponse.setBoId(param.getBoId());
			bbcmResponse.setBoCustCd(param.getBoCustCd());
			bbcmResponse.setCustNm(param.getCustNm());
			bbcmResponse.setCustSnm(param.getCustSnm());
			bbcmResponse.setCustNmEng(param.getCustNmEng());
			bbcmResponse.setCustSnmEng(param.getCustSnmEng());
			bbcmResponse.setDelYn(param.getDelYn());
		
			
			return new BaseResponse(ResponseCode.C200, bbcmResponse);
			
			
		} catch(Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	@Override
	public BaseResponse checkBoCustCd(BseBoCustModel paramDto) {
		try {
			
			// 필수입력값 boCd 존재여부 체크(
			if (StringUtils.isBlank(paramDto.getBoCustCd()) && StringUtils.isBlank(paramDto.getBoId().toString())) {
				// 필수항목이 유효하지 않습니다. : 731
				return new BaseResponse(ResponseCode.C731);
			}
			BseBoCustModel boCust = bbcmDao.checkSelectBoCd(paramDto);
			if (boCust != null) {
				// 코드가 중복되었습니다 : 807
				return new BaseResponse(ResponseCode.C807);
			}

			// svrCode가 200이 리턴이면 등록된 boCd가 아님
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse deleteBoCustMgmt(BseBoCustModel paramDto) {
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getBoId().toString()) && StringUtils.isBlank(paramDto.getBoCustCd())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = bbcmDao.deleteBoCustMgmt(paramDto);
				
			if (isDeleted < 1) {
				return new BaseResponse(ResponseCode.C589);
			}

			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse updateBoCustMgmt(BseBoCustModel paramDto) {
		try {
			
			BseBoCustModel param = new BseBoCustModel();

			param.setBoId(paramDto.getBoId());
			param.setBoId(paramDto.getBoId());
			param.setBoCustCd(paramDto.getBoCustCd());
			param.setCustNm(paramDto.getCustNm());
			param.setCustSnm(paramDto.getCustSnm());
			param.setCustNmEng(paramDto.getCustNmEng());
			param.setCustSnmEng(paramDto.getCustSnmEng());
			param.setDelYn(paramDto.getDelYn());
			
			bbcmDao.updateBoCustMgmt(param);
			
			BseBoCustMgmtResponse bbcmResponse = new BseBoCustMgmtResponse();
			bbcmResponse.setBoId(param.getBoId());
			bbcmResponse.setBoCustCd(param.getBoCustCd());
			bbcmResponse.setCustNm(param.getCustNm());
			bbcmResponse.setCustSnm(param.getCustSnm());
			bbcmResponse.setCustNmEng(param.getCustNmEng());
			bbcmResponse.setCustSnmEng(param.getCustSnmEng());
			bbcmResponse.setDelYn(param.getDelYn());
			
			return new BaseResponse(ResponseCode.C200, bbcmResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

}
