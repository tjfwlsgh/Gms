package com.lgl.gms.webapi.bse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.bse.dto.request.BseExchgRateInqRequest;
import com.lgl.gms.webapi.bse.dto.request.BsePlnExchgRateInqRequest;
import com.lgl.gms.webapi.bse.dto.response.BseExchgRateInqExcelResponse;
import com.lgl.gms.webapi.bse.dto.response.BseExchgRateInqResponse;
import com.lgl.gms.webapi.bse.dto.response.BsePlnExchgRateInqResponse;
import com.lgl.gms.webapi.bse.persistence.dao.BseExchgRateInqDao;
import com.lgl.gms.webapi.bse.persistence.model.BseExchgRateModel;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;
import com.lgl.gms.webapi.common.util.MessageUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : BseExchgRateInqServiceImpl.java
 * @Date        : 22.03.30
 * @Author      : hj.Chung
 * @Description : Controller
 * @History     : 환율정보 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class BseExchgRateInqServiceImpl implements BseExchgRateInqService {
	
	@Autowired
	public BseExchgRateInqDao beriDao;

	@Override
	public BaseResponse getExchgRateInqList(BseExchgRateInqRequest paramDto) {
		try {
			
			List<BseExchgRateInqResponse> list = (List<BseExchgRateInqResponse>)beriDao.selectExchgRateInqList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			return new BaseResponse(ResponseCode.C589);
		}
	} 
	
	
	@Override
	public BaseResponse getPlnExchgRateInqList(BsePlnExchgRateInqRequest paramDto) {
		try {
			
			List<BsePlnExchgRateInqResponse> list = (List<BsePlnExchgRateInqResponse>)beriDao.selectPlnExchgRateInqList(paramDto);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;
			
		} catch (Exception e) {
			return new BaseResponse(ResponseCode.C589);
		}
	}

	@Override
	public BaseResponse saveExchgRate(Map<String, Object> IudObj) {
		
		List<BseExchgRateInqExcelResponse> exchgRates = (List<BseExchgRateInqExcelResponse>)IudObj.get("exchgRateList");
		List<BseExchgRateInqExcelResponse> plnExchgRates = (List<BseExchgRateInqExcelResponse>)IudObj.get("plnExchgRateList");

		if((exchgRates == null || exchgRates.size() == 0 ) &&  (plnExchgRates == null || plnExchgRates.size() == 0)) {
			return new BaseResponse(ResponseCode.C589);
		}

		try {			
			
			// 엑셀파일 업로드이므로 환율정보 저장전에 기존데이터 삭제
			if (!exchgRates.isEmpty()) {
				beriDao.deleteExchgRate(IudObj);			
				beriDao.insertIncExpPlan(IudObj);

			}
			if (!plnExchgRates.isEmpty()) {
				beriDao.deletePlnExchgRate(IudObj);			
				beriDao.insertPlnIncExpPlan(IudObj);
			}
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
		}	
	}

	/**
	 * 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		BseExchgRateInqRequest req = (BseExchgRateInqRequest)body;
		List<BseExchgRateInqExcelResponse> exchgRateInqExcelList;		// 엑셀 내용 담을 리스트 객체
		
		try {
			exchgRateInqExcelList = ExcelUtil.getObjectList(file, BseExchgRateInqExcelResponse::from, 2);
			
			for(int idx = 0; idx < exchgRateInqExcelList.size(); idx++ ) {
				BseExchgRateInqExcelResponse expRet = exchgRateInqExcelList.get(idx);
				// key값 저장
				expRet.setYymm(req.getYymm());
								
			}

			return new BaseResponse(ResponseCode.C200, exchgRateInqExcelList);
			
		} catch (Exception e) {
			return new BaseResponse(ResponseCode.C589);
		}
	}

	
	/**
	 * 에러 추가
	 * @param expRet
	 * @param msgKey
	 * @param cols
	 */
	private void setError(BseExchgRateInqExcelResponse expRet, String msgKey, String... cols) {		
		String errorMsg = expRet.getErrorMsg();
//		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + ", ";
		expRet.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
		
		// 에러컬럼 추가
		List<String> errorColumns = expRet.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		expRet.setErrorColumns(errorColumns);
	}

	

}
