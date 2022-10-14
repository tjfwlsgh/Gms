package com.lgl.gms.webapi.bse.service;

import com.lgl.gms.webapi.bse.dto.request.BseBranchOfficeMgmtRequest;
import com.lgl.gms.webapi.bse.dto.request.BseIncUpRequest;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface BseIncUpService {

	public BaseResponse getIncRetList(BseIncUpRequest paramDto);

	public BaseResponse getIncPlnList(BseIncUpRequest paramDto);

}
