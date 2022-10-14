package com.lgl.gms.webapi.fin.service;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.request.FinCurrStatusInqRequest;

public interface CurrStatusInqService {

	BaseResponse getBalanSheetList(FinCurrStatusInqRequest paramDto);

	BaseResponse getBoList(BoRequest param);

	BaseResponse getIncStatementList(FinCurrStatusInqRequest paramDto);

}
