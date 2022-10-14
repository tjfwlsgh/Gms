package com.lgl.gms.webapi.inf.service;

import java.util.Map;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.inf.dto.request.InfEmployeeMgmtRequest;
import com.lgl.gms.webapi.inf.persistence.model.InfraBoEmpModel;

public interface InfEmployeeMgmtService extends ExcelUploadService {

	public BaseResponse getEmployeeMgmtList(InfEmployeeMgmtRequest paramDto);

	public BaseResponse addEmployeeMgmt(InfEmployeeMgmtRequest paramDto);

	public BaseResponse deleteEmployeeMgmt(InfEmployeeMgmtRequest paramDto);

	public BaseResponse modifyEmployeeMgmt(InfraBoEmpModel paramDto);

	public BaseResponse getRegidentMgmtList(InfEmployeeMgmtRequest paramDto);

	public BaseResponse doSaveEmployeeExcel(Map<String, Object> iUDObj);

}
