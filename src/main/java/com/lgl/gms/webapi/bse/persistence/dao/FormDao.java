package com.lgl.gms.webapi.bse.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.bse.dto.request.FormRequest;
import com.lgl.gms.webapi.bse.persistence.model.IncItmDetModel;
import com.lgl.gms.webapi.bse.persistence.model.IncItmInfoModel;

public interface FormDao {

	public List<?> selectIncInfoList(FormRequest paramDto);

	public List<?> selectIncDetailList(FormRequest paramDto);

	public IncItmInfoModel selectIncItmInfoByIncItmId(Integer incItmId);

	public void insertIncItmInfo(IncItmInfoModel param);

	public void updateIncItmInfo(IncItmInfoModel param);

	public int deleteIncItmInfo(Integer incItmId);

	public void insertIncItmDetail(IncItmDetModel param);

	public IncItmDetModel selectIncItmDetByIncItmDetId(Integer incItmDetId);

	public void updateIncItmDetail(IncItmDetModel param);

	public int deleteIncItmDetail(Integer incItmDetId);

	public List<FormRequest> selectGrp1List(FormRequest param);



}
