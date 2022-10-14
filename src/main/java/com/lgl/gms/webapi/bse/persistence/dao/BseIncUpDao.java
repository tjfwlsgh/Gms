package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.bse.dto.request.BseBoCustMgmtRequest;
import com.lgl.gms.webapi.bse.dto.request.BseIncUpRequest;
import com.lgl.gms.webapi.bse.dto.response.BseIncUpResponse;
import com.lgl.gms.webapi.bse.persistence.model.BseBoCustModel;
import com.lgl.gms.webapi.bse.persistence.model.BseBoModel;

public interface BseIncUpDao {

	public List<?> selectIncRetList(BseIncUpRequest paramDto);

	public List<?> selectIncPlnList(BseIncUpRequest paramDto);

}
