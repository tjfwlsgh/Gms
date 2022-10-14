package com.lgl.gms.webapi.inc.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.common.util.MessageUtil;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncRetResponse;
import com.lgl.gms.webapi.inc.dto.response.IncSaleRetExcelResponse;
import com.lgl.gms.webapi.inc.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.inc.dto.response.TccResponse;
import com.lgl.gms.webapi.inc.dto.response.TccValResponse;
import com.lgl.gms.webapi.inc.persistence.dao.IncPlanDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncRetDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncSaleRetDao;
import com.lgl.gms.webapi.inc.persistence.model.BoCustModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetSalModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncSalesRetServiceImpl implements IncSalesRetService {
	
	@Autowired
	private IncRetDao incRetDao;
	
	@Autowired
	private IncPlanDao incPlanDao;
	
	@Autowired
	private IncSaleRetDao incSaleRetDao;
	
	/**
	 * (실적)영업손익등록 목록 조회
	 */
	@Override
	public BaseResponse selectIncSaleRetList(BoIncRetRequest param) {

		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("detTotCnt", incSaleRetDao.selectIncSaleRetDetCount(param));			
			returnMap.put("incSalRets", incSaleRetDao.selectIncSaleRetList(param));

			return new BaseResponse(ResponseCode.C200, returnMap);	
			
		} catch (Exception e) {
			
			log.error("selectIncSalesPlanList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}	

	/**
	 * (실적)영업손익등록 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		BoIncRetRequest req = (BoIncRetRequest)body;
		List<IncSaleRetExcelResponse> incSaelsRetList;		// 엑셀 내용 담을 리스트 객체
		
		try {
			incSaelsRetList = ExcelUtil.getObjectList(file, IncSaleRetExcelResponse::from, 3, null, req.getSheetNum());
			
			// 업로드시 선택한 월에 해당하는 내용만 필터링(5월24일 시연회 시 요구사항)			
			// 선택한 월 구하기
			Integer month = Integer.parseInt(StringUtils.defaultString(req.getIncYymm()).substring(4));
			incSaelsRetList = incSaelsRetList.stream()
					  .filter(itm -> !StringUtils.isBlank(itm.getIncMon()) && Integer.parseInt(itm.getIncMon()) == month)
					  .collect(Collectors.toList());
			
			List<BoCustModel> boCustList = incPlanDao.selectBoCustList(req.getBoId());	// 거래처체크용
			List<SvcTypResponse> svcTypList = incPlanDao.selectIncSvcTypList();		// 서비스체크용
			List<TccResponse> tccCodeList = incPlanDao.selectIncPlanTccList();		// 그룹체크용
			
			for( int idx = 0; idx < incSaelsRetList.size(); idx++ ) {
				IncSaleRetExcelResponse salesRet = incSaelsRetList.get(idx);
				// 헤더정보 저장
				salesRet.setBoId(req.getBoId());
				salesRet.setIncYymm(req.getIncYymm());
				salesRet.setDefCl(req.getDefCl());
				
				// 금액계산
				// 판관비 : 인건비+경비
				// 영업이익 : 매출액 - 매출원가 - 판관비(인건비+경비)
				if( salesRet.getErrorColumns() == null || (!salesRet.getErrorColumns().contains("lbrCost") && !salesRet.getErrorColumns().contains("expAmt")) ) {
					// ** 판매관리비 ** //
					BigDecimal seaeAmt = new BigDecimal(salesRet.getLbrCost()).add(new BigDecimal(salesRet.getExpAmt()));
					salesRet.setSeaeAmt(seaeAmt.toString());
					
					if(salesRet.getErrorColumns() == null || (!salesRet.getErrorColumns().contains("salAmt") && !salesRet.getErrorColumns().contains("salCst")) ) {
						// ** 영업이익 ** //
						BigDecimal ifoAmt = new BigDecimal(salesRet.getSalAmt()).subtract(new BigDecimal(salesRet.getSalCst()))
												.subtract(new BigDecimal(salesRet.getLbrCost())).subtract(new BigDecimal(salesRet.getExpAmt()));
						salesRet.setIfoAmt(ifoAmt.toString());
					}					
				}
				
				// 거래처 체크
				checkBoCust(salesRet, boCustList);								
				// 그룹체크
				checkGrpCd(salesRet,tccCodeList);
				// 서비스 체크
				checkSvcTyp(salesRet, svcTypList);
			}

			return new BaseResponse(ResponseCode.C200, incSaelsRetList);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("IncSalesRetServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	/**
	 * 거래처 유효성검사
	 * @param salesPlan
	 * @param boId
	 * @throws Exception
	 */
	private void checkBoCust(IncSaleRetExcelResponse salesRet, List<BoCustModel> boCustList) throws Exception {
			
		
		String inputCustCd = StringUtils.defaultString(salesRet.getBoCustCd()); // 거래처코드
		String inputCustNm= StringUtils.defaultString(salesRet.getCustNm());	// 거래처명 

		BoCustModel boCust = boCustList.stream()
				  .filter(cust -> inputCustCd.equals(cust.getBoCustCd())
						  	|| inputCustNm.equals(cust.getCustNm() ) || inputCustNm.equals(cust.getCustNmEng()))
				  .findAny()
				  .orElse(null);
		
		if(boCust != null) {
			
			salesRet.setBoCustCd(boCust.getBoCustCd());
			if(!boCust.getCustNm().equals(inputCustNm) && !inputCustNm.equals(boCust.getCustNmEng())) {
				salesRet.setCustNm(boCust.getCustNm());
			}
			
		} else {
			setError(salesRet, "message.chkcust", "boCustCd");
		}
		
	}
	
	
	
	/**
	 * 서비스유형 유효성 검사
	 * @param salesPlan
	 * @throws Exception
	 */
	private void checkSvcTyp(IncSaleRetExcelResponse salesRet, List<SvcTypResponse> svcTypList) throws Exception {
				
		String inputSvcNm = StringUtils.defaultString(salesRet.getSvcTyp());
		String inputGrp1Nm= StringUtils.defaultString(salesRet.getGrp1Cd());
		
		SvcTypResponse svcTypData = null;
		
		if( salesRet.getErrorColumns() == null || (!salesRet.getErrorColumns().contains("grp1Cd")) ) {
			svcTypData = svcTypList.stream()
					  .filter(svc -> (inputGrp1Nm.equals(svc.getStdCdNm()) || inputGrp1Nm.equals(svc.getStdCdNmEng())) &&
							  	( inputSvcNm.equals(svc.getSvcNm()) || inputSvcNm.equals(svc.getSvcNmEng())
							  			|| inputSvcNm.equals(svc.getSvcSnm()) || inputSvcNm.equals(svc.getSvcSnmEng()) )
							  )
					  .findAny()
					  .orElse(null);			
		}	
	
		if(svcTypData != null) {
			
			salesRet.setSvcTypc(svcTypData.getSvcTyp());
//			salesRet.setGrp1Cdc(svcTypData.getTccvId());
			
//			if(!svcTypData.getStdCdNm().equals(inputGrp1Nm) && !svcTypData.getStdCdNmEng().equals(inputGrp1Nm)) {
//				salesRet.setGrp1Cd(svcTypData.getStdCdNm());
//			}
			
		} else {			
			setError(salesRet, "message.chksvc", "svcTyp");			
		}

    }

	/**
	 * 그룹1, 2,3 유효성검사
	 * @param salesPlan
	 * @throws Exception
	 */
    private void checkGrpCd(IncSaleRetExcelResponse salesRet, List<TccResponse> tccCodeList) throws Exception {
    	
    	String inputGrp1Nm = StringUtils.defaultString(salesRet.getGrp1Cd());
    	String inputGrp2Nm = StringUtils.defaultString(salesRet.getGrp2Cd());
    	String inputGrp3Nm = StringUtils.defaultString(salesRet.getGrp3Cd());
    	
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
    		salesRet.setGrp1Cdc(grp1Tccv.getTccvId());
		} else {
			setError(salesRet, "message.chkgrp1", "grp1Cd");
		}
	
		if(grp2Tccv != null) {
			salesRet.setGrp2Cdc(grp2Tccv.getTccvId());
		} else {			
			setError(salesRet, "message.chkgrp2", "grp2Cd");
		}
		
		if(grp3Tccv != null) {
			salesRet.setGrp3Cdc(grp3Tccv.getTccvId());
		} else {
//			setError(salesRet, "message.chkgrp3", "grp3Cd");
		}

    }
    
    /**
	 * 에러 추가
	 * @param expPlan
	 * @param msgKey
	 * @param cols
	 */
	private void setError(IncSaleRetExcelResponse salesRet, String msgKey, String... cols) {		
		String errorMsg = salesRet.getErrorMsg();
		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
		salesRet.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
		
		// 에러컬럼 추가
		List<String> errorColumns = salesRet.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		salesRet.setErrorColumns(errorColumns);
	}
    
	/**
	 * (실적)영업손익등록 저장
	 */
	@Override
	public BaseResponse saveIncSalesRet(List<BoIncRetSalModel> retSales, Object user) {
		
		if(retSales == null || retSales.size() == 0) {
			return new BaseResponse(ResponseCode.C781);
		}
					
		Integer	boId = retSales.get(0).getBoId();
		String	incYymm = StringUtils.defaultString(retSales.get(0).getIncYymm());
		String	defCl = retSales.get(0).getDefCl();
		
		BoIncRetRequest param = new BoIncRetRequest();
		param.setBoId(boId);
		param.setIncYymm(incYymm);
		param.setDefCl(defCl);			
		
		try {
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			incSaleRetDao.deleteIncRetSal(param);
			
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)retSales.size() / MAX_ROW);					
			Map<String, Object> itemMap = null;
			
			for(int idx = 0; idx < loopCnt; idx++ ) {
				itemMap = new HashedMap<String, Object>();
				itemMap.put("boId",  boId);
				itemMap.put("incYymm",  incYymm);
				itemMap.put("defCl",  defCl);
						
				int startIdx = idx*MAX_ROW;
				int endIdx = retSales.size() < startIdx+MAX_ROW ? retSales.size() : startIdx+MAX_ROW;

				List<BoIncRetSalModel> temIntExps = retSales.subList(startIdx, endIdx);
				itemMap.put("itemList", temIntExps);
				incSaleRetDao.insertIncRetSal(itemMap);
			}
						
			// 매출실적 상세 프로시저 호출
			incSaleRetDao.procIncSalRetDet(param);
			log.info("## [{}] procIncSalRetDet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 상세 프로시저 오류 시
			if(param.getRtn().contains("ERROR")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C301);
			}
			
			// 손익집계 프로시저 호출
			BoIncRetResponse incRet = incRetDao.selectIncRet(param);
			if("Y".equals(incRet.getRetCls1())) {
				param.setJobCl(JOB_CL);
				param.setDefCl(defCl);
				incRetDao.procIncUpdAggRet(param);
				log.info("## [{}] procIncUpdAggRet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
				
				// 손익집계프로시저 중 오류시
				if(param.getRtn().contains("ERROR")) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return new BaseResponse(ResponseCode.C302);
				}
			} else {
				// 마감 = N 일경우
				// 마감초기화프로시저(마감테이블에 초기화 데이터 저장) - 최초저장시에 INSERT됨
				param.setTaskNm(CLS_INT_TASK_NM);
				param.setIncYy(incYymm.substring(0, 4));
				incRetDao.procIncRetClsInt(param);
				log.info("## [{}] procIncRetClsInt rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
				
			}
			
			return new BaseResponse(ResponseCode.C200);
						
		} catch (Exception e) {
			log.error("saveIncSalesRet error ==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}	
		
	}
	

    /**
     * (실적)영업손익등록 마감
     */
	@Override
	public BaseResponse procIncSalRetCls(BoIncRetModel paramModel, String pgmId) {
		
		try {
			incSaleRetDao.updateIncSalRetCls(paramModel);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("updateIncSalRetCls error ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
		}		
		
		try {
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
			log.info("## [{}] procIncRetCls rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 마감프로시저 오류 시
			if(!"00".equals(param.getRtn())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C303);
			}

			// 손익집계 프로시저 호출
			param.setJobCl(JOB_CL);
			param.setDefCl(defCl);
			incRetDao.procIncUpdAggRet(param);
			log.info("## [{}] procIncUpdAggRet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 손익집계프로시저 중 오류시
			if(param.getRtn().contains("ERROR")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C302);
			}

			return new BaseResponse(ResponseCode.C200);
				
		} catch (Exception e) {
			log.error("procIncSalRetCls error ==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();	
			return new BaseResponse(ResponseCode.C589);
		}
		
	}
	
	/**
	 * (실적)영업손익등록 삭제
	 */
	@Override
	public BaseResponse deleteIncSalesRet(BoIncRetRequest param) {
		
		try {
			
			incSaleRetDao.deleteIncRetSalDet(param);
			incSaleRetDao.deleteIncRetSal(param);
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			log.error("deleteIncSalesRet error!! ==> {} ", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	

}
