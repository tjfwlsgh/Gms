package com.lgl.gms.webapi.inf.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.inf.dto.request.InfEquipmentMgmtRequest;
import com.lgl.gms.webapi.inf.dto.response.InfEquipmentMgmtResponse;
import com.lgl.gms.webapi.inf.persistence.model.InfraRntModel;

public interface InfEquipmentMgmtDao {

	public List<InfEquipmentMgmtResponse> selectInfraEquipmentMgmtList(InfEquipmentMgmtRequest paramDto);

	public InfraRntModel selectInfraEquipmentByRntId(Integer boRntId);

	public void insertEquipmentMgmt(InfraRntModel param);

	public int deleteEquipmentMgmt(Integer boRntId);

	public void updateEquipmentMgmt(InfraRntModel param);
	

}
