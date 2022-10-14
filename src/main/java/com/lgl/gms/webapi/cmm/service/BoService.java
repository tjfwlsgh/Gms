package com.lgl.gms.webapi.cmm.service;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface BoService {
	
	BaseResponse selectBoList(BoRequest param);

}
