package com.lgl.gms.webapi.inc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetHopayRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.IncHopayItmInIdResponse;
import com.lgl.gms.webapi.inc.persistence.dao.IncRetDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncRetHopayDao;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetHopayModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 본사 지급분 관리(실적)
 * @author jokim
 * @date 2022.03.15
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncRetHopayServiceImpl implements IncRetHopayService {
	
	@Autowired
	private IncRetDao incRetDao;
	
	@Autowired
	private IncRetHopayDao incRetHopayDao;
		
	/**
	 * (실적)본사지급분 관리 목록 조회
	 */
	@Override
	public BaseResponse selectIncRetHopayList(BoIncRetRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, incRetHopayDao.selectIncRetHopayList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncRetHopayList error !! == {} ", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}

	}

	/**
	 * (실적)본사지급분 관리 저장
	 */
	@Override
	public BaseResponse saveIncRetHopay(BoIncRetHopayRequest param, Object user) {
		
		try {

			// 본사지급분 저장용 리스트
			BoIncRetHopayModel[] retHopays = makeHopayModel(param);
						
			// 추가
			incRetHopayDao.insertIncRetHopay(retHopays);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveIncRetHopay ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
			
		}

	}

	/**
	 * (실적)본사지급분 관리 수정
	 */
	@Override
	public BaseResponse updateIncRetHopay(BoIncRetHopayRequest param, Object user) {
		
		try {
			
			// 본사지급분 저장용 리스트
			BoIncRetHopayModel[] retHopays = makeHopayModel(param);
			
			for(BoIncRetHopayModel retHopay : retHopays) {
				incRetHopayDao.updateIncRetHopay(retHopay);				
			}
			
			// 손익집계 프로시저 호출
			BoIncRetRequest procParam = new BoIncRetRequest();
			procParam.setBoId(param.getBoId());
			procParam.setIncYymm(param.getIncYymm());
			procParam.setJobCl(AGG_TASK_NM);
			procParam.setDefCl(param.getDefCl());
			incRetDao.procIncUpdAggRet(procParam);
			log.info("## [{}] procIncUpdAggRet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), procParam);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("updateIncRetHopay ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * (실적)본사지급분 관리 저장용 데이터 만들기
	 * @param param
	 * @return
	 */
	private BoIncRetHopayModel[] makeHopayModel(BoIncRetHopayRequest param) {

		// 항목ID조회
		IncHopayItmInIdResponse hopayItm =  incRetHopayDao.selectIncHopayItmId();
					
		// 본사지급분 저장용 리스트
		BoIncRetHopayModel[] retHopays = new BoIncRetHopayModel[4];
		
		for(int idx = 0; idx < retHopays.length; idx++) {
			BoIncRetHopayModel hopay = new BoIncRetHopayModel();
			hopay.setBoId(param.getBoId());
			hopay.setIncYymm(param.getIncYymm());
			hopay.setDefCl(param.getDefCl());
			hopay.setTypCd(hopayItm.getTypCd());
			hopay.setCl1Cd(hopayItm.getCl1Cd());
			hopay.setCl3Cd(hopayItm.getCl3Cd());
			retHopays[idx] = hopay;
		}
		
		// 급여
		retHopays[0].setIncItmId(hopayItm.getPayItmId());
		retHopays[0].setIncItmDetId(hopayItm.getPayItmDetId());
		retHopays[0].setCl2Cd(hopayItm.getPayItmCl2());
		retHopays[0].setPtAmt(param.getPayment());			
		
		// 주택임차료
		retHopays[1].setIncItmId(hopayItm.getRentItmId());
		retHopays[1].setIncItmDetId(hopayItm.getRentItmDetId());
		retHopays[1].setCl2Cd(hopayItm.getRentItmCl2());
		retHopays[1].setPtAmt(param.getRentAmt());			

		// 자녀학자금
		retHopays[2].setIncItmId(hopayItm.getTuitionItmId());
		retHopays[2].setIncItmDetId(hopayItm.getTuitionItmDetId());
		retHopays[2].setCl2Cd(hopayItm.getTuitionItmCl2());
		retHopays[2].setPtAmt(param.getTuition());
		
		// 기타
		retHopays[3].setIncItmId(hopayItm.getEtcItmId());
		retHopays[3].setIncItmDetId(hopayItm.getEtcItmDetId());
		retHopays[3].setCl2Cd(hopayItm.getEtcItmCl2());
		retHopays[3].setPtAmt(param.getEtcAmt());
		
		return retHopays;
		
	}
	
	/**
	 * (실적)본사지급분 관리 삭제
	 * @param param
	 * @return BaseResponse
	 */
	@Override
	public BaseResponse deleteIncRetHopay(BoIncRetHopayRequest param) {
		
		try {

			incRetHopayDao.deleteIncRetHopay(param);
			
			// 손익집계 프로시저 호출
			BoIncRetRequest procParam = new BoIncRetRequest();
			procParam.setBoId(param.getBoId());
			procParam.setIncYymm(param.getIncYymm());
			procParam.setJobCl(AGG_TASK_NM);
			procParam.setDefCl(param.getDefCl());
			incRetDao.procIncUpdAggRet(procParam);
			log.info("## [{}] procIncUpdAggRet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), procParam);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("deleteIncRetHopay ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
			
		}
	}
	
	/**
	 * (실적)본사지급분 관리 마감
	 */
	@Override
	public BaseResponse procIncRetHopayCls(BoIncRetHopayModel paramModel, String pgmId) {
		
		try {

			incRetHopayDao.updateIncRetHopayCls(paramModel);
			
			// 마감프로시저호출(sp_set_cls_mgmt)
			BoIncRetRequest param = new BoIncRetRequest();
			param.setBoId(paramModel.getBoId());
			param.setIncYymm(paramModel.getIncYymm());
			String defCl = StringUtils.defaultString(paramModel.getDefCl()); 
			param.setDefCl("Q1".equals(defCl) ? "01"  : "Q2".equals(defCl) ? "02" : "" );
			param.setPgmId(PGM_ID);
			param.setWorkIp(UserInfo.getWorkIp());
			param.setUpdNo(UserInfo.getUserId());

			incRetDao.procIncRetCls(param);
			
			// 손익집계 프로시저 호출
			param.setJobCl(AGG_TASK_NM);
			param.setDefCl(defCl);
			incRetDao.procIncUpdAggRet(param);
			log.info("## [{}] procIncUpdAggRet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			return new BaseResponse(ResponseCode.C200);
						
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("procIncRetHopayCls ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}


}
