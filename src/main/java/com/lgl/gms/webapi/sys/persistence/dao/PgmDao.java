package com.lgl.gms.webapi.sys.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.sys.dto.response.PgmResponse;
import com.lgl.gms.webapi.sys.persistence.model.PgmModel;

public interface PgmDao {

	// --- 프로그램 리스트 조회 ---
	public List<?> selectPgmList(HashMap<?,?> param);		// n건 조회
	public List<?> selectMenuList(HashMap<?,?> param);		// n건 조회
	public PgmResponse selectPgmOne(HashMap<?,?> param);	// 1건 조회
	
	public int insertPgm(PgmModel model);
	public int updatePgm(PgmModel model);

	public int deletePgm(HashMap<?,?> param);		// 삭제 flag 처리
	public int deletePgmeReal(HashMap<?,?> param);	// 실제 삭제 처리

	
}
