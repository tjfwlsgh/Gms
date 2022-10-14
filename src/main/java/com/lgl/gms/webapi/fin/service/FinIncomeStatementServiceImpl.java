package com.lgl.gms.webapi.fin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.lgl.gms.webapi.fin.dto.request.FinCommonRequest;
import com.lgl.gms.webapi.fin.dto.request.FinIncomeStatementRequest;
import com.lgl.gms.webapi.fin.dto.response.BoPlInfoResponse;
import com.lgl.gms.webapi.fin.dto.response.FinIncomeStatementExcelResponse;
import com.lgl.gms.webapi.fin.dto.response.FinIncomeStatementExcelResponse.FrmBo;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoBoResponse;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoDetResponse;
import com.lgl.gms.webapi.fin.persistence.dao.FinCommonDao;
import com.lgl.gms.webapi.fin.persistence.dao.FinIncomeStatementDao;
import com.lgl.gms.webapi.fin.persistence.model.BoPlDetModel;
import com.lgl.gms.webapi.fin.persistence.model.BoPlInfoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 재무 > 손익계산서 Impl
 * @author jokim
 * @date 2022.02.22
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class FinIncomeStatementServiceImpl implements FinIncomeStatementService {
	
	@Autowired
	private FinCommonDao finComDao;
	
	@Autowired
	private FinIncomeStatementDao finIncomeStatementDao;

	
	@Override
	public BaseResponse selectFinPlStatementList(FinIncomeStatementRequest param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			
			// 손익계산서 헤더 조회
			returnMap.put("finBoPlInfos", finIncomeStatementDao.selectFinBoPlInfoList(param));
			
			// 손익계산서 리스트 조회
			param.setFrmBoList(finIncomeStatementDao.selectBoPlInfoList(param));
			returnMap.put("finPlStatements", finIncomeStatementDao.selectFinIncStatementList(param));			
			
			return new BaseResponse(ResponseCode.C200, returnMap);		
			
		} catch (Exception e) {
			
			log.error("selectFinPlStatementList error!! ==> {}",e.toString());
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		FinIncomeStatementRequest req = (FinIncomeStatementRequest)body;
		
		FinCommonRequest param = new FinCommonRequest();
		param.setFrmId(req.getFrmId());
		
		// 해당양식 법인리스트 조회
		List<FrmInfoBoResponse> frmBoInfos = finComDao.selectFrmInfoBoList(param);
		
		List<FrmBo> list = new ArrayList<FrmBo>();
		
		for(FrmInfoBoResponse bo : frmBoInfos) {
			FrmBo fBo = new FrmBo();
			fBo.setBoId(String.valueOf(bo.getBoId()));
			list.add(fBo);
		}
		
		FinIncomeStatementExcelResponse.META_DATA = list;
	
		// 엑셀내용을 담을 리스트 객체 정의
		Map<String, Object> excelMap = new HashMap<String, Object>();
		List<FinIncomeStatementExcelResponse> finBoPlInfos;		// 헤더정보
		List<FinIncomeStatementExcelResponse> finPlStatements;		// 재무제표내용
				
		try {
			int hIndex = "KRW".equals(req.getCrncyType()) ? 3 : 2;
			finBoPlInfos = ExcelUtil.getObjectList(file, FinIncomeStatementExcelResponse::from, 0, hIndex, req.getSheetNum());
			finPlStatements = ExcelUtil.getObjectList(file, FinIncomeStatementExcelResponse::from, hIndex, null, req.getSheetNum());
			
			// 리턴할 객체
			List<LinkedHashMap<String, Object>> returList = new ArrayList<LinkedHashMap<String, Object>>(); 
			
			for(FinIncomeStatementExcelResponse finPl : finPlStatements) {
				LinkedHashMap<String, Object> tmap = new LinkedHashMap<String, Object>();
				
				tmap.put("frmNm", finPl.getFrmNm());
				
				List<FrmBo> frmBos = finPl.getFrmBos();

				for(FrmBo fbo : frmBos) {
					tmap.put(String.valueOf(fbo.getBoId()), fbo.getFinValue());
				}
				
				returList.add(tmap);
			}
						
			excelMap.put("finBoPlInfos", finBoPlInfos);
			excelMap.put("finPlStatements", returList);
			
			return new BaseResponse(ResponseCode.C200, excelMap);
			
		} catch (Exception e) {
			log.error("FinBalanceSheetServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	/**
	 * 해당 항목양식과 / 양식 법인에 따라 손익계산서 리스트를 저장한다
	 */
	@Override
	public BaseResponse saveFinIncomeStatement(HashMap<String, Object> finPlMap) {

		Integer frmId = (Integer) finPlMap.get("frmId");
		String clsYymm = (String) finPlMap.get("clsYymm");
		String crncyType = (String) finPlMap.get("crncyType");
		
		try {
			// 법인BS 정보
			ArrayList<HashMap<String, Object>> finBoPlInfos = (ArrayList<HashMap<String, Object>>) finPlMap.get("finBoPlInfos");
			// 법인 상세
			ArrayList<HashMap<String, Object>> finPlStatements = (ArrayList<HashMap<String, Object>>) finPlMap.get("finPlStatements");
			
			// **** 법인정보 저장 
			// 첫번째배열 -> PASS :법인명 (pass 사용X)	
			
			// 두번째배열 (통화)
			List<HashMap<String, Object>> plInfoList1 = new ArrayList<HashMap<String, Object>>();
			if(finBoPlInfos.size() > 1) {
				plInfoList1 = (ArrayList<HashMap<String, Object>>) finBoPlInfos.get(1).get("frmBos");
			}
			
			// 세번째배열 평균환율
			List<HashMap<String, Object>> plInfoList2 = new ArrayList<HashMap<String, Object>>();
			if(finBoPlInfos.size() > 2) {
				plInfoList2 = (ArrayList<HashMap<String, Object>>) finBoPlInfos.get(2).get("frmBos");
			}
			
			// 합치기
			List<BoPlInfoModel> plInfoList = new ArrayList<BoPlInfoModel>();

			for(HashMap<String, Object> plInfo: plInfoList1) {
				BoPlInfoModel target= new BoPlInfoModel();
				target.setBoId(Integer.parseInt((String)plInfo.get("boId")));
				target.setCrncyCd((String) plInfo.get("finValue"));

				plInfoList.add(target);
		    }

			if(finBoPlInfos.size() > 2) {
				for(int idx = 0; idx < plInfoList.size(); idx++) {
					BoPlInfoModel pl = plInfoList.get(idx);
					pl.setBseExchgRate(new BigDecimal((String)plInfoList2.get(idx).get("finValue")));
				}			
			}
			
			// 저장
			FinIncomeStatementRequest plParam = new FinIncomeStatementRequest();
			plParam.setClsYymm(clsYymm);
			plParam.setFrmId(frmId);
			
			// 양식 법인 리스트 가지고 오기 - // 법인정보가 이미 저장되어 있는지 체크용 
			List<BoPlInfoResponse> checkBoList = finIncomeStatementDao.selectBoPlInfoList(plParam);
			
			Map<String, Object> plInfoReq = new HashMap<String, Object>(); 
			plInfoReq.put("frmId", frmId);
			plInfoReq.put("clsYymm", clsYymm);
			plInfoReq.put("plInfoList", plInfoList);

			// 해당 법인 헤더 없으면 insert / 있으면 upldate
			if(checkBoList == null || checkBoList.size() <= 0) {				
				finIncomeStatementDao.insertBoPlInfo(plInfoReq);
			} else {
				
				for(BoPlInfoModel pl : plInfoList) {
					pl.setFrmId(frmId);
					pl.setClsYymm(clsYymm);
					finIncomeStatementDao.updateBoPlInfo(pl);
				}
			}
			
			// 법인 상세
			// 양식정보 상세 리스트 조회
			FinCommonRequest comReq = new FinCommonRequest();
			comReq.setFrmId((Integer) finPlMap.get("frmId"));
			List<FrmInfoDetResponse> frmInfoDetList = finComDao.selectFrmInfoDetList(comReq);
			
			// 법인 PL 정보 조회			
			List<BoPlInfoResponse> boPlInfoList = finIncomeStatementDao.selectBoPlInfoList(plParam);

			List<BoPlDetModel> plStatements= new ArrayList<BoPlDetModel>();
			
			// 양식항목 리스트
			for(int idx = 0; idx < frmInfoDetList.size(); idx++ ) {
				FrmInfoDetResponse frmDet = frmInfoDetList.get(idx);
				
				// 손익계산서 파라미터 리스트
				HashMap<String, Object> finPlStatement = finPlStatements.get(idx);
				
				// 법인 양식 리스트
				for(BoPlInfoResponse boPlInfo : boPlInfoList) {
					BoPlDetModel plDet = new BoPlDetModel();
					plDet.setRowSeq(frmDet.getRowSeq());

					String boId = String.valueOf((boPlInfo.getBoId()));
					String finAmtStr = (String)finPlStatement.get(boId);
					
					BigDecimal finAmt = StringUtils.isBlank(finAmtStr) ? null : new BigDecimal((String)finPlStatement.get(boId));

					plDet.setPlId(boPlInfo.getPlId());
					plDet.setFinAmt(finAmt);
					
					// 저장용 리스트에 add한다.
					plStatements.add(plDet);
				}
				
			}
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			if("KRW".equals(crncyType)) {
				finIncomeStatementDao.deleteBoPlDetKrw(plParam);
			} else if("LOC".equals(crncyType)) {
				finIncomeStatementDao.deleteBoPlDetLocCrcy(plParam);
			}
			
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)plStatements.size() / MAX_ROW);					

			for(int idx = 0; idx < loopCnt; idx++ ) {
						
				int startIdx = idx*MAX_ROW;
				int endIdx = plStatements.size() < startIdx+MAX_ROW ? plStatements.size() : startIdx+MAX_ROW;

				List<BoPlDetModel> temList = plStatements.subList(startIdx, endIdx);
				if("KRW".equals(crncyType)) {
					finIncomeStatementDao.insertBoPlDetKrw(temList);
				} else if("LOC".equals(crncyType)) {
					finIncomeStatementDao.insertBoPlDetLocCrcy(temList);
				}
			}

			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveFinIncomeStatement error!! ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
		}	
	}

	
}
