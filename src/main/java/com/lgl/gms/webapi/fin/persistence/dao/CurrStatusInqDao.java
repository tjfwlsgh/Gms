package com.lgl.gms.webapi.fin.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.cmm.dto.request.BoRequest;
import com.lgl.gms.webapi.cmm.dto.response.BoResponse;
import com.lgl.gms.webapi.fin.dto.request.FinCurrStatusInqRequest;

public interface CurrStatusInqDao {

	List<?> selectBalanSheetList(FinCurrStatusInqRequest paramDto);

	List<?> selectBoListByCurr(BoRequest param);

	List<?> selectIncStatementList(FinCurrStatusInqRequest paramDto);

}
