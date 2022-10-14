package com.lgl.gms.webapi.fin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.FinCommonRequest;
import com.lgl.gms.webapi.fin.persistence.dao.FinCommonDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 재무공통 서비스 Impl
 * @author jokim
 * @date 2022.04.13
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class FinCommonServiceImpl implements FinCommonService {
	
	@Autowired
	private FinCommonDao finCommonDao;
	
	/**
	 * 양식정보(콤보용)
	 */
	@Override
	public BaseResponse selectFrmInfoList(FinCommonRequest param) {

		try {
			
			return new BaseResponse(ResponseCode.C200, finCommonDao.selectFrmInfoList(param));
			
		} catch (Exception e) {
			
			log.error("selectFrmInfoList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}

	/**
	 * 양식정보 법인리스트
	 */
	@Override
	public BaseResponse selectFrmBoInfoList(FinCommonRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, finCommonDao.selectFrmInfoBoList(param));
			
		} catch (Exception e) {
			
			log.error("selectFrmBoInfoList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}

	/**
	 * 엑셀 시트명 가지고 오기
	 */
	@Override
	public BaseResponse getExcelSheetNames(MultipartFile file, Object body) {

		try {
			final Workbook workbook = WorkbookFactory.create(file.getInputStream());

			//sheet 갯수
			int sheetCnt = workbook.getNumberOfSheets();

			List<HashMap<String, Object>> sheets = new ArrayList<HashMap<String, Object>>();

			for(int idx = 0; idx < sheetCnt; idx++){
				if(!workbook.isSheetHidden(idx)) {
					Map<String, Object> sheetMap = new HashMap<String, Object>();
					
					sheetMap.put("value", idx);
					sheetMap.put("label", workbook.getSheetName(idx));
					sheets.add((HashMap<String, Object>) sheetMap);
				}				
			}
			
			return new BaseResponse(ResponseCode.C200, sheets);
			
		} catch (Exception e) {
			
			log.error("getExcelSheetNames error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
		
	}

	/**
	 * 재무제표 법인목록 리스트 - 법인목록관리팝업
	 */
	@Override
	public BaseResponse selectFinBoMgmtList(FinCommonRequest param, String pos) {
		
		try {
			
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			
			if("ALL".equalsIgnoreCase(pos) || "LEFT".equalsIgnoreCase(pos)) {
				rtnMap.put("sourceBos", finCommonDao.selectFrmSourceBoList(param));
			}
			
			if("ALL".equalsIgnoreCase(pos) || "RIGHT".equalsIgnoreCase(pos)) {
				rtnMap.put("targetBos", finCommonDao.selectFrmTargetBoList(param));
			}
			
			return new BaseResponse(ResponseCode.C200, rtnMap);
			
		} catch (Exception e) {
			
			log.error("selectFrmSourceBoList error => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}
	
	/**
	 * 법인양식 저장
	 * @frmBoInfo : 법인양식 대상 리시트
	 */
	@Override
	public BaseResponse saveFinBoMgmtList(HashMap<String, Object> frmBoInfo) {
		
		try {
			FinCommonRequest param = new FinCommonRequest();
			
			param.setFrmId((Integer) frmBoInfo.get("frmId"));
			
			finCommonDao.deleteFrmInfoBos(param);
			finCommonDao.insertFrmInfoBos(frmBoInfo);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveFinBoMgmtList error!! ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
			
		}
	}
		

}
