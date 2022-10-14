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
import com.lgl.gms.webapi.inc.dto.response.BoIncPlanResponse;
import com.lgl.gms.webapi.inc.dto.response.IncExpPlnExcelResponse;
import com.lgl.gms.webapi.inc.dto.response.IncItmDetResponse;
import com.lgl.gms.webapi.inc.persistence.dao.IncExpPlanDao;
import com.lgl.gms.webapi.inc.persistence.dao.IncPlanDao;
import com.lgl.gms.webapi.inc.persistence.model.BoIncExpPlanModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class IncExpPlanServiceImpl implements IncExpPlanService {
	
	@Autowired
	private IncPlanDao incPlanDao;
	
	@Autowired
	private IncExpPlanDao incExpPlanDao;
	
	@Autowired
	private CommonCodeDao codeDao;
	
	/**
	 * (계획)영업외손익/판관비 목록 조회
	 */
	@Override
	public BaseResponse selectIncExpPlanList(BoIncPlanRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, incExpPlanDao.selectIncExpPlanList(param));		
			
		} catch (Exception e) {
			
			log.error(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
			
		}

	}
	
	/**
	 * 엑셀업로드
	 */
	@Override
	public BaseResponse excelUpload(MultipartFile file, Object body) {
		BoIncPlanRequest req = (BoIncPlanRequest)body;
		List<IncExpPlnExcelResponse> IncExpPlnList;		// 엑셀 내용 담을 리스트 객체
		
		try {
			IncExpPlnList = ExcelUtil.getObjectList(file, IncExpPlnExcelResponse::from, 3);
			
			// 손익항목조회(체크용)
			List<IncItmDetResponse> incItmDetList = incExpPlanDao.selectIncItmDetList(req);
						
			for(int idx = 0; idx < IncExpPlnList.size(); idx++ ) {
				IncExpPlnExcelResponse expPlan = IncExpPlnList.get(idx);
				// key값 저장
				expPlan.setBoId(req.getBoId());
				expPlan.setIncYy(req.getIncYy());
				expPlan.setSeq(idx);
				
				// 월 zero pad
				expPlan.setIncMon(String.format("%02d", Integer.parseInt(expPlan.getIncMon())));
								
				// 항목체크
				checkItemDel(expPlan, incItmDetList);
				
			}
			
			// 엑셀등록시 해당 유형만 필터링
			IncExpPlnList = IncExpPlnList.stream().filter( itm -> req.getTypCd().equals(itm.getTypCdc()))
					  								.collect(Collectors.toList());
			
			
			// 영업실적 금액체크용(유형(STD_CD)TYP = 1 일 경우)
			// 항목체크시 에러 없을 경우
			if("1".equals(req.getTypCd())) {
				
				checkType1Amt(IncExpPlnList, req);				
			}

			return new BaseResponse(ResponseCode.C200, IncExpPlnList);
			
		} catch (Exception e) {
			log.error("IncExpPlanServiceImpl excelUpload Exception => {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	/**
	 * 판관비-영업실적 확인
	 * @param IncExpPlnList
	 * @param req
	 */
	private void checkType1Amt(List<IncExpPlnExcelResponse> IncExpPlnList, BoIncPlanRequest req) {

		// 그룹1 공통코드
		CommonCodeRequest codeReq = new CommonCodeRequest();
		codeReq.setCodeType("GRP01");
		
		List<CommonCodeResponse> grp1s = (List<CommonCodeResponse>) codeDao.selectCommonCodeList(codeReq);
		String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("boId", req.getBoId());
		param.put("incYy", req.getIncYy());
		param.put("grp1s", grp1s);
		param.put("months", months);
		Map<String, Object> type1Sales = incExpPlanDao.selectType1SaleAmt(param);
		
		for(CommonCodeResponse grp1 : grp1s) {
			
			// 체크할 항목 Id			
			// 인건비 ( 1-> 기존사업, 2 -> 협력사업 )
			Integer chkLbrIncItmId = "1".equals(grp1.getStdCd()) ? 123 : ("2".equals(grp1.getStdCd()) ? 126 : 0);
			
			// 경비 ( 1-> 기존사업, 2 -> 협력사업 )
			Integer chkExpIncItmId = "1".equals(grp1.getStdCd()) ? 124 : ("2".equals(grp1.getStdCd()) ? 127 : 0);
			
			for(String mon : months) {
				// 해당월과 그룹1 금액 합산 체크
				// 인건비
				List<IncExpPlnExcelResponse> lbrList = IncExpPlnList.stream()
						  .filter( itm -> grp1.getStdCd().equals(itm.getGrp1Cdc()) 
								  			&& mon.equals(itm.getIncMon()) 
								  			&& chkLbrIncItmId == itm.getIncItmId() )
						  .collect(Collectors.toList());
				
				// 경비
				List<IncExpPlnExcelResponse>  expList = IncExpPlnList.stream()
						  .filter( itm -> grp1.getStdCd().equals(itm.getGrp1Cdc()) 
								  			&& mon.equals(itm.getIncMon()) 
								  			&& chkExpIncItmId == itm.getIncItmId() )
						  .collect(Collectors.toList());
				
				// 합계
				// 인건비
				Long lbrSum = Math.round(lbrList.stream().mapToDouble(i-> Double.parseDouble(i.getExpAmt())).sum());

				// 경비
				Long expSum = Math.round(expList.stream().mapToDouble(i-> Double.parseDouble(i.getExpAmt())).sum());

				// 금액 비교
				// 인건비
				BigDecimal lbrDecimal = type1Sales == null ? BigDecimal.ZERO : (BigDecimal)type1Sales.get("lbr_"+grp1.getStdCd()+"_"+mon);
				lbrDecimal = lbrDecimal == null ? BigDecimal.ZERO : lbrDecimal;
				Long lbrCost = lbrDecimal.setScale(0, RoundingMode.HALF_UP).longValue();

				if(lbrSum.longValue() != lbrCost) {
					for(IncExpPlnExcelResponse expPlan : lbrList) {
						if(expPlan.getErrorColumns() == null || expPlan.getErrorColumns().size() <= 0 ) {
							setError2(expPlan, "message.misLbrCost", lbrCost, "expAmt");
						}						
					}

				}
				
				// 경비
				BigDecimal expDecimal = type1Sales == null ? BigDecimal.ZERO : (BigDecimal)type1Sales.get("exp_"+grp1.getStdCd()+"_"+mon);
				expDecimal = expDecimal == null ? BigDecimal.ZERO : expDecimal;
				Long expCost = expDecimal.setScale(0, RoundingMode.HALF_UP).longValue();
				
				if(expSum.longValue() != expCost) {

					for(IncExpPlnExcelResponse expPlan : expList) {
						if(expPlan.getErrorColumns() == null || expPlan.getErrorColumns().size() <= 0 ) {
							setError2(expPlan, "message.misExpCost", expCost, "expAmt");
						}						
					}

				}				
				
			}			
			
		}
		
	}
	
	/**
	 * 손익 항목 상세 체크(유형,구분1,구분2,구분3)
	 * @param expPlan
	 */
	private void checkItemDel(IncExpPlnExcelResponse expPlan, List<IncItmDetResponse> incItmDetList) {
		
		if(StringUtils.isBlank(expPlan.getTypCd()) || StringUtils.isBlank(expPlan.getCl1Cd()) || StringUtils.isBlank(expPlan.getCl2Cd())) {
			setError(expPlan, "message.chkItm", "typCd", "cl1Cd" , "cl2Cd", "grp1Cd");
			return;			
		}
		
		// 유형입력값
		String inputTpCd =	StringUtils.defaultString(expPlan.getTypCd());
		
		// 구분1 입력값
		String inputCl1Cd =	StringUtils.defaultString(expPlan.getCl1Cd());
		
		// 구분2 입력값
		String inputCl2Cd =	StringUtils.defaultString(expPlan.getCl2Cd());
		
		// 구분3 입력값
		String inputCl3Cd =	StringUtils.defaultString(expPlan.getCl3Cd());
		
		// 그룹1 입력값
		String inputGrp1Cd = StringUtils.defaultString(expPlan.getGrp1Cd());
		
		// 입력된 유형,구분1,구분2에 해당하는 유형상세
		List<IncItmDetResponse> fillteredItmList = incItmDetList.stream()
				  .filter(itm -> (inputTpCd.equals(itm.getTypCdNm()) || inputTpCd.equals(itm.getTypCdNmEng()))				// 유형일치
						  			&& ( inputCl1Cd.equals(itm.getCl1CdNm()) || inputCl1Cd.equals(itm.getCl1CdNmEng()) )	// 구분1일치
						  			&& ( inputCl2Cd.equals(itm.getCl2CdNm()) || inputCl2Cd.equals(itm.getCl2CdNmEng()) )	// 구분2 일치
						  			&& ( itm.getGrp1Id() == null || itm.getGrp1Id() == 0 || inputGrp1Cd.equals(itm.getGrp1IdNm()) || inputGrp1Cd.equals(itm.getGrp1IdNmEng()) ) // 그룹1 일치
						  		)
				  .collect(Collectors.toList());
		
		// 검색된 내용이 없으면 에러 추가
		if(fillteredItmList == null || fillteredItmList.size() == 0) {
			setError(expPlan,  "message.chkItm", "typCd", "cl1Cd" , "cl2Cd", "grp1Cd");
			return;
		}
		
		// 구분3, 그룹1 체크
		for(IncItmDetResponse itm : fillteredItmList) {
			// 유형, 구분1, 구분2 코드 세팅
			expPlan.setTypCdc(itm.getTypCd());		// 유형
			expPlan.setCl1Cdc(itm.getCl1Cd());		// 구분1
			expPlan.setCl2Cdc(itm.getCl2Cd());		// 구분2
			
			//String cl3CdNm = StringUtils.defaultString(itm.getCl3CdNm()
			
			// 구분3 항목이 존재하지만 입력이 안된경우..
			if( "".equals(inputCl3Cd) &&  !"".equals(StringUtils.defaultString(itm.getCl3CdNm()))) {
				setError(expPlan, "message.chkItm", "cl3Cd");
				break;
			}
				
		}
		
		// 수익금액, 비용금액 체크
		// 매칭데이터 한개만 뽑음
		IncItmDetResponse matchItm = null;
		
		if(!"".equals(inputCl3Cd)) {
			matchItm= fillteredItmList.stream()
					  .filter(itm -> inputCl3Cd.equals(itm.getCl3CdNm()) || inputCl3Cd.equals(itm.getCl3CdNmEng()))
					  .findAny()
					  .orElse(null);
			expPlan.setCl3Cdc(matchItm.getCl3Cd());
		} else {
			matchItm = fillteredItmList.get(0);
		}
			
		if(matchItm == null ) {
			setError(expPlan, "message.chkItm", "typCd", "cl1Cd" , "cl2Cd");	
		} else {
			String rvnYn = matchItm.getRvnYn();
			String expYn = matchItm.getExpYn();
			
			// 수익 YN == Y
			if("Y".equals(rvnYn)) {
				String rvnAmt = StringUtils.isBlank(expPlan.getRvnAmt()) ? expPlan.getExpAmt() : expPlan.getRvnAmt();
				if(StringUtils.isBlank(rvnAmt)) {
					setError(expPlan, "message.chkRvnAmt", "rvnAmt");
				} else {
					expPlan.setRvnAmt(rvnAmt);
					expPlan.setExpAmt(null);
				}					
			} else if("Y".equals(expYn )) {
				String expAmt = StringUtils.isBlank(expPlan.getExpAmt()) ? expPlan.getRvnAmt() : expPlan.getExpAmt();
				if(StringUtils.isBlank(expAmt)) {
					setError(expPlan, "message.chkExpAmt", "expAmt");
				} else {
					expPlan.setExpAmt(expAmt);
					expPlan.setRvnAmt(null);
				}					
			}
						
			
			// 그룹1 없어야하는 데이터이지만 엑셀에 값이 들어있을 경우 삭제시킴
			if(!"".equals(inputGrp1Cd) && (matchItm.getGrp1Id() == null || matchItm.getGrp1Id() == 0)) {
				expPlan.setGrp1Cd("");
			}							
						
			// 항목세팅
			expPlan.setIncItmId(matchItm.getIncItmId());
			// 상세항목 세팅
			expPlan.setIncItmDetId(matchItm.getIncItmDetId());
			// AGG_YN 세팅
			expPlan.setAggYn(matchItm.getAggYn());
			// 그룹코드 세팅
			expPlan.setGrp1Cdc(matchItm.getGrp1Cd());

		}
		
	}
	
	/**
	 * 에러 추가
	 * @param expPlan
	 * @param msgKey
	 * @param cols
	 */
	private void setError(IncExpPlnExcelResponse expPlan, String msgKey, String... cols) {		
		setError2(expPlan, msgKey, null, cols);
	}
	
	/**
	 * 금액 추가하여 에러메시지 추가
	 * @param expPlan
	 * @param msgKey
	 * @param col
	 * @param cost
	 */
	private void setError2(IncExpPlnExcelResponse expPlan, String msgKey, Long cost, String... cols) {		
		String errorMsg = expPlan.getErrorMsg();
		errorMsg = StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
		
		if(cost != null) {
			DecimalFormat dformat = new DecimalFormat("#,###");
			String coststr = dformat.format(cost);
			expPlan.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null)+coststr);
		} else {
			expPlan.setErrorMsg(errorMsg +MessageUtil.getMessage(msgKey, null));
		}
				
		// 에러컬럼 추가
		List<String> errorColumns = expPlan.getErrorColumns();
		errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
		
		String[] colsArr = cols;
		for(String col : colsArr) {
			errorColumns.add(col);
		}
		expPlan.setErrorColumns(errorColumns);
	}
	
	/**
	 * (계획)영업외손익/판관비 계획 저장
	 */
	@Override
	public BaseResponse saveIncExpPlan(List<BoIncExpPlanModel> incExpPlans, BoIncPlanRequest param) {
				
		if(incExpPlans == null || incExpPlans.size() == 0) {
			return new BaseResponse(ResponseCode.C781);
		}

		Integer	boId = incExpPlans.get(0).getBoId();
		String	incYy = incExpPlans.get(0).getIncYy();
		
		param.setBoId(boId);
		param.setIncYy(incYy);
		
		try {			

			// 엑셀파일 업로드이므로 매출계획 저장전에 기존데이터 삭제
			incExpPlanDao.deleteIncExpPlan(param);			
			
			final int MAX_ROW = 300;			
			int loopCnt = (int) Math.ceil((double)incExpPlans.size() / MAX_ROW);					

			for(int idx = 0; idx < loopCnt; idx++ ) {
						
				int startIdx = idx*MAX_ROW;
				int endIdx = incExpPlans.size() < startIdx+MAX_ROW ? incExpPlans.size() : startIdx+MAX_ROW;

				List<BoIncExpPlanModel> temIntExps = incExpPlans.subList(startIdx, endIdx);
				incExpPlanDao.insertIncExpPlan(temIntExps);
			}
	
			// 마감여부 확인위해...
			BoIncPlanResponse incPln = incPlanDao.selectIncPlan(param);
			
			if("Y".equals(incPln.getPlnCls2())) {
				
				// 버전업데이트
				BoIncPlnModel paramModel = new BoIncPlnModel();
				paramModel.setPlnCls1( incPln.getPlnCls1());
				paramModel.setBoId(param.getBoId());
				paramModel.setIncYy(param.getIncYy());
				incPlanDao.updateIncPlanFinlVer(paramModel);			
				
				// 히스토리저장
				param.setTaskNm(TASK_NM);
				incPlanDao.procIncHist(param);
				log.info("## [{}] procIncHist rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
				
				// 손익집계 프로시저 호출
				param.setTaskNm(AGG_TASK_NM);
				incPlanDao.procIncUpdAggProc(param);
				log.info("## [{}] procIncUpdAggProc rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
								
				// 손익집계프로시저 중 오류시
				if(param.getRtn().contains("ERROR")) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return new BaseResponse(ResponseCode.C302);
				}
				
			} else {
				
				// 마감초기화프로시저(마감테이블에 초기화 데이터 저장) - 최초저장시에 INSERT됨
				param.setTaskNm(CLS_INT_TASK_NM);
				incPlanDao.procIncPlnClsInt(param);
				log.info("## [{}] procIncPlnClsInt rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
				
			}
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("saveIncExpPlan ==> {}", e.getMessage());			
			return new BaseResponse(ResponseCode.C589);
		}	
	}

	/**
	 * (계획)영업외손익/판관비 삭제
	 */
	@Override
	public BaseResponse deleteIncExpPlan(BoIncPlanRequest param) {
		
		try {
			
			incExpPlanDao.deleteIncExpPlan(param);
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("deleteIncExpPlan error!! ==> {} ", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
	}
	
	/**
	 * (계획)영업외손익/판관비 마감
	 */
	@Override
	public BaseResponse procIncExpPlnCls(BoIncPlnModel paramModel, String pgmId) {
		
		try {
			
			// 마감Flag->Y(pln_cls2)			
			incExpPlanDao.updateIncExpPlnCls(paramModel);
			
			// 마감프로시저호출(sp_set_cls_mgmt)
			BoIncPlanRequest param = new BoIncPlanRequest();
			param.setBoId(paramModel.getBoId());
			param.setIncYy(paramModel.getIncYy());
			param.setPgmId(PGM_ID);
			param.setWorkIp(UserInfo.getWorkIp());
			param.setUpdNo(UserInfo.getUserId());
			incPlanDao.procIncCls(param);
			
			log.info("## [{}] procIncCls rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 마감프로시저 오류 시
			if(!"00".equals(param.getRtn())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C303);
			}

			// 히스토리저장
			param.setTaskNm(TASK_NM);
			incPlanDao.procIncHist(param);
			log.info("## [{}] procIncHist rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 손익집계 프로시저 호출
			param.setTaskNm(AGG_TASK_NM);
			incPlanDao.procIncUpdAggProc(param);
			log.info("## [{}] procIncUpdAggProc rtn : {} ", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), param);
			
			// 손익집계프로시저 중 오류시
			if(param.getRtn().contains("ERROR")) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return new BaseResponse(ResponseCode.C302);
			}
			
			return new BaseResponse(ResponseCode.C200);
			
		} catch (Exception e) {
			log.error("procIncExpPlnCls error ==> {}", e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}

	/**
	 * (계획)영업외손익/판관비 매출조회
	 */
	@Override
	public BaseResponse selectIncSalPlnDelByExpList(BoIncPlanRequest param) {
		
		try {
			
			return new BaseResponse(ResponseCode.C200, incExpPlanDao.selectIncSalPlnDelByExpList(param));		
			
		} catch (Exception e) {
			
			log.error("selectIncSalPlnDelByExpList error!! ==> {}", e.getMessage());
			return new BaseResponse(ResponseCode.C589);
			
		}
		
	}
		

}
