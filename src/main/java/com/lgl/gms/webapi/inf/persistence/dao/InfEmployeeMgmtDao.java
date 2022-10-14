package com.lgl.gms.webapi.inf.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.inf.dto.request.InfEmployeeMgmtRequest;
import com.lgl.gms.webapi.inf.dto.response.InfEmployeeMgmtResponse;
import com.lgl.gms.webapi.inf.dto.response.InfEquipmentMgmtResponse;
import com.lgl.gms.webapi.inf.persistence.model.InfraBoEmpModel;

public interface InfEmployeeMgmtDao {

	public List<?> selectInfraEmployeeMgmtList(Object paramDto);

	public InfraBoEmpModel selectInfraEmployeeByEmpId(Integer boEmpId);

	public void insertEmployeeMgmt(InfraBoEmpModel param);

	public int deleteEmployeeMgmt(Integer boEmpId);

	public void updateEmployeeMgmt(InfraBoEmpModel param);

	public List<?> selectRegidentMgmtList(Object paramDto);

	public Integer selectBoIdByBoKey(String boKey);

	public int selectEmpTypIdByEmpTypKey(String empTypKey);

	public int insertExcelEmployeeMgmt(Map<String, Object> iUDObj);


	

}
