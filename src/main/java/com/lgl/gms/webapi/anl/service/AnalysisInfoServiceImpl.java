package com.lgl.gms.webapi.anl.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.anl.dto.request.BoSummaryRequest;
import com.lgl.gms.webapi.anl.dto.request.BusiProfRequest;
import com.lgl.gms.webapi.anl.dto.request.CustPerfRequest;
import com.lgl.gms.webapi.anl.dto.response.BoEmpInfo;
import com.lgl.gms.webapi.anl.dto.response.BoSummaryAmtInfo;
import com.lgl.gms.webapi.anl.dto.response.BoSummaryResponse;
import com.lgl.gms.webapi.anl.dto.response.BusiProfAmtInfo;
import com.lgl.gms.webapi.anl.dto.response.BusiProfResponse;
import com.lgl.gms.webapi.anl.dto.response.CustPerfResponse;
import com.lgl.gms.webapi.anl.persistence.dao.AnalysisInfoDao;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class AnalysisInfoServiceImpl implements AnalysisInfoService {

	@Autowired
	private AnalysisInfoDao analysisInfoDao;
	
	/**
	 * 거래처 매출 분석
	 */
	@Override
	public BaseResponse selectBoSummaryInfo(BoSummaryRequest param) {
		try {
			log.debug("AnalysisInfoServiceImpl.selectBoSummaryInfo > param > " + param);
		
			Integer boId = param.getBoId();   // 법인id(0이면 전체)
			String incYy = param.getIncYy();  // 년도(ex. 2022)
			String incMm = param.getIncMm();  // 선택된 월(ex. 01)
			String summaryCl = param.getSumryCl();
			
			if((boId == null)  || StringUtils.isBlank(incYy) || 
					StringUtils.isBlank(incMm) || StringUtils.isBlank(summaryCl)) {
				// 항목이 유효하지 않습니다. : 732
				return new BaseResponse(ResponseCode.C732);
			}
			
			BoSummaryResponse boSumResp = new BoSummaryResponse();

			// 법인 인원 정보 조회
			BoEmpInfo empInfo =	analysisInfoDao.selectEmpInfo(param);
	
			log.debug("AnalysisInfoServiceImpl.selectBoSummaryInfo > empInfo > " + empInfo);
			
			if (empInfo != null) {
				boSumResp.setEmpTotCnt(empInfo.getEmpTotCnt());
				boSumResp.setEmpResiCnt(empInfo.getEmpResiCnt());
				boSumResp.setEmpLocalCnt(empInfo.getEmpLocalCnt());				
			}
			
			// 법인 매출액/영업이익 조회
			List<BoSummaryAmtInfo> list = (List<BoSummaryAmtInfo>) analysisInfoDao.selectSummaryAmtList(param);
			  
			log.debug("AnalysisInfoServiceImpl.selectBoSummaryInfo > list > " + list.toString());
			
			// 법인 매출액/영업이익 정보 리스트 BoSummaryResponse에 추가
			boSumResp.setBoAmtInfoList(list);

			log.debug("AnalysisInfoServiceImpl.selectBoSummaryInfo > boSumResp > " + boSumResp);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(boSumResp);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	/**
	 * 영업이익 추이 분석
	 */
	@Override
	public BaseResponse selectBusiProfList(BusiProfRequest param) {
		try {
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList > param > " + param);
								
			Integer boId = param.getBoId();   // 법인id 
			String incYy = param.getIncYy();  // 년도(ex. 2022)
			String incMm = param.getIncMm();  // 선택된 월(ex. 01)
			Integer ct = param.getCt();   	  // 원단위(기본 만원단위)

			if((boId == null || boId == 0) || StringUtils.isBlank(incYy) || 
					StringUtils.isBlank(incMm) ) {
				// 항목이 유효하지 않습니다. : 732
				return new BaseResponse(ResponseCode.C732);
			}
			
			// 원단위 설정이 없으면 
			if (ct == null || ct == 0) {
				param.setCt(10000);	// 기본 만원단위 설정
			}
			
			BusiProfResponse bpResponse = new BusiProfResponse();

			// ---------------------- 매출액, 영업이익, 영업이익&영업비용 조회 ------------------------
			// 매출액 조회
			param.setIncItm1("매출액");
			param.setIncItm2("--");
			List<BusiProfAmtInfo> list1 = (List<BusiProfAmtInfo>) analysisInfoDao.selectSalesProfAmtList(param);
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList() > selectSalesProfAmtList > salesAmtList(매출액) > " + list1);
			bpResponse.setSalesAmtList(list1);
			
			// 영업이익 조회
			param.setIncItm1("영업이익");
			param.setIncItm2("--");
			List<BusiProfAmtInfo> list2 = (List<BusiProfAmtInfo>) analysisInfoDao.selectSalesProfAmtList(param);
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList() > selectSalesProfAmtList > busiProfAmtList(영업이익) > " + list2);
			bpResponse.setBusiProfAmtList(list2);

			// 영업이익&영업비용 조회
			param.setIncItm1("영업이익");
			param.setIncItm12("일반관리비");
			param.setIncItm2("--");
			List<BusiProfAmtInfo> list3 = (List<BusiProfAmtInfo>) analysisInfoDao.selectBusiProfExpAmtList(param);
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList() > selectBusiProfExpAmtList > busiProfExpAmtList(영업이익&영업비용) > " + list3);
			bpResponse.setBusiProfExpAmtList(list3);

			// ---------------------- 매출액누계, 영업이익누계, 영업이익&영업비용누계 조회 ------------------------
			// 매출액누계 조회
			param.setIncItm1("매출액");
			param.setIncItm2("--");
			List<BusiProfAmtInfo> list4 = (List<BusiProfAmtInfo>) analysisInfoDao.selectCumSalesProfAmtList(param);
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList() > selectCumSalesProfAmtList > cumSalesAmtList(매출액누계) > " + list4);
			bpResponse.setCumSalesAmtList(list4);
			
			// 영업이익누계 조회
			param.setIncItm1("영업이익");
			param.setIncItm2("--");
			List<BusiProfAmtInfo> list5 = (List<BusiProfAmtInfo>) analysisInfoDao.selectCumSalesProfAmtList(param);
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList() > selectSalesProfAmtList > cumBusiProfAmtList(영업이익누계) > " + list5);
			bpResponse.setCumBusiProfAmtList(list5);

			// 영업이익&영업비용 누계 조회
			param.setIncItm1("영업이익");
			param.setIncItm12("일반관리비");
			param.setIncItm2("--");
			List<BusiProfAmtInfo> list6 = (List<BusiProfAmtInfo>) analysisInfoDao.selectCumBusiProfExpAmtList(param);
			log.debug("AnalysisInfoServiceImpl.selectBusiProfList() > selectBusiProfExpAmtList > cumBusiProfExpAmtList(영업이익&영업비용누계) > " + list6);
			bpResponse.setCumBusiProfExpAmtList(list6);
			
//			log.debug("AnalysisInfoServiceImpl.selectBusiProfList > bpResponse > " + bpResponse);
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(bpResponse);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
	
	/**
	 * 거래처 매출 분석
	 */
	@Override
	public BaseResponse selectCustPerfList(CustPerfRequest param) {
		
		try {
			log.debug("AnalysisInfoServiceImpl.selectCustPerfList > param > " + param);
		
			List<CustPerfResponse> list = (List<CustPerfResponse>) analysisInfoDao.selectCustPerfList(param);
			  
			log.debug("AnalysisInfoServiceImpl.selectCustPerfList > list > " + list.toString());

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}





}
