package com.lgl.gms.webapi.inf.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inf.dto.request.InfFacilityMgmtRequest;
import com.lgl.gms.webapi.inf.dto.response.InfFacilityMgmtResponse;
import com.lgl.gms.webapi.inf.persistence.dao.InfFacilityMgmtDao;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class InfFacilityMgmtServiceImpl implements InfFacilityMgmtService {
	
	@Autowired
	public InfFacilityMgmtDao ifmDao;
	
	@Override
	public BaseResponse getFacilityMgmtList(InfFacilityMgmtRequest paramDto) {
		
		try {
					
			List<InfFacilityMgmtResponse> list = (List<InfFacilityMgmtResponse>)ifmDao.selectInfraFacilityMgmtList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			log.debug("InfFacility > getFacilityMgmtList ===> {}", res);
			
			return res; 
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse addFacilityMgmt(InfFacilityMgmtRequest paramDto) {
		
		try {
			
			InfraRntModel infraRnt = ifmDao.selectInfraFacilityByRntId(paramDto.getBoRntId());
			
			if (infraRnt != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			InfraRntModel param = new InfraRntModel();
			
			param.setBoId(paramDto.getBoId());
			param.setBoNm(paramDto.getBoNm());
			param.setOffice(paramDto.getOffice());
			param.setRntTypId(paramDto.getRntTypId());
			param.setRntClId(paramDto.getRntClId());
			param.setLocClId(paramDto.getLocClId());
			param.setLocNm(paramDto.getLocNm());
//			param.setRntNm(paramDto.getRntNm());
			param.setLocClId(paramDto.getLocClId());
			param.setQty(paramDto.getQty());
			param.setUnit(paramDto.getUnit());
			param.setRntPrd(paramDto.getRntPrd());
//			param.setLtcYn(paramDto.getLtcYn());
			param.setContStd(paramDto.getContStd());
			param.setContEtd(paramDto.getContEtd());
			param.setPnltyNote(paramDto.getPnltyNote());
			param.setCrncyCd(paramDto.getCrncyCd());
			param.setHre(paramDto.getHre());
			param.setHrePrd(paramDto.getHrePrd());
			param.setDpamEndYn(paramDto.getDpamEndYn());
			param.setAddr(paramDto.getAddr());
			param.setNote(paramDto.getNote());
			param.setDpst(paramDto.getDpst());
			
			param.setBoCl(paramDto.getBoCl());
			param.setSubBoId(param.getSubBoId());
			
			ifmDao.insertFacilityMgmt(param);
			
			InfFacilityMgmtResponse ifmResponse = new InfFacilityMgmtResponse();
			ifmResponse.setBoId(param.getBoId());
			ifmResponse.setSubBoId(param.getSubBoId());
			ifmResponse.setBoNm(param.getBoNm());
			ifmResponse.setOffice(param.getOffice());
			ifmResponse.setRntTypId(param.getRntTypId());
			ifmResponse.setRntClId(param.getRntClId());
			ifmResponse.setLocClId(param.getLocClId());
			ifmResponse.setLocNm(param.getLocNm());
//			ifmResponse.setRntNm(param.getRntNm());
			ifmResponse.setLocClId(param.getLocClId());
			ifmResponse.setQty(param.getQty());
			ifmResponse.setUnit(param.getUnit());
			ifmResponse.setRntPrd(param.getRntPrd());
//			ifmResponse.setLtcYn(param.getLtcYn());
			ifmResponse.setContStd(param.getContStd());
			ifmResponse.setContEtd(param.getContEtd());
			ifmResponse.setPnltyNote(param.getPnltyNote());
			ifmResponse.setCrncyCd(param.getCrncyCd());
			ifmResponse.setHre(param.getHre());
			ifmResponse.setHrePrd(param.getHrePrd());
			ifmResponse.setDpamEndYn(param.getDpamEndYn());
			ifmResponse.setAddr(param.getAddr());
			ifmResponse.setNote(param.getNote());
			ifmResponse.setBoCl(param.getBoCl());
			ifmResponse.setDpst(param.getDpst());
			
			return new BaseResponse(ResponseCode.C200, ifmResponse);
			
			
		} catch(Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse delete(InfFacilityMgmtRequest paramDto) {
		
		try {
			log.debug(paramDto.getBoRntId().toString());
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getBoRntId().toString())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = ifmDao.deleteFacilityMgmt(paramDto.getBoRntId());
			
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
	public BaseResponse modifyFacilityMgmt(InfraRntModel paramDto) {
		
		try {
			InfraRntModel param = new InfraRntModel();
			param.setBoRntId(paramDto.getBoRntId());
			param.setBoId(paramDto.getBoId());
			param.setBoNm(paramDto.getBoNm());
			param.setOffice(paramDto.getOffice());
			param.setLocClId(paramDto.getLocClId());
			param.setLocNm(paramDto.getLocNm());
//			param.setRntNm(paramDto.getRntNm());
			param.setLocClId(paramDto.getLocClId());
			param.setQty(paramDto.getQty());
			param.setUnit(paramDto.getUnit());
			param.setRntPrd(paramDto.getRntPrd());
			param.setLtcYn(paramDto.getLtcYn());
			param.setContStd(paramDto.getContStd());
			param.setContEtd(paramDto.getContEtd());
			param.setCrncyCd(paramDto.getCrncyCd());
			param.setHre(paramDto.getHre());
			param.setDpamEndYn(paramDto.getDpamEndYn());
			param.setNote(paramDto.getNote());
			param.setDpst(paramDto.getDpst());
			param.setAddr(paramDto.getAddr());
			param.setBoCl(paramDto.getBoCl());
			
			ifmDao.updateFacilityMgmt(param);
			
			InfFacilityMgmtResponse ifmResponse = new InfFacilityMgmtResponse();
			ifmResponse.setBoId(param.getBoId());
			ifmResponse.setBoNm(param.getBoNm());
			ifmResponse.setOffice(param.getOffice());
			ifmResponse.setLocClId(param.getLocClId());
			ifmResponse.setLocNm(param.getLocNm());
//			ifmResponse.setRntNm(param.getRntNm());
			ifmResponse.setLocClId(param.getLocClId());
			ifmResponse.setQty(param.getQty());
			ifmResponse.setUnit(param.getUnit());
			ifmResponse.setRntPrd(param.getRntPrd());
//			ifmResponse.setLtcYn(param.getLtcYn());
			ifmResponse.setContStd(param.getContStd());
			ifmResponse.setContEtd(param.getContEtd());
			ifmResponse.setCrncyCd(param.getCrncyCd());
			ifmResponse.setDpamEndYn(param.getDpamEndYn());
			ifmResponse.setNote(param.getNote());
			ifmResponse.setDpst(param.getDpst());
			ifmResponse.setAddr(param.getAddr());
			ifmResponse.setBoCl(param.getBoCl());

			return new BaseResponse(ResponseCode.C200, ifmResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}


}
