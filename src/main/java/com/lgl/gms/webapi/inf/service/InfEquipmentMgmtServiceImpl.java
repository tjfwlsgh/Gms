package com.lgl.gms.webapi.inf.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inf.dto.request.InfEquipmentMgmtRequest;
import com.lgl.gms.webapi.inf.dto.response.InfEquipmentMgmtResponse;
import com.lgl.gms.webapi.inf.persistence.dao.InfEquipmentMgmtDao;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : InfEquipmentMgmtServiceImpl.java
 * @Date        : 22.03.11
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 법인 장비현황 관리 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class InfEquipmentMgmtServiceImpl implements InfEquipmentMgmtService {
	
	@Autowired
	public InfEquipmentMgmtDao iemDao;
	
	// 직원현황 List 조회
	@Override
	public BaseResponse getEquipmentMgmtList(InfEquipmentMgmtRequest paramDto) {
		try {
			
			List<InfEquipmentMgmtResponse> list = (List<InfEquipmentMgmtResponse>)iemDao.selectInfraEquipmentMgmtList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addEquipmentMgmt(InfEquipmentMgmtRequest paramDto) {
		try {
			
			InfraRntModel infraRnt = iemDao.selectInfraEquipmentByRntId(paramDto.getBoRntId());
			
			if (infraRnt != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			InfraRntModel param = new InfraRntModel();
			
			param.setBoId(paramDto.getBoId());
			param.setBoNm(paramDto.getBoNm());
			param.setRntTypId(paramDto.getRntTypId());
			param.setRntClId(paramDto.getRntClId());
			param.setLocClId(paramDto.getLocClId());
			param.setLocNm(paramDto.getLocNm());
			param.setRntNm(paramDto.getRntNm());
			param.setLocClId(paramDto.getLocClId());
			param.setQty(paramDto.getQty());
			param.setUnit(paramDto.getUnit());
			param.setRntPrd(paramDto.getRntPrd());
			param.setLtcYn(paramDto.getLtcYn());
			param.setContStd(paramDto.getContStd());
			param.setContEtd(paramDto.getContEtd());
			param.setPnltyNote(paramDto.getPnltyNote());
			param.setCrncyCd(paramDto.getCrncyCd());
			param.setHre(paramDto.getHre());
			param.setHrePrd(paramDto.getHrePrd());
			param.setDpamEndYn(paramDto.getDpamEndYn());
			param.setNote(paramDto.getNote());
			param.setBoCl(paramDto.getBoCl());
			param.setSubBoId(param.getSubBoId());
			
			iemDao.insertEquipmentMgmt(param);
			
			InfEquipmentMgmtResponse iemResponse = new InfEquipmentMgmtResponse();
			iemResponse.setBoId(param.getBoId());
			iemResponse.setBoNm(param.getBoNm());
			iemResponse.setRntTypId(param.getRntTypId());
			iemResponse.setRntClId(param.getRntClId());
			iemResponse.setLocClId(param.getLocClId());
			iemResponse.setLocNm(param.getLocNm());
			iemResponse.setRntNm(param.getRntNm());
			iemResponse.setLocClId(param.getLocClId());
			iemResponse.setQty(param.getQty());
			iemResponse.setUnit(param.getUnit());
			iemResponse.setRntPrd(param.getRntPrd());
			iemResponse.setLtcYn(param.getLtcYn());
			iemResponse.setContStd(param.getContStd());
			iemResponse.setContEtd(param.getContEtd());
			iemResponse.setPnltyNote(param.getPnltyNote());
			iemResponse.setCrncyCd(param.getCrncyCd());
			iemResponse.setHre(param.getHre());
			iemResponse.setHrePrd(param.getHrePrd());
			iemResponse.setDpamEndYn(param.getDpamEndYn());
			iemResponse.setNote(param.getNote());
			iemResponse.setBoCl(param.getBoCl());
		
			return new BaseResponse(ResponseCode.C200, iemResponse);
			
			
		} catch(Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse delete(InfEquipmentMgmtRequest paramDto) {
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getBoRntId().toString())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = iemDao.deleteEquipmentMgmt(paramDto.getBoRntId());
			
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
	public BaseResponse modifyEquipmentMgmt(InfraRntModel paramDto) {
		try {
			InfraRntModel param = new InfraRntModel();
			param.setBoRntId(paramDto.getBoRntId());
			param.setBoId(paramDto.getBoId());
			param.setBoNm(paramDto.getBoNm());
			param.setRntTypId(paramDto.getRntTypId());
			param.setRntClId(paramDto.getRntClId());
			param.setLocClId(paramDto.getLocClId());
			param.setLocNm(paramDto.getLocNm());
			param.setRntNm(paramDto.getRntNm());
			
			param.setQty(paramDto.getQty());
			param.setUnit(paramDto.getUnit());
			param.setRntPrd(paramDto.getRntPrd());
			param.setLtcYn(paramDto.getLtcYn());
			param.setContStd(paramDto.getContStd());
			param.setContEtd(paramDto.getContEtd());
			param.setPnltyNote(paramDto.getPnltyNote());
			param.setCrncyCd(paramDto.getCrncyCd());
			param.setHre(paramDto.getHre());
			param.setHrePrd(paramDto.getHrePrd());
			param.setDpamEndYn(paramDto.getDpamEndYn());
			param.setNote(paramDto.getNote());
			param.setBoCl(paramDto.getBoCl());
			param.setSubBoId(paramDto.getSubBoId());
			
			iemDao.updateEquipmentMgmt(param);
			
			InfEquipmentMgmtResponse iemResponse = new InfEquipmentMgmtResponse();
			iemResponse.setBoRntId(param.getBoRntId());
			iemResponse.setBoId(param.getBoId());
			iemResponse.setBoNm(param.getBoNm());
			iemResponse.setRntTypId(param.getRntTypId());
			iemResponse.setRntClId(param.getRntClId());
			iemResponse.setLocClId(param.getLocClId());
			iemResponse.setLocNm(param.getLocNm());
			iemResponse.setRntNm(param.getRntNm());
			iemResponse.setLocClId(param.getLocClId());
			iemResponse.setQty(param.getQty());
			iemResponse.setUnit(param.getUnit());
			iemResponse.setRntPrd(param.getRntPrd());
			iemResponse.setLtcYn(param.getLtcYn());
			iemResponse.setContStd(param.getContStd());
			iemResponse.setContEtd(param.getContEtd());
			iemResponse.setPnltyNote(param.getPnltyNote());
			iemResponse.setCrncyCd(param.getCrncyCd());
			iemResponse.setHre(param.getHre());
			iemResponse.setHrePrd(param.getHrePrd());
			iemResponse.setDpamEndYn(param.getDpamEndYn());
			iemResponse.setNote(param.getNote());
			iemResponse.setBoCl(param.getBoCl());

			return new BaseResponse(ResponseCode.C200, iemResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

}
