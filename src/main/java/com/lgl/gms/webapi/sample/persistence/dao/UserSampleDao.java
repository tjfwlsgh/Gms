package com.lgl.gms.webapi.sample.persistence.dao;

import java.util.HashMap;
import java.util.List;

import com.lgl.gms.webapi.sample.persistence.model.UserSampleModel;

public interface UserSampleDao {

	public UserSampleModel selectUserByEmail(String email);

	public UserSampleModel selectUserById(String id);

	public int selectUserListCount(HashMap<?, ?> param);

	public List<?> selectUserList(HashMap<?, ?> param);

	// For Admin(include deleted users)
	public int selectUserListAllCount(HashMap<?, ?> param);

	// For Admin(include deleted users)
	public List<?> selectUserListAll(HashMap<?, ?> param);

	public int insertUser(UserSampleModel model);

	public int updateUserPw(UserSampleModel model);

	public int updateUser(UserSampleModel model);

	public int deleteUser(String id);

}
