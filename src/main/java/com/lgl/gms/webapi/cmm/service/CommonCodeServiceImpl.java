package com.lgl.gms.webapi.cmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgl.gms.webapi.cmm.dto.request.CommonCodeRequest;
import com.lgl.gms.webapi.cmm.dto.response.AuthCodeResponse;
import com.lgl.gms.webapi.cmm.dto.response.CommonCodeResponse;
import com.lgl.gms.webapi.cmm.dto.response.CountryCodeResponse;
import com.lgl.gms.webapi.cmm.dto.response.CurrencyCodeResponse;
import com.lgl.gms.webapi.cmm.dto.response.LocaleCodeResponse;
import com.lgl.gms.webapi.cmm.persistence.dao.CommonCodeDao;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Transactional(transactionManager = "transactionManagerBatis")
@Slf4j
@Service
public class CommonCodeServiceImpl implements CommonCodeService {

	@Autowired
	private CommonCodeDao codeDao;
	
	/**
	 *  공통코드 리스트 조회
	 */
	@Override
	public BaseResponse selectCommonCodelist(CommonCodeRequest paramDto) {

		try {
// 2022.02.28 변경이력 요약
// 1. paramDto의 내용을 HashMap으로 다시 넣는 것은 CodeMapper에 parameter를 HashMap으로 정의했기 때문
//    parameterType을 CommonCodeRequest로 정의하면 바로 넘길 수 있음
				
//			HashMap<String, Object> param = new HashMap<String, Object>();
//			if (StringUtils.isBlank(body.getCodeType()) {
//				param.put("codeType", body.getCodeType());
//			}
			
			List<CommonCodeResponse> list = (List<CommonCodeResponse>) codeDao.selectCommonCodeList(paramDto);
			  
			System.out.println("CodeServiceImpl.selectCommonCodelist > list > " + list.toString());

// 2. DefaultListResponse는 PagingListResponse로 이름 변경
// List 기본은 바로 내려 보내고 paing과 같이 리스트와 같이 추가적인 정보가 필요한 경우
// PagingListResponse와 같이 추가로 만들어서 사용함
// 3. 리스트 조회의 경우는 바로 Response로 받아도 상관없어 보임
			// 4. model은 insert, update, delete일 경우 파라미터로 사용하기 위해 필요			
//			DefaultListResponse resData = new DefaultListResponse();
//			resData.setRows(list);

//			if(list != null) {
//				// 공통코드 리스트에 "---" 항목 추가 
//				CommonCodeResponse CommonCodeRec = new CommonCodeResponse();
//				
//				CommonCodeRec.setTccvId("");
//				CommonCodeRec.setStdCd("");
//				CommonCodeRec.setStdCdNm("---");
//				CommonCodeRec.setStdCdNmEng("---");
//				CommonCodeRec.setBseCd("");
//
//	            list.add(0, CommonCodeRec);
//	        }
			
			BaseResponse res = new BaseResponse(ResponseCode.C200);
// 			res.setData(resData);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	/**
	 *  권한코드 리스트 조회
	 */
	@Override
	public BaseResponse selectAuthCodelist() {

		try {
			
			List<AuthCodeResponse> list = (List<AuthCodeResponse>) codeDao.selectAuthCodeList();
			  
			System.out.println("CommonCodeServiceImpl.selectAuthCodelist > list > " + list.toString());

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}
	
	/**
	 *  통화코드 리스트 조회
	 */
	@Override
	public BaseResponse selectCurrencyCodelist() {

		try {
			
			List<CurrencyCodeResponse> list = (List<CurrencyCodeResponse>) codeDao.selectCurrencyCodeList();
			  
//			System.out.println("CodeServiceImpl.selectCurrencyCodelist > list > " + list.toString());

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	/**
	 *  국가코드 리스트 조회
	 */
	@Override
	public BaseResponse selectCountryCodelist() {

		try {
			
			List<CountryCodeResponse> list = (List<CountryCodeResponse>) codeDao.selectCountryCodeList();
			  
//			System.out.println("CodeServiceImpl.selectCurrencyCodelist > list > " + list.toString());

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

	/**
	 *  지역 언어코드 리스트 조회
	 */
	@Override
	public BaseResponse selectLocaleCodelist() {

		try {
			
			List<LocaleCodeResponse> list = (List<LocaleCodeResponse>) codeDao.selectLocaleCodeList();
			  
//			System.out.println("CodeServiceImpl.selectLocaleCodelist > list > " + list.toString());

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(list);
			
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}

	}

}
