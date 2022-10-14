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
import com.lgl.gms.webapi.fin.dto.request.FinBalanceSheetRequest;
import com.lgl.gms.webapi.fin.dto.request.FinCommonRequest;
import com.lgl.gms.webapi.fin.dto.response.BoBsInfoResponse;
import com.lgl.gms.webapi.fin.dto.response.FinBalanceSheetExcelResponse;
import com.lgl.gms.webapi.fin.dto.response.FinBalanceSheetExcelResponse.FrmBo;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoBoResponse;
import com.lgl.gms.webapi.fin.dto.response.FrmInfoDetResponse;
import com.lgl.gms.webapi.fin.persistence.dao.FinBalanceSheetDao;
import com.lgl.gms.webapi.fin.persistence.dao.FinCommonDao;
import com.lgl.gms.webapi.fin.persistence.model.BoBsDetModel;
import com.lgl.gms.webapi.fin.persistence.model.BoBsInfoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 재무제표 Impl
 * @author JOKIM
 * @date 2022.02.22
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class FinBalanceSheetServiceImpl implements FinBalanceSheetService {
	
	@Autowired
	private FinCommonDao finComDao;
	
	@Autowired
	private FinBalanceSheetDao finBalanceSheetDao;

	
	@Override
	public BaseResponse selectFinBsSheetList(FinBalanceSheetRequest param) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			
			returnMap.put("finBoBsInfos", finBalanceSheetDao.selectFinBoBsInfoList(param));
			
			param.setFrmBoList(finBalanceSheetDao.selectBoBsInfoList(param));
			returnMap.put("finBsSheets", finBalanceSheetDao.selectFinBsSheetList(param));			
			
			return new BaseResponse(ResponseCode.C200, returnMap);		
			
		} catch (Exception e) {
			
			log.error("selectFinBsSheetList error!! ==> {}",e.toString());
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		FinBalanceSheetRequest req = (FinBalanceSheetRequest)body;
		
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
		
		FinBalanceSheetExcelResponse.META_DATA = list;
	
		// 엑셀내용을 담을 리스트 객체 정의
		Map<String, Object> excelMap = new HashMap<String, Object>();
		List<FinBalanceSheetExcelResponse> finBoBsInfos;		// 헤더정보
		List<FinBalanceSheetExcelResponse> finBsSheets;		// 재무제표내용
				
		try {
			int hIndex = "KRW".equals(req.getCrncyType()) ? 3 : 2;
			finBoBsInfos = ExcelUtil.getObjectList(file, FinBalanceSheetExcelResponse::from, 0, hIndex, req.getSheetNum());
			finBsSheets = ExcelUtil.getObjectList(file, FinBalanceSheetExcelResponse::from, hIndex, null, req.getSheetNum());
			
			// 리턴할 객체
			List<LinkedHashMap<String, Object>> returList = new ArrayList<LinkedHashMap<String, Object>>(); 
			
			for(FinBalanceSheetExcelResponse finBs : finBsSheets) {
				LinkedHashMap<String, Object> tmap = new LinkedHashMap<String, Object>();
				
				tmap.put("frmNm", finBs.getFrmNm());
				
				List<FrmBo> frmBos = finBs.getFrmBos();
				if(frmBos != null) {
					for(FrmBo fbo : frmBos) {
						tmap.put(String.valueOf(fbo.getBoId()), fbo.getFinValue());
					}
				}
								
				returList.add(tmap);
			}
						
			excelMap.put("finBoBsInfos", finBoBsInfos);
			excelMap.put("finBsSheets", returList);
			
			return new BaseResponse(ResponseCode.C200, excelMap);
			
		} catch (Exception e) {
			log.error("FinBalanceSheetServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	@Override
	public BaseResponse saveFinBalanceSheet(HashMap<String, Object> finBalanceMap) {

		Integer frmId = (Integer) finBalanceMap.get("frmId");
		String clsYymm = (String) finBalanceMap.get("clsYymm");
		String crncyType = (String) finBalanceMap.get("crncyType");
		
		try {
			// 법인BS 정보
			ArrayList<HashMap<String, Object>> finBoBsInfos = (ArrayList<HashMap<String, Object>>) finBalanceMap.get("finBoBsInfos");
			// 법인 상세
			ArrayList<HashMap<String, Object>> finBsSheets = (ArrayList<HashMap<String, Object>>) finBalanceMap.get("finBsSheets");
			
			// **** 법인정보 저장 
			// 첫번째배열 -> PASS :법인명 (pass 사용X) - 법인양식에 따라 저장하므로.
			
			// 두번째배열 (통화)
			List<HashMap<String, Object>> bsInfoList1 = new ArrayList<HashMap<String, Object>>();
			if(finBoBsInfos.size() > 1) {
				bsInfoList1 = (ArrayList<HashMap<String, Object>>) finBoBsInfos.get(1).get("frmBos");
			}
			
			// 세번째배열 기말환율
			List<HashMap<String, Object>> bsInfoList2 = new ArrayList<HashMap<String, Object>>();
			if(finBoBsInfos.size() > 2) {
				bsInfoList2 = (ArrayList<HashMap<String, Object>>) finBoBsInfos.get(2).get("frmBos");
			}
			
			// 법인양식에 따라 헤더값 합치기 - 법인BS정보테이블에 법인ID/통화/기말환율등 저장 위하여 3가지 합침
			List<BoBsInfoModel> bsInfoList = new ArrayList<BoBsInfoModel>();

			for(HashMap<String, Object> bsInfo: bsInfoList1) {
				BoBsInfoModel target= new BoBsInfoModel();
				target.setBoId(Integer.parseInt((String)bsInfo.get("boId")));
				target.setCrncyCd((String) bsInfo.get("finValue"));	// 엑셀업로드 시 CrncyCd 헤더값dl finValue 변수안에 넣어진다.

		        bsInfoList.add(target);
		    }

			if(finBoBsInfos.size() > 2) {
				for(int idx = 0; idx < bsInfoList.size(); idx++) {
					BoBsInfoModel bs = bsInfoList.get(idx);
					bs.setBseExchgRate(new BigDecimal((String)bsInfoList2.get(idx).get("finValue")));
				}			
			}
			
			// 저장
			FinBalanceSheetRequest bsParam = new FinBalanceSheetRequest();
			bsParam.setClsYymm(clsYymm);
			bsParam.setFrmId(frmId);
			
			// 법인정보가 이미 저장되어 있는지 체크용 
			List<BoBsInfoResponse> checBoList = finBalanceSheetDao.selectBoBsInfoList(bsParam);
			
			Map<String, Object> bsInfoReq = new HashMap<String, Object>(); 
			bsInfoReq.put("frmId", frmId);
			bsInfoReq.put("clsYymm", clsYymm);
			bsInfoReq.put("bsInfoList", bsInfoList);
			// 이미저장되어 있는 정보가 없다면 모두 insert
			if(checBoList == null || checBoList.size() <= 0) {				
				finBalanceSheetDao.insertBoBsInfo(bsInfoReq);
			
			// 이미 저장되어 있는 정보가 있다면 merge
			} else {
				
				for(BoBsInfoModel bs : bsInfoList) {
					bs.setFrmId(frmId);
					bs.setClsYymm(clsYymm);
					finBalanceSheetDao.updateBoBsInfo(bs);
				}
			}
			
			// 법인BS 상세
			// 양식정보 상세 리스트 조회
			FinCommonRequest comReq = new FinCommonRequest();
			comReq.setFrmId((Integer) finBalanceMap.get("frmId"));
			List<FrmInfoDetResponse> frmInfoDetList = finComDao.selectFrmInfoDetList(comReq);
			
			// 법인 BS 정보 조회(해당 BS_ID)에 따라 저장 위하여..
			List<BoBsInfoResponse> boBsInfoList = finBalanceSheetDao.selectBoBsInfoList(bsParam);
			// 저장용 리스트 변수 선언
			List<BoBsDetModel> bsSheets= new ArrayList<BoBsDetModel>();
			
			// 해당 재무양식 정보에 따라, 법인BS정보에 따라(row_seq, bs_id)
			// 재무양식 루프
			for(int idx = 0; idx < frmInfoDetList.size(); idx++ ) {
				FrmInfoDetResponse frmDet = frmInfoDetList.get(idx);
				
				HashMap<String, Object> finBsSheet = finBsSheets.get(idx);
				// 법인BS 루프
				for(BoBsInfoResponse boBsInfo : boBsInfoList) {
					BoBsDetModel bsDet = new BoBsDetModel();
					bsDet.setRowSeq(frmDet.getRowSeq());

					String boId = String.valueOf((boBsInfo.getBoId()));
					
					// 재무금액 - 엑셀 업로드데이터에서 법인BS에 해당하는 재무금액을 가지고온다
					String finAmtStr = (String)finBsSheet.get(boId);					
					BigDecimal finAmt = StringUtils.isBlank(finAmtStr) ? null : new BigDecimal((String)finBsSheet.get(boId));

					bsDet.setBsId(boBsInfo.getBsId());
					bsDet.setFinAmt(finAmt);
					bsSheets.add(bsDet);
				}
				
			}
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			if("KRW".equals(crncyType)) { 		// 원화				
				finBalanceSheetDao.deleteBoBsDetKrw(bsParam);
			} else if("LOC".equals(crncyType)) {	// 외화				
				finBalanceSheetDao.deleteBoBsDetLocCrcy(bsParam);
			}
			
			// 300개씩 나눠서 insert
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)bsSheets.size() / MAX_ROW);					

			for(int idx = 0; idx < loopCnt; idx++ ) {
						
				int startIdx = idx*MAX_ROW;
				int endIdx = bsSheets.size() < startIdx+MAX_ROW ? bsSheets.size() : startIdx+MAX_ROW;

				List<BoBsDetModel> temList = bsSheets.subList(startIdx, endIdx);
				if("KRW".equals(crncyType)) {				// 원화
					finBalanceSheetDao.insertBoBsDetKrw(temList);
				} else if("LOC".equals(crncyType)) {		// 외화
					finBalanceSheetDao.insertBoBsDetLocCrcy(temList);
				}
			}

			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			log.error("saveFinBalanceSheet error!! ==> {}", e.getMessage());		
			e.printStackTrace();
			return new BaseResponse(ResponseCode.C589);
		}	
	}

	
}
