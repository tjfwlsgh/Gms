package com.lgl.gms.webapi.cmm.persistence.dao;

import java.util.List;

public interface CommonMenuDao {

	public List<?> selectMenuList(Object param);
	
	public List<?> selectAuthList(Object param);

}
