package com.lgl.gms.webapi.cmm.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.cmm.persistence.model.LoginLogModel;
import com.lgl.gms.webapi.sys.dto.response.PgmResponse;
import com.lgl.gms.webapi.sys.persistence.model.PgmModel;

public interface LoginLogDao {

	// --- 로그인 로그 리스트 조회 ---
	public List<?> selectLoginLogList(HashMap<?,?> param);	// n건 조회
	
	public int insertLoginLog(LoginLogModel model);
	
	// 로그이므로 아래 수정 및 삭제는 필요없음
	public int updateLoginLog(LoginLogModel model);
	public int deleteLoginLogReal(HashMap<?,?> param);	// 실제 삭제 처리

}
