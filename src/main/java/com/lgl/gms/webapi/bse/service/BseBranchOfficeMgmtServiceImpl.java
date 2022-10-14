package com.lgl.gms.webapi.bse.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.bse.dto.request.BseBranchOfficeMgmtRequest;
import com.lgl.gms.webapi.bse.dto.response.BseBranchOfficeMgmtResponse;
import com.lgl.gms.webapi.bse.persistence.dao.BseBranchOfficeMgmtDao;
import com.lgl.gms.webapi.bse.persistence.model.BseBoModel;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseBranchOfficeMgmtServiceImpl.java
 * @Date        : 22.03.22
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 마스터 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class BseBranchOfficeMgmtServiceImpl implements BseBranchOfficeMgmtService {

	@Autowired
	public BseBranchOfficeMgmtDao bbomDao;
	
	@Override
	public BaseResponse getBranchOfficeMgmt(BseBranchOfficeMgmtRequest paramDto) {
		try {
			
			// 법인 List
			List<BseBranchOfficeMgmtResponse> list = (List<BseBranchOfficeMgmtResponse>)bbomDao.selectBranchOfficeMgmtList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	@Override
	public BaseResponse addBranchOfficeMgmt(BseBranchOfficeMgmtRequest paramDto) {
		
		try {
			
			BseBoModel bseBo = bbomDao.selectBranchOfficeByBoId(paramDto.getBoId());
			
			if (bseBo != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			BseBoModel param = new BseBoModel();
			
			param.setBoId(paramDto.getBoId());
			param.setBoCd(paramDto.getBoCd());
			param.setBoCl(paramDto.getBoCl());
			param.setBoNm(paramDto.getBoNm());
			param.setBoSnm(paramDto.getBoSnm());
			param.setBoNmEng(paramDto.getBoNmEng());
			param.setBoSnmEng(paramDto.getBoSnmEng());
			param.setCntryCd(paramDto.getCntryCd());
			param.setCrncyCd(paramDto.getCrncyCd());
			param.setStd(paramDto.getStd());
			param.setEtd(paramDto.getEtd());
			param.setPboId(paramDto.getPboId());
			param.setTrrtId(paramDto.getTrrtId());
			param.setUseYn(paramDto.getUseYn());
			
			bbomDao.insertBranchOfficeMgmt(param);
			
			BseBranchOfficeMgmtResponse bbomResponse = new BseBranchOfficeMgmtResponse();
			bbomResponse.setBoId(param.getBoId());
			bbomResponse.setBoCd(param.getBoCd());
			bbomResponse.setBoCl(param.getBoCl());
			bbomResponse.setBoNm(param.getBoNm());
			bbomResponse.setBoSnm(param.getBoSnm());
			bbomResponse.setBoNmEng(param.getBoNmEng());
			bbomResponse.setBoSnmEng(param.getBoSnmEng());
			bbomResponse.setCntryCd(param.getCntryCd());
			bbomResponse.setCrncyCd(param.getCrncyCd());
			bbomResponse.setStd(param.getStd());
			bbomResponse.setEtd(param.getEtd());
			bbomResponse.setPboId(param.getPboId());
			bbomResponse.setTrrtId(param.getTrrtId());
			bbomResponse.setUseYn(param.getUseYn());
			bbomResponse.setRegDt(param.getRegDt());
			bbomResponse.setUpdDt(param.getUpdDt());
			bbomResponse.setWorkIp(param.getWorkIp());
			bbomResponse.setRegNo(param.getRegNo());
			bbomResponse.setUpdNo(param.getUpdNo());
			
			return new BaseResponse(ResponseCode.C200, bbomResponse);
			
			
		} catch(Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse deleteBranchOfficeMgmt(BseBranchOfficeMgmtRequest paramDto) {
		try {
			log.debug("paramDto.getBoId() ===> {}", paramDto.getBoId());
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getBoId().toString())) {
				return new BaseResponse(ResponseCode.C589);
			}
			
			// 법인 밑에 사용하고 있는 지사가 있는지 
			int count = bbomDao.countPboId(paramDto.getBoId());
			
			log.debug("count : " + count);
			
			// 법인은 사용하는 지사가 없는 경우에만 삭제 가능
			if (count > 0) {
				return new BaseResponse(ResponseCode.C735);
			}
			
			int isDeleted = bbomDao.deleteBranchOfficeMgmt(paramDto.getBoId());
				
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
	public BaseResponse modifyBranchOfficeMgmt(BseBoModel paramDto) {
		try {
			
			BseBoModel param = new BseBoModel();

			param.setBoId(paramDto.getBoId());
			param.setBoCd(paramDto.getBoCd());
			param.setBoCl(paramDto.getBoCl());
			param.setBoNm(paramDto.getBoNm());
			param.setBoSnm(paramDto.getBoSnm());
			param.setBoNmEng(paramDto.getBoNmEng());
			param.setBoSnmEng(paramDto.getBoSnmEng());
			param.setCntryCd(paramDto.getCntryCd());
			param.setCrncyCd(paramDto.getCrncyCd());
			param.setStd(paramDto.getStd());
			param.setEtd(paramDto.getEtd());
			param.setPboId(paramDto.getPboId());
			param.setTrrtId(paramDto.getTrrtId());
			param.setUseYn(paramDto.getUseYn());
			
			
			bbomDao.updateBranchOfficeMgmt(param);
			
			BseBranchOfficeMgmtResponse bbomResponse = new BseBranchOfficeMgmtResponse();
			bbomResponse.setBoId(param.getBoId());
			bbomResponse.setBoCd(param.getBoCd());
			bbomResponse.setBoCl(param.getBoCl());
			bbomResponse.setBoNm(param.getBoNm());
			bbomResponse.setBoSnm(param.getBoSnm());
			bbomResponse.setBoNmEng(param.getBoNmEng());
			bbomResponse.setBoSnmEng(param.getBoSnmEng());
			bbomResponse.setCntryCd(param.getCntryCd());
			bbomResponse.setCrncyCd(param.getCrncyCd());
			bbomResponse.setStd(param.getStd());
			bbomResponse.setEtd(param.getEtd());
			bbomResponse.setPboId(param.getPboId());
			bbomResponse.setTrrtId(param.getTrrtId());
			bbomResponse.setUseYn(param.getUseYn());
			bbomResponse.setRegDt(param.getRegDt());
			bbomResponse.setUpdDt(param.getUpdDt());
			bbomResponse.setWorkIp(param.getWorkIp());
			bbomResponse.setRegNo(param.getRegNo());
			bbomResponse.setUpdNo(param.getUpdNo());
			return new BaseResponse(ResponseCode.C200, bbomResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}


	@Override
	public BaseResponse selectPboCount(BseBoModel param) {
		
		int count = bbomDao.countPboId(param.getBoId());
		
		return new BaseResponse(ResponseCode.C200, count);
	}

	@Override
	public BaseResponse checkBoCd(BseBoModel param) {
		try {
			// 필수입력값 boCd 존재여부 체크(
			if (StringUtils.isBlank(param.getBoCd())				) {
				// 필수항목이 유효하지 않습니다. : 731
				return new BaseResponse(ResponseCode.C731);
			}
			
			BseBoModel bo = bbomDao.selectBranchOfficeByBoCd(param);
			if (bo != null) {
				// 법인 코드가 중복되었습니다 : 807
				return new BaseResponse(ResponseCode.C807);
			}

			// svrCode가 200이 리턴이면 등록된 boCd가 아님
			return new BaseResponse(ResponseCode.C200);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}


}
