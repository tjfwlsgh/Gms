package com.lgl.gms.webapi.sys.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.sys.dto.request.PwdChangeRequest;
import com.lgl.gms.webapi.sys.dto.response.PwdChangeResponse;
import com.lgl.gms.webapi.sys.dto.response.UserResponse;
import com.lgl.gms.webapi.sys.persistence.model.UserModel;

public interface UserDao {

	// 공통 처리용(로그인, 로그아웃, 토큰 처리 등)
//	public UserModel selectUserById(String id);	
	public UserModel selectUserById(HashMap<?, ?> param);	

	// 사용자 관리용
	public List<?> selectUserList(HashMap<?, ?> param);
	
	public UserResponse selectUserOne(String id);	

	public int insertUser(UserModel model);

	public int updateUser(UserModel model);

	public int deleteUser(UserModel model);

	// password 변경
	public int updateUserPwd(UserModel model);
	
	// password 이력 조회
	public PwdChangeResponse selectUserPwdHistory(PwdChangeRequest param);
		
	// password 이력 최초 추가
	public int insertUserPwdHistory(UserModel model);
	
	// password 이력 변경
	public int updateUserPwdHistory(UserModel model);
	
	// password 변경 90일 연장
	public int updateUserPwdChgDelay(HashMap<?, ?> param);
	
}
