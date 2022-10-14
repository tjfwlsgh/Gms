package com.lgl.gms.webapi.inc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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

import com.lgl.gms.webapi.cmm.dto.request.CommonCodeRequest;
import com.lgl.gms.webapi.cmm.dto.response.CommonCodeResponse;
import com.lgl.gms.webapi.cmm.persistence.dao.CommonCodeDao;
import com.lgl.gms.webapi.common.context.UserInfo;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.common.util.MessageUtil;
import com.lgl.gms.webapi.inc.dto.request.BoIncPlanRequest;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.dto.response.BoIncRetResponse;
import com.lgl.gms.webapi.inc.dto.response.IncExpRetExcelResponse;
import com.lgl.gms.webapi.inc.dto.response.IncItmDetResponse;
import com.lgl.gms.webapi.inc.persistence.dao.IncExpPlanDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncExpRetDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncRetDao;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpRetModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 수익비용 실적
 * @author jokim
 *
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncExpRetServiceImpl implements IncExpRetService {
	
	@Autowired
	private IncRetDao incRetDao;
	
	@Autowired
	private IncExpRetDao incExpRetDao;
	
	@Autowired
	private IncExpPlanDao incExpPlanDao;
	
	@Autowired
	private CommonCodeDao codeDao;
	
	/**
	 * (실적)영업외손익/판관비상세 목록 조회
	 */
	@Override
	public BaseResponse selectIncExpRetList(BoIncRetRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, incExpRetDao.selectIncExpRetList(param));		
			
		} catch (Exception e) {
			
			log.error(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * (실적)영업외손익/판관비상세 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		BoIncRetRequest req = (BoIncRetRequest)body;
		List<IncExpRetExcelResponse> incExpRetList;		// 엑셀 내용 담을 리스트 객체
		
		try {
			incExpRetList = ExcelUtil.getObjectList(file, IncExpRetExcelResponse::from, 3, null, req.getSheetNum());
			
			// 업로드시 선택한 월에 해당하는 내용만 필터링(5월24일 시연회 시 요구사항)			
			// 선택한 월 구하기
			Integer month = Integer.parseInt(StringUtils.defaultString(req.getIncYymm()).substring(4));
			incExpRetList = incExpRetList.stream()
					  .filter(itm -> !StringUtils.isBlank(itm.getIncMon()) && Integer.parseInt(itm.getIncMon()) == month)
					  .collect(Collectors.toList());
			
			// 손익항목조회(체크용)
			BoIncPlanRequest param = new BoIncPlanRequest();
			req.setTypCd(req.getTypCd());
			List<IncItmDetResponse> incItmDetList = incExpPlanDao.selectIncItmDetList(param);
			
			for(int idx = 0; idx < incExpRetList.size(); idx++ ) {
				IncExpRetExcelResponse expRet = incExpRetList.get(idx);
				// key값 저장
				expRet.setBoId(req.getBoId());
				expRet.setIncYymm(req.getIncYymm());
				expRet.setDefCl(req.getDefCl());
				expRet.setSeq(idx);
								
				// 항목체크
				checkItemDel(expRet, incItmDetList);				
			}
			
			// 엑셀등록시 해당 유형만 필터링
			incExpRetList = incExpRetList.stream().filter( itm -> req.getTypCd().equals(itm.getTypCdc()))
					  								.collect(Collectors.toList());

			// 영업실적 금액체크용(유형(STD_CD)TYP = 1 일 경우)
			// 항목체크시 에러 없을 경우
			if("1".equals(req.getTypCd())) {
				
				checkType1Amt(incExpRetList, req);				
			}

			return new BaseResponse(ResponseCode.C200, incExpRetList);
			
		} catch (Exception e) {
			log.error("IncExpRetServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	/**
	 * 판관비-영업실적 확인
	 * @param incExpRetList
	 * @param req
	 */
	private void checkType1Amt(List<IncExpRetExcelResponse> incExpRetList, BoIncRetRequest req) {
		
		// 그룹1 공통코드
		CommonCodeRequest codeReq = new CommonCodeRequest();
		codeReq.setCodeType("GRP01");
		
		List<CommonCodeResponse> grp1s = (List<CommonCodeResponse>) codeDao.selectCommonCodeList(codeReq);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("boId", req.getBoId());
		param.put("incYymm", req.getIncYymm());
		param.put("defCl", req.getDefCl());
		param.put("grp1s", grp1s);
		
		Map<String, Object> type1RetSales = incExpRetDao.selectType1SaleRetAmt(param);
		
		log.debug("##type1RetSales ==> {}", type1RetSales);
		
		for(CommonCodeResponse grp1 : grp1s) {
			
			// 체크할 항목 Id			
			// 인건비
			Integer chkLbrIncItmId = "1".equals(grp1.getStdCd()) ? 123 : ("2".equals(grp1.getStdCd()) ? 126 : 0);
			
			// 경비
			Integer chkExpIncItmId = "1".equals(grp1.getStdCd()) ? 124 : ("2".equals(grp1.getStdCd()) ? 127 : 0);			

			// 그룹1 금액 합산 체크
			// 인건비
			List<IncExpRetExcelResponse> lbrList = incExpRetList.stream()
					  	.filter( itm -> grp1.getStdCd().equals(itm.getGrp1Cdc()) 
							  		&& chkLbrIncItmId == itm.getIncItmId() )
					  	.collect(Collectors.toList());
			
			// 경비
			List<IncExpRetExcelResponse>  expList = incExpRetList.stream()
					  .filter( itm -> grp1.getStdCd().equals(itm.getGrp1Cdc()) 
							  			&& chkExpIncItmId == itm.getIncItmId() )
					  .collect(Collectors.toList());
			
			// 합계
			// 인건비
			Long lbrSum = Math.round(lbrList.stream().mapToDouble(i-> Double.parseDouble(i.getExpAmt())).sum());

			// 경비
			Long expSum = Math.round(expList.stream().mapToDouble(i-> Double.parseDouble(i.getExpAmt())).sum());

			// 금액 비교
			// 인건비
			BigDecimal lbrDecimal = type1RetSales == null ? BigDecimal.ZERO : (BigDecimal)type1RetSales.get("lbr_"+grp1.getStdCd());
			lbrDecimal = lbrDecimal == null ? BigDecimal.ZERO : lbrDecimal;
			Long lbrCost = lbrDecimal.setScale(0, RoundingMode.HALF_UP).longValue();

			if(lbrSum.longValue() != lbrCost) {
				for(IncExpRetExcelResponse expRet : lbrList) {
					if(expRet.getErrorColumns() == null || expRet.getErrorColumns().size() <= 0 ) {
						setError2(expRet, "message.misLbrCost", lbrCost, "expAmt");
					}						
				}
			}
			
			// 경비
			BigDecimal expDecimal = type1RetSales == null ? BigDecimal.ZERO : (BigDecimal)type1RetSales.get("exp_"+grp1.getStdCd());
			expDecimal = expDecimal == null ? BigDecimal.ZERO : expDecimal;
			Long expCost = expDecimal.setScale(0, RoundingMode.HALF_UP).longValue();
			log.debug("expCost ==> {}", expCost);
			if(expSum.longValue() != expCost) {

				for(IncExpRetExcelResponse expRet : expList) {
					if(expRet.getErrorColumns() == null || expRet.getErrorColumns().size() <= 0 ) {
						setError2(expRet, "message.misExpCost", expCost, "expAmt");
					}						
				}

			}				
					
			
		}
		
	}
	
	
	/**
	 * 손익 항목 상세 체크(유형,구분1,구분2,구분3)
	 * @param expPlan
	 */
	private void checkItemDel(IncExpRetExcelResponse expRet, List<IncItmDetResponse> incItmDetList) {
		
		if(StringUtils.isBlank(expRet.getTypCd()) || StringUtils.isBlank(expRet.getCl1Cd()) || StringUtils.isBlank(expRet.getCl2Cd())) {
			setError(expRet, "message.chkItm", "typCd", "cl1Cd" , "cl2Cd", "grp1Cd");
			return;			
		}
		
		// 유형입력값
		String inputTpCd =	StringUtils.defaultString(expRet.getTypCd());
		
		// 구분1 입력값
		String inputCl1Cd =	StringUtils.defaultString(expRet.getCl1Cd());
		
		// 구분2 입력값
		String inputCl2Cd =	StringUtils.defaultString(expRet.getCl2Cd());
		
		// 구분3 입력값
		String inputCl3Cd =	StringUtils.defaultString(expRet.getCl3Cd());
		
		// 그룹1 입력값
		String inputGrp1Cd = StringUtils.defaultString(expRet.getGrp1Cd());
		
		// 입력된 유형,구분1,구분2에 해당하는 유형상세
		List<IncItmDetResponse> fillteredItmList = incItmDetList.stream()
				  .filter(itm -> (inputTpCd.equals(itm.getTypCdNm()) || inputTpCd.equals(itm.getTypCdNmEng()) )			// 유형일치
						  			&& ( inputCl1Cd.equals(itm.getCl1CdNm()) || inputCl1Cd.equals(itm.getCl1CdNmEng()) )	// 구분1일치
						  			&& ( inputCl2Cd.equals(itm.getCl2CdNm()) || inputCl2Cd.equals(itm.getCl2CdNmEng()) )	// 구분2 일치
						  			&& ( itm.getGrp1Id() == null || itm.getGrp1Id() == 0 || inputGrp1Cd.equals(itm.getGrp1IdNm()) || inputGrp1Cd.equals(itm.getGrp1IdNmEng()) ) 	// 그룹1 일치
						  		)
				  .collect(Collectors.toList());

		// 검색된 내용이 없으면 에러 추가
		if(fillteredItmList == null || fillteredItmList.size() == 0) {
			setError(expRet, "message.chkItm", "typCd", "cl1Cd" , "cl2Cd", "grp1Cd");
			return;
		}
		
		// 구분3항목이 있어야하지만  입력하지 않은 경우
		
		for(IncItmDetResponse itm : fillteredItmList) {
			// 유형, 구분1, 구분2 코드 세팅
			expRet.setTypCdc(itm.getTypCd());		// 유형
			expRet.setCl1Cdc(itm.getCl1Cd());		// 구분1
			expRet.setCl2Cdc(itm.getCl2Cd());		// 구분2
			
			//String cl3CdNm = StringUtils.defaultString(itm.getCl3CdNm()
			
			// 구분3 항목이 존재하지만 입력이 안된경우..
			if( "".equals(inputCl3Cd) &&  !"".equals(StringUtils.defaultString(itm.getCl3CdNm()))) {
				setError(expRet, "message.chkItm", "cl3Cd");
				break;
			}
			
			// 그룹1 체크			
			// 그룹1이 존재하지만 입력하지 않은경우 에러
			if("".equals(inputGrp1Cd) && itm.getGrp1Id() != null && itm.getGrp1Id() != 0) {
				setError(expRet, "message.chkgrp1", "grp1Cd");
			}
				
		}
		
		// 수익금액, 비용금액, 그룹1 체크
		// 매칭데이터 한개만 뽑음
		IncItmDetResponse matchItm = null;
		
		if(!"".equals(inputCl3Cd)) {
			matchItm= fillteredItmList.stream()
					  .filter(itm -> inputCl3Cd.equals(itm.getCl3CdNm()) || inputCl3Cd.equals(itm.getCl3CdNmEng()))
					  .findAny()
					  .orElse(null);
			expRet.setCl3Cdc(matchItm.getCl3Cd());
		} else {
			matchItm = fillteredItmList.get(0);
		}
			
		if(matchItm == null ) {
			setError(expRet, "message.chkItm", "typCd", "cl1Cd" , "cl2Cd");	
		} else {
			String rvnYn = matchItm.getRvnYn();
			String expYn = matchItm.getExpYn();
			
			// 수익 YN == Y
			if("Y".equals(rvnYn)) {
				String rvnAmt = StringUtils.isBlank(expRet.getRvnAmt()) ? expRet.getExpAmt() : expRet.getRvnAmt();
				if(StringUtils.isBlank(rvnAmt)) {
					setError(expRet, "message.chkRvnAmt", "rvnAmt");
				} else {
					expRet.setRvnAmt(rvnAmt);
					expRet.setExpAmt(null);
				}					
			} else if("Y".equals(expYn )) {
				String expAmt = StringUtils.isBlank(expRet.getExpAmt()) ? expRet.getRvnAmt() : expRet.getExpAmt();
				if(StringUtils.isBlank(expAmt)) {
					setError(expRet, "message.chkExpAmt", "expAmt");
				} else {
					expRet.setExpAmt(expAmt);
					expRet.setRvnAmt(null);
				}					
			}
			
			// 그룹1 없어야하는 데이터이지만 엑셀에 값이 들어있을 경우 삭제시킴
			if(!"".equals(inputGrp1Cd) && (matchItm.getGrp1Id() == null || matchItm.getGrp1Id() == 0)) {
				expRet.setGrp1Cd("");
			}	
			// 항목세팅
			expRet.setIncItmId(matchItm.getIncItmId());
			// 상세항목 세팅
			expRet.setIncItmDetId(matchItm.getIncItmDetId());
			// AGG_YN 세팅
			expRet.setAggYn(matchItm.getAggYn());			
			// 그룹코드 세팅
			expRet.setGrp1Cdc(matchItm.getGrp1Cd());
		}
		
	}
	
	/**
	 * 에러 추가
	 * @param expRet
	 * @param msgKey
	 * @param cols
	 */
	private void setError(IncExpRetExcelResponse expRet, String msgKey, String... cols) {		
		setError2(expRet, msgKey, null, cols);
	}
	
	private void setError2(IncExpRetExcelResponse expRet, String msgKey, Long cost, String... cols) {		
		String errorMsg = expRet.getErrorMsg();
		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
		
		if(cost != null) {
			DecimalFormat dformat = new DecimalFormat("#,###");
			String coststr = dformat.format(cost);
			expRet.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null)+coststr);
		} else {
			expRet.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
		}
				
		// 에러컬럼 추가
		List<String> errorColumns = expRet.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		expRet.setErrorColumns(errorColumns);

	}
	
	/**
	 * 비용실적 저장
	 */
	@Override
	public BaseResponse saveIncExpRet(List<BoIncExpRetModel> incExpRets, BoIncRetRequest param) {
		
		if(incExpRets == null || incExpRets.size() == 0) {
			return new BaseResponse(ResponseCode.C781);
		}

		Integer	boId = incExpRets.get(0).getBoId();
		String	incYymm = StringUtils.defaultString(incExpRets.get(0).getIncYymm());
		String	defCl = StringUtils.defaultString(incExpRets.get(0).getDefCl());

		param.setBoId(boId);
		param.setIncYymm(incYymm);
		param.setDefCl(defCl);
		
		try {			

			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			incExpRetDao.deleteIncExpRet(param);
			
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)incExpRets.size() / MAX_ROW);					
			Map<String, Object> itemMap = null;
			
			for(int idx = 0; idx < loopCnt; idx++ ) {
				itemMap = new HashedMap<String, Object>();
				itemMap.put("boId",  boId);
				itemMap.put("incYymm",  incYymm);
				itemMap.put("defCl",  defCl);
						
				int startIdx = idx*MAX_ROW;
				int endIdx = incExpRets.size() < startIdx+MAX_ROW ? incExpRets.size() : startIdx+MAX_ROW;

				List<BoIncExpRetModel> temIntExps = incExpRets.subList(startIdx, endIdx);
				itemMap.put("itemList", temIntExps);
				incExpRetDao.insertIncExpRet(itemMap);
			}
			
			// 손익집계 프로시저 호출
			BoIncRetResponse incRet = incRetDao.selectIncRet(param);
			if("Y".equals(incRet.getRetCls2())) {
				param.setJobCl(JOB_CL);
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
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveIncExpPlan ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
		}	
	}

	/**
	 * (실적)영업외손익/판관비상세 삭제
	 */
	@Override
	public BaseResponse deleteIncExpRet(BoIncRetRequest param) {
		
		try {
			
			incExpRetDao.deleteIncExpRet(param);
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			log.error("deleteIncExpRet error!! ==> {} ", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
			
		}
	}

	/**
	 * (실적)영업외손익/판관비상세 마감
	 */
	@Override
	public BaseResponse procIncExpRetCls(BoIncRetModel paramModel, String pgmId) {
		
		try {
			
			// 마감Flag->Y(ret_cls2)			
			incExpRetDao.updateIncExpRetCls(paramModel);
			
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
			incRetDao.procIncUpdAggRet(param);
			log.info("## [{}] procIncUpdAggRet rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 손익집계프로시저 중 오류시
			if(param.getRtn().contains("ERROR")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C302);
			}
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			log.error("procIncExpRetCls error ==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	
	/**
	 * 해당 (실적)판관비에 대한 매출조회
	 */
	@Override
	public BaseResponse selectIncSalRetByExpList(BoIncRetRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, incExpRetDao.selectIncSalRetByExpList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncSalRetByExpList error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	


}
