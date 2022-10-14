package com.lgl.gms.webapi.bse.service;

import com.lgl.gms.webapi.bse.dto.request.BseBranchOfficeMgmtRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoModel;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;

public interface BseBranchOfficeMgmtService {

	public BaseResponse getBranchOfficeMgmt(BseBranchOfficeMgmtRequest paramDto);

	public BaseResponse addBranchOfficeMgmt(BseBranchOfficeMgmtRequest paramDto);

	public BaseResponse deleteBranchOfficeMgmt(BseBranchOfficeMgmtRequest paramDto);

	public BaseResponse modifyBranchOfficeMgmt(BseBoModel paramDto);

	public BaseResponse selectPboCount(BseBoModel param);

	public BaseResponse checkBoCd(BseBoModel param);

	


}
