package com.lgl.gms.webapi.fin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.cmm.persistence.dao.BoDao;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.common.util.MessageUtil;
import com.lgl.gms.webapi.fin.dto.request.AccountsReceivableReuqest;
import com.lgl.gms.webapi.fin.dto.response.AccountsReceivableExcelResponse;
import com.lgl.gms.webapi.fin.dto.response.AccountsReceivableResponse;
import com.lgl.gms.webapi.fin.persistence.dao.AccountsReceivableDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : AccountsReceivableServiceImpl.java
 * @Date        : 22.04.19
 * @Author      : hj.Chung
 * @Description : ServiceImpl
 * @History     : 미수채권 미수채권 정보 등록 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class AccountsReceivableServiceImpl implements AccountsReceivableService {
	
	@Autowired
	private AccountsReceivableDao arDao;
	
	@Autowired
	private BoDao boDao;
	
	/** 
	 * 엑셀 업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		AccountsReceivableReuqest req = (AccountsReceivableReuqest)body;
		List<AccountsReceivableExcelResponse> ArList;	// 엑셀 내용 담을 리스트 객체
		
		try {
			ArList = ExcelUtil.getObjectList(file, AccountsReceivableExcelResponse::from, 3, null, req.getSheetNum());
			// 2 : 상선 제외
			// 5 : 상선 포함
			
			List<AccountsReceivableExcelResponse> subList = new ArrayList<AccountsReceivableExcelResponse>();
			
			// 법인체크용
			List<BoResponse> boList = (List<BoResponse>) boDao.selectAllBoList(new BoRequest());
			
//			int endIdx = 0;
			
			for( int idx = 0; idx < ArList.size(); idx++ ) {
				AccountsReceivableExcelResponse analysis = ArList.get(idx);
				
				// row에 데이터가 '구분'이나 '합계'가 있는 경우 pass 하기
				if (!StringUtils.isBlank(analysis.getTrrtNm()) && analysis.getTrrtNm().replaceAll("\\s+","").equals("구분")) {
					idx = idx + 2; 
					continue;
				} else if (!StringUtils.isBlank(analysis.getTrrtNm()) && analysis.getTrrtNm().replaceAll("\\s+","").equals("합계")) {
					continue;
				}
				
				// 미회수 채권 금액이 모두 0이거나 비어있다면 pass
			// 2022.07.27 5차 수정사항 - 0이여도 저장되도록 주석
//				if (StringUtils.isBlank(analysis.getRcvbBndsAmt30()) 
//						&& StringUtils.isBlank(analysis.getRcvbBndsAmt60())
//						&& StringUtils.isBlank(analysis.getRcvbBndsAmt90())
//						&& StringUtils.isBlank(analysis.getRcvbBndsAmt91())) {
//					saveName(ArList, analysis, idx);
//					continue;
//				}
				
				// 지역 및 법인 명 저장
				// 2022.07.27 주석 jokim - 이전법인을 가지고 오지 않도록..
//				saveName(ArList, analysis, idx);
				
				// 2022.07.27 5차 수정사항관련
				// 법인명이 없을 경우 pass
				if (StringUtils.isBlank(analysis.getBoNm())) {
					continue;
				}
				
				// 2022.07.28 jokim 법인체크(단축명/영문 모두 체크)
				String boNm = StringUtils.defaultString(analysis.getBoNm()).replaceAll(" ", "");
				BoResponse boRes = boList.stream()
				  .filter(bo -> boNm.equals(bo.getBoNmKo()) || boNm.equals(bo.getBoNmEng())
						  	|| boNm.equals(bo.getBoSnm()) || boNm.equals(bo.getBoSnmEng()) )
				  .findAny()
				  .orElse(null);
				
				if(boRes == null) {
					setError(analysis, "message.chkBoNm", "boNm");
				}
				
				// rcvb_bnds_amt_*
				if (StringUtils.isBlank(analysis.getRcvbBndsAmt30()) ) {
					analysis.setRcvbBndsAmt30("0");
				}
				if (StringUtils.isBlank(analysis.getRcvbBndsAmt60())) {
					analysis.setRcvbBndsAmt60("0");
				}
				if (StringUtils.isBlank(analysis.getRcvbBndsAmt90())) {
					analysis.setRcvbBndsAmt90("0");
				}
				if (StringUtils.isBlank(analysis.getRcvbBndsAmt91())) {
					analysis.setRcvbBndsAmt91("0");
				}
				
				// 마지막 row 있는 idx 저장
//				if (!analysis.getTrrtNm().isEmpty() && analysis.getBoNm().isEmpty()) {
//					endIdx = idx;
//				}
				
//				if(endIdx > passIdx) {
//					subList = ArList.subList(passIdx, endIdx);
//				}
				subList.add(analysis);
			}
			
			return new BaseResponse(ResponseCode.C200, subList);
			
		} catch (Exception e) {
			log.error("AccountsReceivableServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	/**
	 * 에러 추가
	 * @param expPlan
	 * @param msgKey
	 * @param cols
	 */
	private void setError(AccountsReceivableExcelResponse excelRes, String msgKey, String... cols) {		
		String errorMsg = excelRes.getErrorMsg();
		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
		
		excelRes.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
				
		// 에러컬럼 추가
		List<String> errorColumns = excelRes.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		excelRes.setErrorColumns(errorColumns);
	}
	
	
	/**
	 * 지역과 법인의 이름을 저장하는 메소드
	 * @param ArList
	 * @param analysis
	 * @param idx
	 */
	private void saveName(List<AccountsReceivableExcelResponse> ArList, AccountsReceivableExcelResponse analysis, int idx) {
		// idx번의 지역이 null이면 idx-1의 지역 가져오기
//		if (StringUtils.isBlank(analysis.getTrrtNm()) && idx != 0) {
//			analysis.setTrrtNm(ArList.get(idx - 1).getTrrtNm());
//		} 
		
		// 법인
		// 2022.07.27 주석 jokim - 이전법인을 가지고 오지 않도록..
//		if (StringUtils.isBlank(analysis.getBoNm()) && idx != 0) {
//			analysis.setBoNm(ArList.get(idx - 1).getBoNm());
//		} 
	}
	
	
	/**
	 * 상선 제외
	 */

	@Override
	public BaseResponse getExcludingMarineList(AccountsReceivableReuqest paramDto) {
		try {
			List<AccountsReceivableReuqest> list = (List<AccountsReceivableReuqest>)arDao.selectExcludingMarineList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.error("getExcludingMarineList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse doSaveExcludingExcel(Map<String, Object> exIUDObj) {
		try {
			List<AccountsReceivableResponse> arList = (List<AccountsReceivableResponse>)exIUDObj.get("excludingList");
			
			if (arList == null || arList.size() == 0) {
				return new BaseResponse(ResponseCode.C589);
			}
			
			AccountsReceivableReuqest param = new AccountsReceivableReuqest();
			param.setArYymm(exIUDObj.get("arYymm").toString());
			param.setIncclCd(exIUDObj.get("frmClssCd").toString());
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			arDao.deleteAccounts(param);
			
			exIUDObj.put("arList", arList);
			
			arDao.insertAccountsExcel(exIUDObj);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.error("doSaveArExcel==> {}", e.getMessage());
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}	
	}
	
	/**
	 * 상선 포함
	 */

	@Override
	public BaseResponse getIncludingMarineList(AccountsReceivableReuqest paramDto) {
		try {
			List<AccountsReceivableReuqest> list = (List<AccountsReceivableReuqest>)arDao.selectIncludingMarineList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.error("getIncludingMarineList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse doSaveIncludingExcel(Map<String, Object> inIUDObj) {
		try {
			List<AccountsReceivableResponse> arList = (List<AccountsReceivableResponse>)inIUDObj.get("includingList");
			
			if (arList == null || arList.size() == 0) {
				return new BaseResponse(ResponseCode.C589);
			}
			
			AccountsReceivableReuqest param = new AccountsReceivableReuqest();
			param.setArYymm(inIUDObj.get("arYymm").toString()); 
			param.setIncclCd(inIUDObj.get("frmClssCd").toString());
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			arDao.deleteAccounts(param);
			
			inIUDObj.put("arList", arList);
			
			arDao.insertAccountsExcel(inIUDObj);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.error("doSaveArExcel==> {}", e.getMessage());
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}	
	}



	@Override
	public BaseResponse delectAccountsRow(AccountsReceivableReuqest paramDto) {
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getArYymm()) 
					|| StringUtils.isBlank(paramDto.getBoId().toString())
					|| StringUtils.isBlank(paramDto.getIncclCd())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = arDao.delectAccountsRow(paramDto);
			
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

}
