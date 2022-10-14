package com.lgl.gms.webapi.fin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;
import com.lgl.gms.webapi.fin.dto.request.AccountsReceivableReuqest;
import com.lgl.gms.webapi.fin.persistence.model.ArSmmryModel;

public interface AccountsReceivableService extends ExcelUploadService {

	public BaseResponse getIncludingMarineList(AccountsReceivableReuqest paramDto);

	public BaseResponse getExcludingMarineList(AccountsReceivableReuqest paramDto);

	public BaseResponse doSaveExcludingExcel(Map<String, Object> exIUDObj);

	public BaseResponse doSaveIncludingExcel(Map<String, Object> inIUDObj);

	public BaseResponse delectAccountsRow(AccountsReceivableReuqest paramDto);


	

}
