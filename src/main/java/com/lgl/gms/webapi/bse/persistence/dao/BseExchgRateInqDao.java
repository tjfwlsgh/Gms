package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.bse.dto.request.BseExchgRateInqRequest;
import com.lgl.gms.webapi.bse.dto.response.BsePlnExchgRateInqResponse;
import com.lgl.gms.webapi.bse.persistence.model.BseExchgRateModel;

public interface BseExchgRateInqDao {

	public List<?> selectExchgRateInqList(Object paramDto);

	public void insertIncExpPlan(Map<String, Object> iudObj);
	
	public void insertPlnIncExpPlan(Map<String, Object> iudObj);

	public void deleteExchgRate(Map<String, Object> iudObj);
	
	public void deletePlnExchgRate(Map<String, Object> iudObj);

	public List<?> selectPlnExchgRateInqList(Object paramDto);

}
