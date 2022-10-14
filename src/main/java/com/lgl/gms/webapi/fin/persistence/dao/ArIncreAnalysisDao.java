package com.lgl.gms.webapi.fin.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.fin.dto.request.ArIncreAnalysisRequest;
import com.lgl.gms.webapi.fin.dto.response.BoCustResponse;

public interface ArIncreAnalysisDao {

	public List<?> selectArIncreAnalysisList(ArIncreAnalysisRequest paramDto);
	
	public int insertArSmmryCust(Map<String, Object> IUDObj);

	public Integer selectBoId(String boNm);

	public int deleteAnalysis(ArIncreAnalysisRequest paramDto);

	public BoCustResponse selectBoCustByCustNm(String custNm);

	public void deleteArSmmryCust(ArIncreAnalysisRequest param);

	public String selectTrrtByBoNm(String boNm);


}
