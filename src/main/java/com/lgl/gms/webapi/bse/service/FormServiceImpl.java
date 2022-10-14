package com.lgl.gms.webapi.bse.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.bse.dto.request.FormRequest;
import com.lgl.gms.webapi.bse.dto.request.IncItmDetRequest;
import com.lgl.gms.webapi.bse.dto.request.IncItmInfoRequest;
import com.lgl.gms.webapi.bse.dto.response.IncItmDetResponse;
import com.lgl.gms.webapi.bse.dto.response.IncItmInfoResponse;
import com.lgl.gms.webapi.bse.persistence.dao.FormDao;
import com.lgl.gms.webapi.bse.persistence.model.IncItmDetModel;
import com.lgl.gms.webapi.bse.persistence.model.IncItmInfoModel;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : FormServiceImpl.java
 * @Date        : 22.04.06
 * @Author      : hj.Chung
 * @Description : ServiceImpl
 * @History     : 양식관리 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class FormServiceImpl implements FormService {
	
	@Autowired
	public FormDao formDao;
	
	// 손익 항목 (tab1)
	
	// 손익 항목 조회
	@Override
	public BaseResponse getIncInfoList(FormRequest paramDto) {
		try {
			// 손익 항목 list
			List<FormRequest> list = (List<FormRequest>)formDao.selectIncInfoList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	// 손익 항목 추가
	@Override
	public BaseResponse addIncInfo(IncItmInfoRequest paramDto) {
		try {
			
			IncItmInfoModel infoModel = formDao.selectIncItmInfoByIncItmId(paramDto.getIncItmId());
				
			if (infoModel != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			IncItmInfoModel param = new IncItmInfoModel();
			
			param.setIncItmId(paramDto.getIncItmId());
			param.setIncItm1(paramDto.getIncItm1());
			param.setIncItm2(paramDto.getIncItm2());
			param.setIncItm3(paramDto.getIncItm3());
			param.setIncItm4(paramDto.getIncItm4());
			param.setLvCl(paramDto.getLvCl());
			param.setItmNm(paramDto.getItmNm());
			param.setAggYn(paramDto.getAggYn());
			param.setWrkFrml1(paramDto.getWrkFrml1());
			param.setWrkFrml2(paramDto.getWrkFrml2());
			param.setViewSeq(paramDto.getViewSeq());
			param.setDelYn(paramDto.getDelYn());
			param.setCustUseYn(paramDto.getCustUseYn());
			param.setSalAggYn(paramDto.getSalAggYn());
			param.setKrwYn(paramDto.getKrwYn());
			param.setIncItmGrp1(paramDto.getIncItmGrp1());
			
			
			// 손익 항목 추가
			formDao.insertIncItmInfo(param);
			
			IncItmInfoResponse infoResponse = new IncItmInfoResponse();

			infoResponse.setIncItmId(param.getIncItmId());
			infoResponse.setIncItm1(param.getIncItm1());
			infoResponse.setIncItm2(param.getIncItm2());
			infoResponse.setIncItm3(param.getIncItm3());
			infoResponse.setIncItm4(param.getIncItm4());
			infoResponse.setLvCl(param.getLvCl());
			infoResponse.setItmNm(param.getItmNm());
			infoResponse.setAggYn(param.getAggYn());
			infoResponse.setWrkFrml1(param.getWrkFrml1());
			infoResponse.setWrkFrml2(param.getWrkFrml2());
			infoResponse.setViewSeq(param.getViewSeq());
			infoResponse.setDelYn(param.getDelYn());
			infoResponse.setCustUseYn(param.getCustUseYn());
			infoResponse.setSalAggYn(param.getSalAggYn());
			infoResponse.setKrwYn(param.getKrwYn());
			infoResponse.setIncItmGrp1(param.getIncItmGrp1());
			
			
			return new BaseResponse(ResponseCode.C200, infoResponse);
			
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	// 손익 항목 수정
	@Override
	public BaseResponse updateIncInfo(IncItmInfoModel paramDto) {
		try {
			
			IncItmInfoModel param = new IncItmInfoModel();
			
			param.setIncItmId(paramDto.getIncItmId());
			param.setIncItm1(paramDto.getIncItm1());
			param.setIncItm2(paramDto.getIncItm2());
			param.setIncItm3(paramDto.getIncItm3());
			param.setIncItm4(paramDto.getIncItm4());
			param.setLvCl(paramDto.getLvCl());
			param.setItmNm(paramDto.getItmNm());
			param.setAggYn(paramDto.getAggYn());
			param.setWrkFrml1(paramDto.getWrkFrml1());
			param.setWrkFrml2(paramDto.getWrkFrml2());
			param.setViewSeq(paramDto.getViewSeq());
			param.setDelYn(paramDto.getDelYn());
			param.setCustUseYn(paramDto.getCustUseYn());
			param.setSalAggYn(paramDto.getSalAggYn());
			param.setKrwYn(paramDto.getKrwYn());
			param.setIncItmGrp1(paramDto.getIncItmGrp1());
			
			formDao.updateIncItmInfo(param);
			
			IncItmInfoResponse infoResponse = new IncItmInfoResponse();

			infoResponse.setIncItmId(param.getIncItmId());
			infoResponse.setIncItm1(param.getIncItm1());
			infoResponse.setIncItm2(param.getIncItm2());
			infoResponse.setIncItm3(param.getIncItm3());
			infoResponse.setIncItm4(param.getIncItm4());
			infoResponse.setLvCl(param.getLvCl());
			infoResponse.setItmNm(param.getItmNm());
			infoResponse.setAggYn(param.getAggYn());
			infoResponse.setWrkFrml1(param.getWrkFrml1());
			infoResponse.setWrkFrml2(param.getWrkFrml2());
			infoResponse.setViewSeq(param.getViewSeq());
			infoResponse.setDelYn(param.getDelYn());
			infoResponse.setCustUseYn(param.getCustUseYn());
			infoResponse.setSalAggYn(param.getSalAggYn());
			infoResponse.setKrwYn(param.getKrwYn());
			infoResponse.setIncItmGrp1(param.getIncItmGrp1());
			
			
			return new BaseResponse(ResponseCode.C200, infoResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	// 손익 항목 삭제
	@Override
	public BaseResponse deleteIncInfo(IncItmInfoModel paramDto) {
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getIncItmId().toString())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = formDao.deleteIncItmInfo(paramDto.getIncItmId());
			
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
	
	// 손익 항목 상세 조회 (tab2)
	@Override
	public BaseResponse getIncDetailList(FormRequest paramDto) {
		try {
			// 손익 항목 상세 list
			List<FormRequest> list = (List<FormRequest>)formDao.selectIncDetailList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}

	// 손익 항목 상세 추가
	@Override
	public BaseResponse addIncDetail(IncItmDetRequest paramDto) {
		try {
			
			IncItmDetModel detailModel = formDao.selectIncItmDetByIncItmDetId(paramDto.getIncItmDetId());
				
			if (detailModel != null) {
				return new BaseResponse(ResponseCode.C805);
			}
			
			IncItmDetModel param = new IncItmDetModel();
			
			param.setIncItmDetId(paramDto.getIncItmDetId());
			param.setIncTypId(paramDto.getIncTypId());
			param.setIncCl1Id(paramDto.getIncCl1Id());
			param.setIncCl2Id(paramDto.getIncCl2Id());
			param.setIncCl3Id(paramDto.getIncCl3Id());
			param.setRvnYn(paramDto.getRvnYn());
			param.setExpYn(paramDto.getExpYn());
			param.setDelYn(paramDto.getDelYn());
			param.setGrp1Id(paramDto.getGrp1Id());
			param.setIncItmId(paramDto.getIncItmId());
			param.setAggYn(paramDto.getAggYn());
			
			
			// 손익 항목 상세 추가
			formDao.insertIncItmDetail(param);
			
			IncItmDetResponse detailResponse = new IncItmDetResponse();

			detailResponse.setIncItmDetId(param.getIncItmDetId());
			detailResponse.setIncTypId(param.getIncTypId());
			detailResponse.setIncCl1Id(param.getIncCl1Id());
			detailResponse.setIncCl2Id(param.getIncCl2Id());
			detailResponse.setIncCl3Id(param.getIncCl3Id());
			detailResponse.setRvnYn(param.getRvnYn());
			detailResponse.setExpYn(param.getExpYn());
			detailResponse.setDelYn(param.getDelYn());
			detailResponse.setGrp1Id(param.getGrp1Id());
			detailResponse.setIncItmId(param.getIncItmId());
			detailResponse.setAggYn(param.getAggYn());
			
			
			return new BaseResponse(ResponseCode.C200, detailResponse);
			
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}

	// 손익 항목 상세 수정
	@Override
	public BaseResponse updateIncItmDetail(IncItmDetModel paramDto) {
		try {
			
			IncItmDetModel param = new IncItmDetModel();
			
			param.setIncItmDetId(paramDto.getIncItmDetId());
			param.setIncTypId(paramDto.getIncTypId());
			param.setIncCl1Id(paramDto.getIncCl1Id());
			param.setIncCl2Id(paramDto.getIncCl2Id());
			param.setIncCl3Id(paramDto.getIncCl3Id());
			param.setRvnYn(paramDto.getRvnYn());
			param.setExpYn(paramDto.getExpYn());
			param.setDelYn(paramDto.getDelYn());
			param.setGrp1Id(paramDto.getGrp1Id());
			param.setIncItmId(paramDto.getIncItmId());
			param.setAggYn(paramDto.getAggYn());
			
			formDao.updateIncItmDetail(param);
			
			IncItmDetResponse detailResponse = new IncItmDetResponse();

			detailResponse.setIncItmDetId(param.getIncItmDetId());
			detailResponse.setIncTypId(param.getIncTypId());
			detailResponse.setIncCl1Id(param.getIncCl1Id());
			detailResponse.setIncCl2Id(param.getIncCl2Id());
			detailResponse.setIncCl3Id(param.getIncCl3Id());
			detailResponse.setRvnYn(param.getRvnYn());
			detailResponse.setExpYn(param.getExpYn());
			detailResponse.setDelYn(param.getDelYn());
			detailResponse.setGrp1Id(param.getGrp1Id());
			detailResponse.setIncItmId(param.getIncItmId());
			detailResponse.setAggYn(param.getAggYn());			
			
			return new BaseResponse(ResponseCode.C200, detailResponse);
			
		} catch (Exception e) {
			log.warn(e.toString(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	// 손익 항목 상세 삭제
	@Override
	public BaseResponse deleteIncItmDetail(IncItmDetModel paramDto) {
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getIncItmDetId().toString())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = formDao.deleteIncItmDetail(paramDto.getIncItmDetId());
			
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
	public BaseResponse getGrp1List(FormRequest param) {
		try {
			// 손익 항목 그룹 1 list
			List<FormRequest> list = (List<FormRequest>)formDao.selectGrp1List(param);
			
			return new BaseResponse(ResponseCode.C200, list);

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	


}
