package com.lgl.gms.webapi.cmm.persistence.dao;

import com.lgl.gms.webapi.cmm.dto.request.CommonRequest;

public interface CommonDao {
	
	/**
	 * 최종환율년월 가지고오기 (실적용)
	 * @param param
	 * @return
	 */
	public String getLastExchgRateYm(CommonRequest param);	

}
