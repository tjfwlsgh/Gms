package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.bse.dto.request.BseBoCustMgmtRequest;
import com.lgl.gms.webapi.bse.persistence.model.BseBoCustModel;
import com.lgl.gms.webapi.bse.persistence.model.BseBoModel;

public interface BseBoCustMgmtDao {

	public List<?> selectBoCustMgmtList(Object paramDto);

	public void insertBoCustMgmt(BseBoCustModel param);

	public int deleteBoCustMgmt(BseBoCustModel paramDto);

	public void updateBoCustMgmt(BseBoCustModel param);

	public BseBoCustModel selectBoCustByIdAndCd(BseBoCustMgmtRequest paramDto);

	public BseBoCustModel checkSelectBoCd(BseBoCustModel paramDto);
	

}
