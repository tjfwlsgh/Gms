package com.lgl.gms.webapi.bse.service;

import com.lgl.gms.webapi.bse.dto.request.FormRequest;
import com.lgl.gms.webapi.bse.dto.request.IncItmDetRequest;
import com.lgl.gms.webapi.bse.dto.request.IncItmInfoRequest;
import com.lgl.gms.webapi.bse.persistence.model.IncItmDetModel;
import com.lgl.gms.webapi.bse.persistence.model.IncItmInfoModel;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface FormService {

	public BaseResponse getIncInfoList(FormRequest paramDto);

	public BaseResponse getIncDetailList(FormRequest paramDto);

	public BaseResponse addIncInfo(IncItmInfoRequest paramDto);

	public BaseResponse updateIncInfo(IncItmInfoModel paramDto);

	public BaseResponse deleteIncInfo(IncItmInfoModel paramDto);

	public BaseResponse addIncDetail(IncItmDetRequest paramDto);

	public BaseResponse deleteIncItmDetail(IncItmDetModel paramDto);

	public BaseResponse updateIncItmDetail(IncItmDetModel paramDto);

	public BaseResponse getGrp1List(FormRequest param);

}
