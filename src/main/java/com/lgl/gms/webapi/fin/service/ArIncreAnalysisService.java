package com.lgl.gms.webapi.fin.service;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.persistence.model.ArSmmryCustModel;

public interface ArIncreAnalysisService extends ExcelUploadService {

	public BaseResponse getArIncreAnalysisList(ArIncreAnalysisRequest paramDto);

	public BaseResponse doSaveArExcel(Map<String, Object> IUDObj);

	public BaseResponse deleteArIncreAnalysis(ArIncreAnalysisRequest paramDto);

}
