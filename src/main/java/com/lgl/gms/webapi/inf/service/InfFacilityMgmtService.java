package com.lgl.gms.webapi.inf.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inf.dto.request.InfFacilityMgmtRequest;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;


public interface InfFacilityMgmtService { // extends ExcelUploadService

	public BaseResponse getFacilityMgmtList(InfFacilityMgmtRequest body);
	
	public BaseResponse addFacilityMgmt(InfFacilityMgmtRequest body);
	
	public BaseResponse delete(InfFacilityMgmtRequest body);

	public BaseResponse modifyFacilityMgmt(InfraRntModel paramDto);


}
