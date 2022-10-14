package com.lgl.gms.webapi.bse.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.bse.dto.request.FinFormRequest;
import com.lgl.gms.webapi.bse.dto.response.FinFormExcelResponse;
import com.lgl.gms.webapi.bse.persistence.dao.FinFormDao;
import com.lgl.gms.webapi.bse.persistence.model.FrmInfoDetModel;
import com.lgl.gms.webapi.bse.persistence.model.FrmInfoModel;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.util.ExcelUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @FileName    : FinFormServiceImpl.java
 * @Date        : 2022.05.09
 * @Author      : jokim
 * @Description : ServiceImpl
 * @History     : 재무양식관리 ServiceImpl
 */
@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class FinFormServiceImpl implements FinFormService {
	
	@Autowired
	public FinFormDao finFormDao;
	
	/**
	 * 재무양식 조회
	 */
	@Override
	public BaseResponse getFrmInfoList(FinFormRequest paramDto) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, finFormDao.selectFrmInfoList(paramDto));
			
		} catch (Exception e) {
			log.error("getFrmInfoList error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	/**
	 * 재무양식 상세 조회
	 */
	@Override
	public BaseResponse getFrmInfoDetList(FinFormRequest paramDto) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, finFormDao.selectFrmInfoDetList(paramDto));
			
		} catch (Exception e) {
			log.error("getFrmInfoDetList error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
		
	}
	
	/**
	 * 재무양식 상세 
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		
		try {
			FinFormRequest req = (FinFormRequest)body;

			int startRow = "N".equals(req.getIncludeFrmYn()) ? 2 : 1;
				
			List<FinFormExcelResponse> frmDetails = ExcelUtil.getObjectList(file, FinFormExcelResponse::from, startRow, null, req.getSheetNum());
			
			// 양식포함 = N 일 경우 양식(영문)/레벨/순번/계정코드 null
			if("N".equals(req.getIncludeFrmYn())) {
				
				for(int idx = 0; idx < frmDetails.size(); idx++) {
					FinFormExcelResponse frm = frmDetails.get(idx);
					frm.setRowSeq(String.valueOf(idx+1));
					frm.setAccId(null);
					frm.setFrmNmEng(null);
					frm.setLvCl(null);
				}			
				
			}
						
			return new BaseResponse(ResponseCode.C200, frmDetails);
			
		} catch (Exception e) {
			
			log.error("FinFormServiceImpl excelUpload error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
	
	/**
	 * 재무양식 추가
	 */
	@Override
	public BaseResponse insertFrmInfo(FrmInfoModel paramDto) {
		
		try {
			
			finFormDao.insertFrmInfo(paramDto);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("frmId", paramDto.getFrmId());
			return new BaseResponse(ResponseCode.C200, paramDto.getFrmId());
			
		} catch (Exception e) {
			
			log.error("FinFormServiceImpl insertFrmInfo error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}

	/**
	 * 재무양식 수정
	 */
	@Override
	public BaseResponse updateFrmInfo(FrmInfoModel paramDto) {
		
		try {
			
			finFormDao.updateFrmInfo(paramDto);
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			log.error("updateFrmInfo error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * 계정관리 리스트 조회
	 */
	@Override
	public BaseResponse getAccMgmtList(FinFormRequest paramDto) {
		
		try {
			Map<String, Object> accMgmMap = new HashMap<String, Object>();
			paramDto.setFrmCd("1");
			accMgmMap.put("accMgmt1", finFormDao.selectAccMgmtList(paramDto));
			
			paramDto.setFrmCd("2");
			accMgmMap.put("accMgmt2", finFormDao.selectAccMgmtList(paramDto));
			
			return new BaseResponse(ResponseCode.C200, accMgmMap);
			
		} catch (Exception e) {
			log.error("getAccMgmtList error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}


	}
	
	/**
	 * 양식상세 저장(엑셀업로드 한 내용 저장)
	 */
	@Override
	public BaseResponse saveFrmInfoDet(Map<String, Object> frmDetMap) {
		
		try {
			Integer frmId = (Integer) frmDetMap.get("frmId");
			FinFormRequest param = new FinFormRequest();
			param.setFrmId(frmId);
			
			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			finFormDao.deleteFrmInfoDetList(param);
			
			// 추가
			List<FrmInfoDetModel> frmDetList = (List<FrmInfoDetModel>) frmDetMap.get("frmDetails");
			
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)frmDetList.size() / MAX_ROW);					
			
			for(int idx = 0; idx < loopCnt; idx++ ) {
				
				HashMap<String, Object> frmMap = new HashMap<String, Object>();
				frmMap.put("frmId", frmId);
				
				int startIdx = idx*MAX_ROW;
				int endIdx = frmDetList.size() < startIdx+MAX_ROW ? frmDetList.size() : startIdx+MAX_ROW;

				List<FrmInfoDetModel> temList = frmDetList.subList(startIdx, endIdx);
				
				frmMap.put("list", temList);
				finFormDao.insertFrmInfoDetList(frmMap);
			}
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveFrmInfoDet ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
		}
		
		
	}
	
	

}
