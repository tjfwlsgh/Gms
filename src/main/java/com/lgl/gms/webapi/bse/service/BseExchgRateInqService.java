package com.lgl.gms.webapi.bse.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.bse.dto.request.BseExchgRateInqRequest;
import com.lgl.gms.webapi.bse.dto.request.BsePlnExchgRateInqRequest;
import com.lgl.gms.webapi.bse.dto.response.BseExchgRateInqExcelResponse;
import com.lgl.gms.webapi.bse.persistence.model.BseExchgRateModel;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.service.ExcelUploadService;

public interface BseExchgRateInqService extends ExcelUploadService  {

	BaseResponse getExchgRateInqList(BseExchgRateInqRequest paramDto);

	BaseResponse saveExchgRate(Map<String, Object> IudObj);

	BaseResponse excelUpload(MultipartFile file, Object body);

	BaseResponse getPlnExchgRateInqList(BsePlnExchgRateInqRequest paramDto);


}
