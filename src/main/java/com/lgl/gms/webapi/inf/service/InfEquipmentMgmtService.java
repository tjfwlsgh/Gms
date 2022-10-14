package com.lgl.gms.webapi.inf.service;

import javax.validation.Valid;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.inf.dto.request.InfEmployeeMgmtRequest;
import com.lgl.gms.webapi.inf.dto.request.InfEquipmentMgmtRequest;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;

public interface InfEquipmentMgmtService {

	BaseResponse getEquipmentMgmtList(InfEquipmentMgmtRequest paramDto);

	BaseResponse addEquipmentMgmt(InfEquipmentMgmtRequest paramDto);

	BaseResponse delete(InfEquipmentMgmtRequest paramDto);

	BaseResponse modifyEquipmentMgmt(InfraRntModel paramDto);

}
