package com.lgl.gms.webapi.inf.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.inf.dto.request.InfFacilityMgmtRequest;
import com.lgl.gms.webapi.inf.dto.response.InfFacilityMgmtResponse;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;

public interface InfFacilityMgmtDao {

	public List<?> selectInfraFacilityMgmtList(Object body);

	public int deleteFacilityMgmt(Integer boRntId);

	public InfraRntModel selectInfraFacilityByRntId(Integer boRntId);

	public void insertFacilityMgmt(InfraRntModel param);

	public void updateFacilityMgmt(InfraRntModel param);


}
