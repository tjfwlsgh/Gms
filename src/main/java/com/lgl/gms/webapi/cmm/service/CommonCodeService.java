package com.lgl.gms.webapi.cmm.service;

import com.lgl.gms.webapi.cmm.dto.request.CommonCodeRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;


public interface CommonCodeService {
	
	BaseResponse selectCommonCodelist(CommonCodeRequest body);

	BaseResponse selectAuthCodelist();
	
	BaseResponse selectCurrencyCodelist();

	BaseResponse selectCountryCodelist();
	
	BaseResponse selectLocaleCodelist();


}
