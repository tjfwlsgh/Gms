package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.bse.dto.request.BseCloseRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoClsModel;
import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.inc.dto.request.BoIncRetRequest;
import com.lgl.gms.webapi.inc.persistence.model.BoIncPlnModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetHopayModel;
import com.lgl.gms.webapi.inc.persistence.model.BoIncRetModel;

public interface BseCloseDao {

	public List<?> selectCloseList(BseCloseRequest paramDto); 

	public List<?> selectBoList(BoRequest param); 

	public void procBseUpdClsMgmt(BseCloseRequest clsRequest); 

}
