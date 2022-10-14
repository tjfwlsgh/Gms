package com.lgl.gms.webapi.sys.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.sys.dto.response.AuthResponse;
import com.lgl.gms.webapi.sys.dto.response.PgmAuthResponse;
import com.lgl.gms.webapi.sys.dto.response.PgmResponse;
import com.lgl.gms.webapi.sys.persistence.model.AuthModel;
import com.lgl.gms.webapi.sys.persistence.model.PgmAuthModel;

public interface PgmAuthDao {

	// --- Auth 코드 관리 ---
	public List<AuthResponse> selectAuthList(HashMap<?,?> param);		// n건 조회
	public AuthResponse selectAuthOne(HashMap<?,?> param);	// 1건 조회
	public int insertAuth(AuthModel model);
	public int updateAuth(AuthModel model);
	public int deleteAuth(HashMap<?,?> param);		// 삭제 flag처리
	public int deleteAutheReal(HashMap<?,?> param);	// 실제 삭제 처리

	// --- Auth와 메뉴그룹에 속한 프로그램별 권한 관리 ---
	public List<PgmAuthResponse> selectPgmAuthList(HashMap<?,?> param);		
	public int selectPgmAuthCnt(HashMap<?,?> param);		

	public int insertPgmAuth(PgmAuthModel model);	// 저장
	public int updatePgmAuth(PgmAuthModel model);	// 수정
//	public int deletePgmAuth(HashMap<?,?> param);		// 삭제 flag처리
	public int deletePgmAuthReal(PgmAuthModel model);	// 실제 삭제 처리
	
}
