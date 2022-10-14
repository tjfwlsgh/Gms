package com.lgl.gms.webapi.sys.persistence.dao;

import java.util.List;

import com.lgl.gms.webapi.sys.dto.response.SvcTypResponse;
import com.lgl.gms.webapi.sys.dto.response.TccResponse;
import com.lgl.gms.webapi.sys.dto.response.TccValResponse;
import com.lgl.gms.webapi.sys.persistence.model.SvcTypModel;
import com.lgl.gms.webapi.sys.persistence.model.TccModel;
import com.lgl.gms.webapi.sys.persistence.model.TccValModel;

public interface CodeDao {

	// --- 코드유형 리스트 조회 ---
	public List<?> selectCodeTypeList(Object param);	// n건 조회
	public TccResponse selectCodeTypeOne(Object param);		// 1건 조회
	
	public int insertCodeType(TccModel model);
	public int updateCodeType(TccModel model);

	public int deleteCodeType(TccModel model);		// 삭제 flag 처리
	public int deleteCodeTypeReal(TccModel model);	// 실제 삭제 처리

	// --- 코드값 리스트 조회 ---
	public List<?> selectCodeValList(Object param);
	public TccValResponse selectCodeValOne(Object param);
	public int selectCodeValCnt(Object param);
	
	public int insertCodeVal(TccValModel model);
	public int updateCodeVal(TccValModel model);

	public int deleteCodeVal(TccValModel model);		// 삭제 flag 처리
	public int deleteCodeValReal(TccValModel model);	// 실제 삭제 처리
	
	// --- 서비스 유형 리스트 조회 ---
	public List<?> selectSvcTypeList(Object param);
	public SvcTypResponse selectSvcTypeOne(Object param);

	public int insertSvcType(SvcTypModel model);
	public int updateSvcType(SvcTypModel model);

	public int deleteSvcType(SvcTypModel model);		// 삭제 flag 처리
	public int deleteSvcTypeReal(SvcTypModel model);	// 실제 삭제 처리
	
}
