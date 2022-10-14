package com.lgl.gms.webapi.bse.service;

import com.lgl.gms.webapi.bse.dto.request.BseBoCustMgmtRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoCustModel;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface BseBoCustMgmtService {

	public BaseResponse getBoCustMgmt(BseBoCustMgmtRequest paramDto);

	public BaseResponse deleteBoCustMgmt(BseBoCustModel paramDto);

	public BaseResponse addBoCustMgmt(BseBoCustMgmtRequest paramDto);

	public BaseResponse updateBoCustMgmt(BseBoCustModel paramDto);

	public BaseResponse checkBoCustCd(BseBoCustModel paramDto);

}
