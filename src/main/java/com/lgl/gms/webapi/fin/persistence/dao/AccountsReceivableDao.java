package com.lgl.gms.webapi.fin.persistence.dao;

import java.util.List;
import java.util.Map;

import com.lgl.gms.webapi.fin.dto.request.AccountsReceivableReuqest;

public interface AccountsReceivableDao {

	public List<?> selectIncludingMarineList(AccountsReceivableReuqest paramDto);

	public List<?> selectExcludingMarineList(AccountsReceivableReuqest paramDto);

	public void deleteExcludingMarine(AccountsReceivableReuqest param);

	public void insertAccountsExcel(Map<String, Object> inIUDObj);

	public void deleteAccounts(AccountsReceivableReuqest param);

	public int delectAccountsRow(AccountsReceivableReuqest paramDto);

}
