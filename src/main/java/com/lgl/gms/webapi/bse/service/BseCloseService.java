package com.lgl.gms.webapi.bse.service;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.bse.dto.request.BseCloseRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoClsModel;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface BseCloseService {

	BaseResponse getCloseList(BseCloseRequest paramDto);

	BaseResponse selectBoList(BoRequest param);

	BaseResponse updateCheckedCloser(Map<String, Object> iUDObj);

	BaseResponse updateBoClear(Map<String, Object> iUDObj);


}
