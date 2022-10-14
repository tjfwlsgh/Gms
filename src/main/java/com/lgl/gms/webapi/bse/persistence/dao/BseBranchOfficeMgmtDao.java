package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.bse.persistence.model.BseBoModel;

public interface BseBranchOfficeMgmtDao {

	public List<?> selectBranchOfficeMgmtList(Object paramDto);

	public BseBoModel selectBranchOfficeByBoId(Integer boId);

	public void insertBranchOfficeMgmt(BseBoModel param);

	public int deleteBranchOfficeMgmt(Integer boId);

	public void updateBranchOfficeMgmt(BseBoModel param);
	
	public int countPboId(Integer boId);

	public BseBoModel selectBranchOfficeByBoCd(BseBoModel param);


}
