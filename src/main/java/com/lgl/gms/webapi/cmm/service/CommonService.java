package com.lgl.gms.webapi.cmm.service;

import com.lgl.gms.webapi.cmm.dto.request.CommonRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface CommonService {
	
	BaseResponse getLastExchgRateYm(CommonRequest param);

}
