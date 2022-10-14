package com.lgl.gms.webapi.inc.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.common.util.MessageUtil;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncPlanResponse;
import com.lgl.gms.webapi.inc.dto.response.IncSalePlanExcelResponse;
import com.lgl.gms.webapi.inc.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.inc.dto.response.TccResponse;
import com.lgl.gms.webapi.inc.dto.response.TccValResponse;
import com.lgl.gms.webapi.inc.persistence.dao.IncPlanDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncSalePlanDao;
import com.lgl.gms.webapi.inc.persistence.model.BoCustModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnSalModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncSalesPlanServiceImpl implements IncSalesPlanService {
	
	@Autowired
	private IncPlanDao incPlanDao;
	
	@Autowired
	private IncSalePlanDao incSalePlanDao;
	
	/**
	 * (계획)영업손익등록 목록 조회
	 */
	@Override
	public BaseResponse selectIncSalesPlanList(BoIncPlanRequest boIncPlanRequest) {

		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("detTotCnt", incSalePlanDao.selectIncSalePlanDetCount(boIncPlanRequest));
			returnMap.put("incSalPlans", incSalePlanDao.selectIncSalePlanList(boIncPlanRequest));
			
			return new BaseResponse(ResponseCode.C200, returnMap);		
			
		} catch (Exception e) {
			
			log.error("selectIncSalesPlanList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	
	/**
	 * (계획)영업손익등록 저장
	 */
	@Override
	public BaseResponse saveIncSalesPlan(List<BoIncPlnSalModel> salPlans, Object user) {
		
		if(salPlans == null || salPlans.size() == 0) {
			return new BaseResponse(ResponseCode.C781);
		}
		
		Integer	boId = salPlans.get(0).getBoId();
		String	incYy = salPlans.get(0).getIncYy();
		
		BoIncPlanRequest boIncPlanRequest = new BoIncPlanRequest();
		boIncPlanRequest.setBoId(boId);
		boIncPlanRequest.setIncYy(incYy);
		
		try {			
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			incSalePlanDao.deleteIncPlanSal(boIncPlanRequest);
			
			// 데이터 저장
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)salPlans.size() / MAX_ROW);					
			
			for(int idx = 0; idx < loopCnt; idx++ ) {

				int startIdx = idx*MAX_ROW;
				int endIdx = salPlans.size() < startIdx+MAX_ROW ? salPlans.size() : startIdx+MAX_ROW;

				List<BoIncPlnSalModel> temIntExps = salPlans.subList(startIdx, endIdx);
				incSalePlanDao.insertIncPlanSal(temIntExps);
			}
			
			// 매출계획 상세 프로시저 호출
			incSalePlanDao.procIncSalDet(boIncPlanRequest);
			
			log.info("## [{}] procIncSalDet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
			
			// 상세 프로시저 오류 시
			if(boIncPlanRequest.getRtn().contains("ERROR")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C301);
			}			
			
			// 마감된 경우 버전 update
			BoIncPlanResponse incPln = incPlanDao.selectIncPlan(boIncPlanRequest);
			if("Y".equals(incPln.getPlnCls1())) {
				
				// 최종버전 업데이트
				BoIncPlnModel paramModel = new BoIncPlnModel();
				paramModel.setBoId(boIncPlanRequest.getBoId());
				paramModel.setIncYy(boIncPlanRequest.getIncYy());
				incPlanDao.updateIncPlanFinlVer(paramModel);
				
				// 히스토리저장
				boIncPlanRequest.setTaskNm(TASK_NM);
				incPlanDao.procIncHist(boIncPlanRequest);
				
				log.info("## [{}] procIncHist rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
				
				// 손익집계 프로시저 호출
				boIncPlanRequest.setTaskNm(AGG_TASK_NM);
				incPlanDao.procIncUpdAggProc(boIncPlanRequest);
				
				log.info("## [{}] procIncUpdAggProc rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
				
				// 손익집계프로시저 중 오류시
				if(boIncPlanRequest.getRtn().contains("ERROR")) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return new BaseResponse(ResponseCode.C302);
				}
				
			} else {
				
				// 마감초기화프로시저(마감테이블에 초기화 데이터 저장) - 최초저장시에 INSERT됨
				boIncPlanRequest.setTaskNm(CLS_INT_TASK_NM);
				incPlanDao.procIncPlnClsInt(boIncPlanRequest);
				log.info("## [{}] procIncPlnClsInt rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
				
			}

			return new BaseResponse(ResponseCode.C200);
			
						
		} catch (Exception e) {
			log.error("saveIncSalesPlan ==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			
			return new BaseResponse(ResponseCode.C589);
		}	
		
	}
	
	/**
	 * (계획)영업손익등록 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		BoIncPlanRequest req = (BoIncPlanRequest)body;
		List<IncSalePlanExcelResponse> incSaelsPlanList;		// 엑셀 내용 담을 리스트 객체
		
		try {
			incSaelsPlanList = ExcelUtil.getObjectList(file, IncSalePlanExcelResponse::from, 3, null, req.getSheetNum());
			
			List<BoCustModel> boCustList = incPlanDao.selectBoCustList(req.getBoId());	// 거래처체크용
			List<SvcTypResponse> svcTypList = incPlanDao.selectIncSvcTypList();		// 서비스체크용
			List<TccResponse> tccCodeList = incPlanDao.selectIncPlanTccList();		// 그룹체크용
			
			for( int idx = 0; idx < incSaelsPlanList.size(); idx++ ) {
				IncSalePlanExcelResponse salesPlan = incSaelsPlanList.get(idx);
				// 헤더정보 저장
				salesPlan.setBoId(req.getBoId());
				salesPlan.setIncYy(req.getIncYy());
				
				// 월 zero pad
				salesPlan.setIncMon(String.format("%02d", Integer.parseInt(salesPlan.getIncMon())));
				
				// 금액계산
				// 판관비 : 인건비+경비
				// 영업이익 : 매출액 - 매출원가 - 판관비(인건비+경비)
				if( salesPlan.getErrorColumns() == null || (!salesPlan.getErrorColumns().contains("lbrCost") && !salesPlan.getErrorColumns().contains("expAmt")) ) {
					// ** 판매관리비 ** //
					BigDecimal seaeAmt = new BigDecimal(salesPlan.getLbrCost()).add(new BigDecimal(salesPlan.getExpAmt()));
					salesPlan.setSeaeAmt(seaeAmt.toString());
					
					if(salesPlan.getErrorColumns() == null || !salesPlan.getErrorColumns().contains("salAmt")) {
						// ** 영업이익 ** //
						BigDecimal ifoAmt = new BigDecimal(salesPlan.getSalAmt()).subtract(new BigDecimal(salesPlan.getSalCst()))
												.subtract(new BigDecimal(salesPlan.getLbrCost())).subtract(new BigDecimal(salesPlan.getExpAmt()));
						salesPlan.setIfoAmt(ifoAmt.toString());
					}					
				}
				
				// 거래처 체크
				checkBoCust(salesPlan, boCustList);							
				// 그룹체크
				checkGrpCd(salesPlan, tccCodeList);
				// 서비스 체크
				checkSvcTyp(salesPlan, svcTypList);	
			}

			return new BaseResponse(ResponseCode.C200, incSaelsPlanList);
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("IncSalesPlanServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	/**
	 * 거래처 유효성검사
	 * @param salesPlan
	 * @param boId
	 * @throws Exception
	 */
	private void checkBoCust(IncSalePlanExcelResponse salesPlan, List<BoCustModel> boCustList) throws Exception {
		
		String inputCustCd = StringUtils.defaultString(salesPlan.getBoCustCd()); // 거래처코드
		String inputCustNm= StringUtils.defaultString(salesPlan.getCustNm());	// 거래처명 

		BoCustModel boCust = boCustList.stream()
				  .filter(cust -> inputCustCd.equals(cust.getBoCustCd())
						  	|| inputCustNm.equals(cust.getCustNm()) || inputCustNm.equals(cust.getCustNmEng())
						  	|| inputCustNm.equals(cust.getCustSnm()) || inputCustNm.equals(cust.getCustSnmEng()) )
				  .findAny()
				  .orElse(null);
		
		if(boCust != null) {
			salesPlan.setBoCustCd(boCust.getBoCustCd());
			if(!boCust.getCustNm().equals(inputCustNm) && !inputCustNm.equals(boCust.getCustNmEng())) {
				salesPlan.setCustNm(boCust.getCustNm());
			}
		} else {
			String errorMsg = salesPlan.getErrorMsg();
			errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + ", ";

			salesPlan.setErrorMsg(errorMsg+MessageUtil.getMessage("message.chkcust", null));
			
			// 에러컬럼 추가
			List<String> errorColumns = salesPlan.getErrorColumns();
			errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
			errorColumns.add("boCustCd");
			salesPlan.setErrorColumns(errorColumns);
		}

	}
	
	/**
	 * 서비스유형 유효성 검사
	 * @param salesPlan
	 * @throws Exception
	 */
	private void checkSvcTyp(IncSalePlanExcelResponse salesPlan, List<SvcTypResponse> svcTypList) throws Exception {
		
		String inputSvcNm = StringUtils.defaultString(salesPlan.getSvcTyp());
		String inputGrp1Nm= StringUtils.defaultString(salesPlan.getGrp1Cd());
		
		SvcTypResponse svcTypData = null;
		// 그룹1에 오류가 없을경우에 체크
		if( salesPlan.getErrorColumns() == null || (!salesPlan.getErrorColumns().contains("grp1Cd")) ) {
			// 해당그룹1에 서비스 항목이 맞는지..
			// 서비스 항목작성 오류가 있는지..
			svcTypData = svcTypList.stream()
					  .filter(svc -> (inputGrp1Nm.equals(svc.getStdCdNm()) || inputGrp1Nm.equals(svc.getStdCdNmEng())) &&
							  	( inputSvcNm.equals(svc.getSvcNm()) || inputSvcNm.equals(svc.getSvcNmEng())
							  			|| inputSvcNm.equals(svc.getSvcSnm()) || inputSvcNm.equals(svc.getSvcSnmEng()) )
							  )
					  .findAny()
					  .orElse(null);			
		}
		
	
		if(svcTypData != null) {
			salesPlan.setSvcTypc(svcTypData.getSvcTyp());
//			salesPlan.setGrp1Cdc(svcTypData.getTccvId());
			
//			if(!svcTypData.getStdCdNm().equals(inputGrp1Nm) && !svcTypData.getStdCdNmEng().equals(inputGrp1Nm)) {
//				salesPlan.setGrp1Cd(svcTypData.getStdCdNm());
//			}
		} else {
			setError(salesPlan, "message.chksvc", "svcTyp");	
		}

    }

	/**
	 * 그룹1, 2,3 유효성검사
	 * @param salesPlan
	 * @throws Exception
	 */
    private void checkGrpCd(IncSalePlanExcelResponse salesPlan, List<TccResponse> tccCodeList) throws Exception {
    	
    	String inputGrp1Nm = StringUtils.defaultString(salesPlan.getGrp1Cd());
    	String inputGrp2Nm = StringUtils.defaultString(salesPlan.getGrp2Cd());
    	String inputGrp3Nm = StringUtils.defaultString(salesPlan.getGrp3Cd());
    	
    	// 그룹1
    	List<TccValResponse> grp1Tccvs = tccCodeList.get(0).getTccVals();
    	
    	// 그룹2
    	List<TccValResponse> grp2Tccvs = tccCodeList.get(1).getTccVals();
    	
    	// 그룹3
    	List<TccValResponse> grp3Tccvs = tccCodeList.get(2).getTccVals();
    	
    	TccValResponse grp1Tccv = grp1Tccvs.stream()
				  .filter(tcc -> inputGrp1Nm.equals(tcc.getStdCdNm() ) || inputGrp1Nm.equals(tcc.getStdCdNmEng()))
				  .findAny()
				  .orElse(null);
    	
    	TccValResponse grp2Tccv = grp2Tccvs.stream()
				  .filter(tcc -> inputGrp2Nm.equals(tcc.getStdCdNm() ) || inputGrp2Nm.equals(tcc.getStdCdNmEng()))
				  .findAny()
				  .orElse(null);
		
    	TccValResponse grp3Tccv = grp3Tccvs.stream()
				  .filter(tcc -> inputGrp3Nm.equals(tcc.getStdCdNm() ) || inputGrp3Nm.equals(tcc.getStdCdNmEng()))
				  .findAny()
				  .orElse(null);
    	
    	if(grp1Tccv != null) {
			salesPlan.setGrp1Cdc(grp1Tccv.getTccvId());
		} else {
			setError(salesPlan, "message.chkgrp1", "grp1Cd");
		}
    	
		if(grp2Tccv != null) {
			salesPlan.setGrp2Cdc(grp2Tccv.getTccvId());
		} else {
			setError(salesPlan, "message.chkgrp2", "grp2Cd");
		}
		
		if(grp3Tccv != null) {
			salesPlan.setGrp3Cdc(grp3Tccv.getTccvId());
		} else {
//			setError(salesPlan, "message.chkgrp3", "grp3Cd");
		}

    }
    
    /**
	 * 에러 추가
	 * @param expPlan
	 * @param msgKey
	 * @param cols
	 */
	private void setError(IncSalePlanExcelResponse salesPlan, String msgKey, String... cols) {		
		String errorMsg = salesPlan.getErrorMsg();
		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
		salesPlan.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
		
		// 에러컬럼 추가
		List<String> errorColumns = salesPlan.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		salesPlan.setErrorColumns(errorColumns);
	}

    /**
     * (계획)영업손익등록 마감
     */
	@Override
	public BaseResponse procIncSalesPlanCls(BoIncPlnModel boIncPlnModel, String pgmId) {
		
		try {
			incSalePlanDao.updateIncSalPlanCls(boIncPlnModel);
			
			// 마감프로시저호출(sp_set_cls_mgmt)
			BoIncPlanRequest boIncPlanRequest = new BoIncPlanRequest();
			boIncPlanRequest.setBoId(boIncPlnModel.getBoId());
			boIncPlanRequest.setIncYy(boIncPlnModel.getIncYy());
			boIncPlanRequest.setPgmId(PGM_ID);
			boIncPlanRequest.setWorkIp(boIncPlnModel.getWorkIp());
			boIncPlanRequest.setUpdNo(boIncPlnModel.getUpdNo());
			incPlanDao.procIncCls(boIncPlanRequest);
			
			log.info("## [{}] procIncCls rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
			
			// 마감프로시저 오류 시
			if(!"00".equals(boIncPlanRequest.getRtn())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C303);
			}
				
			// 히스토리저장
			boIncPlanRequest.setTaskNm(TASK_NM);
			incPlanDao.procIncHist(boIncPlanRequest);
			
			log.info("## [{}] procIncHist rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
			
			// 손익집계 프로시저 호출
			boIncPlanRequest.setTaskNm(AGG_TASK_NM);
			incPlanDao.procIncUpdAggProc(boIncPlanRequest);
			
			log.info("## [{}] procIncUpdAggProc rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), boIncPlanRequest);
			
			// 손익집계프로시저 중 오류시
			if(boIncPlanRequest.getRtn().contains("ERROR")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C302);
			}
			
			return new BaseResponse(ResponseCode.C200);
				
		} catch (Exception e) {
			log.error("procIncSalesPlanCls Exception => {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();			
			return new BaseResponse(ResponseCode.C589);
		}
		
	}
	
	/**
	 * (계획)영업손익등록 삭제
	 */
	@Override
	public BaseResponse deleteIncSalesPlan(BoIncPlanRequest param) {
		
		try {
			
			incSalePlanDao.deleteIncPlanSalDet(param);
			incSalePlanDao.deleteIncPlanSal(param);
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("deleteIncSalPln error!! ==> {} ", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	

}
