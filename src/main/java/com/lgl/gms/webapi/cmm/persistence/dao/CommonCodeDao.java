package com.lgl.gms.webapi.cmm.persistence.dao;

import java.util.List;

public interface CommonCodeDao {

	// 공통코드 리스트 조회
	public List<?> selectCommonCodeList(Object param);
	
	// 권한코드 리스트 조회
	public List<?> selectAuthCodeList();
	
	// 통화코드 리스트 조회
	public List<?> selectCurrencyCodeList();
	
	// 국가코드 리스트 조회
	public List<?> selectCountryCodeList();

	// 지역언어코드 리스트 조회
	public List<?> selectLocaleCodeList();

}
