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
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.response.ArIncreAnalysisExcelResponse;
import com.lgl.gms.webapi.fin.dto.response.ArIncreAnalysisResponse;
import com.lgl.gms.webapi.fin.dto.response.BoCustResponse;
import com.lgl.gms.webapi.fin.persistence.dao.ArIncreAnalysisDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : ArIncreAnalysisServiceImpl.java
 * @Date        : 22.04.12
 * @Author      : hj.Chung
 * @Description : ServiceImpl
 * @History     : 미수채권 증감 현황 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class ArIncreAnalysisServiceImpl implements ArIncreAnalysisService {
	
	@Autowired
	private ArIncreAnalysisDao aiaDao;
	
	@Autowired
	private BoDao boDao;
	
	@Override
	public BaseResponse getArIncreAnalysisList(ArIncreAnalysisRequest paramDto) {

		try {
			List<ArIncreAnalysisRequest> list = (List<ArIncreAnalysisRequest>)aiaDao.selectArIncreAnalysisList(paramDto);
			
			log.debug("ArIncreAnalysisServiceImpl > getArIncreAnalysisList =====> {}", list);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			log.error("getArIncreAnalysisList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}

	
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		ArIncreAnalysisRequest req = (ArIncreAnalysisRequest)body;
		List<ArIncreAnalysisExcelResponse> ArList;	// 엑셀 내용 담을 리스트 객체
		
		try {
			ArList = ExcelUtil.getObjectList(file, ArIncreAnalysisExcelResponse::from, 0, null, req.getSheetNum());
			// 8 : 미주 지역
			// 11 : 구주 지역
			
			List<ArIncreAnalysisExcelResponse> subList = new ArrayList<ArIncreAnalysisExcelResponse>();	
			
			// 법인체크용
			List<BoResponse> boList = (List<BoResponse>) boDao.selectAllBoList(new BoRequest());
			
			for( int idx = 0; idx < ArList.size(); idx++ ) {
				
				ArIncreAnalysisExcelResponse analysis = ArList.get(idx);
				
				String trrNm = StringUtils.defaultString(analysis.getTrrtNm());
				// row에 데이터가 '구분'이 있는 경우 pass 하기
				if (trrNm.replaceAll("\\s+","").equals("구분")) {
					// 엑셀에서 구분은 총 2개의 row를 차지한다.
					// index + 1를 하여 구분에 해당하는 2개의 row 패스
					idx = idx + 1;
					continue;
				}
				
				// row에 데이터가 '합계'가 있으면 해당 row 패스
				if (trrNm.replaceAll("\\s+","").equals("합계")) {
					continue;
				}
				
				// 거래처명이 없다면 pass
				if (StringUtils.isBlank(analysis.getCustNm())) {
					saveName(ArList, analysis, idx);
					continue;
				}
				
				// 미회수 채권 금액이 모두 0이거나 비어있다면 pass
				if (StringUtils.isBlank(analysis.getRcvbBndsAmt30()) 
						&& StringUtils.isBlank(analysis.getRcvbBndsAmt60())
						&& StringUtils.isBlank(analysis.getRcvbBndsAmt90())
						&& StringUtils.isBlank(analysis.getRcvbBndsAmt91())) {
					saveName(ArList, analysis, idx);
					continue;
				}
				
				// 지역 및 법인 명 저장
				saveName(ArList, analysis, idx);
				
				// 법인에 해당하는 지역명 가져오기
				// 2022.07.26 5차 수정사항 jokim ( 지역확인 X )
//				String selectTrrt = StringUtils.defaultString(aiaDao.selectTrrtByBoNm(analysis.getBoNm().replaceAll("(\r\n|\r|\n|\n\r|\\p{Z}|\\t)", "")));
				// 엑셀의 지역 이름과 비교하기 위해 '지역' 문자열 제거
//				selectTrrt = (selectTrrt.substring(0, selectTrrt.length() - 2));
								
				// 해당 지역에 해당하는 법인이 맞는 지 확인하기
				// 맞으면 upload / 아니면 pass
			// 2022.07.26 법인명으로 법인ID를  찾을 수 없을 경우 패스되므로 패스되는 부분 주석 jokim 
//				if (!(StringUtils.defaultString(analysis.getTrrtNm())).equals(selectTrrt)) {
//					continue;
//				}
				
				// ** 2022.07.28 jokim 법인체크(단축명/영문 모두 체크)
				String boNm = StringUtils.defaultString(analysis.getBoNm()).replaceAll("(\r\n|\r|\n|\n\r|\\p{Z}|\\t)", "");
		
				BoResponse boRes = boList.stream()
				  .filter(bo -> boNm.equals(bo.getBoNmKo()) || boNm.equals(bo.getBoNmEng())
						  	|| boNm.equals(bo.getBoSnm()) || boNm.equals(bo.getBoSnmEng()) )
				  .findAny()
				  .orElse(null);
				
				if(boRes == null) {
					setError(analysis, "message.chkBoNm", "boNm");
				}
				
				// 청구기간
				String std = (analysis.getBllPrd().split("~"))[0].toString().trim().replace(".", "");
				analysis.setBllStd(std);
				
				if (analysis.getBllPrd().contains("~")) {
					String etd = (analysis.getBllPrd().split("~"))[1].toString().trim().replace(".", "");
					
					// 청구기간(E)의 길이가 2인 경우 청구기간(S)의 년도 넣기
					if(etd.length() == 2) {
						String bllEtd = analysis.getBllPrd().substring(0, 4);
						analysis.setBllEtd(bllEtd.concat(etd));
					}
					else {
						analysis.setBllEtd(etd);
					}
				}
				
				// custNm으로 거래처 코드 찾기
				BoCustResponse boCust = (BoCustResponse) aiaDao.selectBoCustByCustNm(analysis.getCustNm());
				
				if(boCust != null) {
					analysis.setBoCustCd(boCust.getBoCustCd());
				} else {
					analysis.setBoCustCd("0");
				}
				
				subList.add(analysis);
				
				
			}
			
			// 합계 계산
			for (int i = 0; i < subList.size(); i++) {
				ArIncreAnalysisExcelResponse analysis = subList.get(i);
				
				// rcvb_bnds_amt_*
				if (analysis.getRcvbBndsAmt30().isEmpty()) {
					analysis.setRcvbBndsAmt30("0");
				}
				if (analysis.getRcvbBndsAmt60().isEmpty()) {
					analysis.setRcvbBndsAmt60("0");
				}
				if (analysis.getRcvbBndsAmt90().isEmpty()) {
					analysis.setRcvbBndsAmt90("0");
				}
				if (analysis.getRcvbBndsAmt91().isEmpty()) {
					analysis.setRcvbBndsAmt91("0");
				}
				if (analysis.getRcvbBndsTamt().isEmpty()) {
					int sum = 0;
					int rbAmt30 = Integer.parseInt(analysis.getRcvbBndsAmt30());
					int rbAmt60 = Integer.parseInt(analysis.getRcvbBndsAmt60());
					int rbAmt90 = Integer.parseInt(analysis.getRcvbBndsAmt90());
					int rbAmt91 = Integer.parseInt(analysis.getRcvbBndsAmt91());
					
					sum = rbAmt30 + rbAmt60 + rbAmt90 + rbAmt91;
					analysis.setRcvbBndsTamt(Integer.toString(sum));
				}
			}
			
			return new BaseResponse(ResponseCode.C200, subList);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ArIncreAnalysis excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}
	

	/**
	 * 지역과 법인의 이름을 저장하는 메소드
	 * @param ArList
	 * @param analysis
	 * @param idx
	 */
	private void saveName(List<ArIncreAnalysisExcelResponse> ArList, ArIncreAnalysisExcelResponse analysis, int idx) {
		// idx번의 지역이 null이면 idx-1의 지역 가져오기
		if (StringUtils.isBlank(analysis.getTrrtNm()) && idx != 0) {
			analysis.setTrrtNm(ArList.get(idx - 1).getTrrtNm());
		} 
		
		// 법인
		if (StringUtils.isBlank(analysis.getBoNm()) && idx != 0) {
			analysis.setBoNm(ArList.get(idx - 1).getBoNm());
		} 
	}
	
	/**
	 * 에러 추가
	 * @param expPlan
	 * @param msgKey
	 * @param cols
	 */
	private void setError(ArIncreAnalysisExcelResponse analysis, String msgKey, String... cols) {		
		String errorMsg = analysis.getErrorMsg();
		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
		
		analysis.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
				
		// 에러컬럼 추가
		List<String> errorColumns = analysis.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		analysis.setErrorColumns(errorColumns);
	}


	/**
	 * 엑셀 저장 
	 */

	@Override
	public BaseResponse doSaveArExcel(Map<String, Object> IUDObj) {
		try {
			List<ArIncreAnalysisResponse> arList = (List<ArIncreAnalysisResponse>) IUDObj.get("arList");
			// arList.get(i)는 linkedHashMap이므로 새로운 Map에 담아줌
			Map<String, Object> map =  (Map<String, Object>) arList.get(0);
			
			if (arList == null || arList.size() == 0) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			ArIncreAnalysisRequest param = new ArIncreAnalysisRequest();
			param.setArYymm(IUDObj.get("arYymm").toString()); 
//			param.setTrrtNm(map.get("trrtNm").toString());
			
			for (int i = 0; i < arList.size(); i++) {
				Map<String, Object> idxMap = (Map<String, Object>) arList.get(i);
				
				String currentBoNm = ((String)idxMap.get("boNm")).replaceAll("(\r\n|\r|\n|\n\r|\\p{Z}|\\t)", "");

				String preBoNm = "";
				
				if (i > 0) {
					preBoNm = ((String)(((Map<String, Object>)arList.get(i - 1)).get("boNm"))).replaceAll("(\r\n|\r|\n|\n\r|\\p{Z}|\\t)", "");
				}
				
				log.debug("currentBoNm ===> {}", currentBoNm);
				log.debug("preBoNm ===> {}", preBoNm);
				
				if (preBoNm.equals(currentBoNm)) {
					// 현재 행과 이전 행의 법인 이름이 같다면 pass / 다르면 delete하기
					continue;
				}
				
				param.setBoNm(currentBoNm);
				
				// 기존데이터 삭제
				aiaDao.deleteArSmmryCust(param);
				
			}
			
			int isInserted = aiaDao.insertArSmmryCust(IUDObj);
			
			if (isInserted < 1) {
				return new BaseResponse(ResponseCode.C589);
			}
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.error("doSaveArExcel==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}
	}


	@Override
	public BaseResponse deleteArIncreAnalysis(ArIncreAnalysisRequest paramDto) {
		
		try {
			// 필수입력값 체크
			if (StringUtils.isBlank(paramDto.getArYymm()) 
					|| StringUtils.isBlank(paramDto.getBoId().toString())
					|| StringUtils.isBlank(paramDto.getArSeq().toString())) {
				return new BaseResponse(ResponseCode.C781);
			}
			
			int isDeleted = aiaDao.deleteAnalysis(paramDto);
			
			log.debug("deleteArIncreAnalysis > isDeleted ===> " + aiaDao.deleteAnalysis(paramDto));
			
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
