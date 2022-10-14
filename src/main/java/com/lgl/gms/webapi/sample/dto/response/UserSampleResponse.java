package com.lgl.gms.webapi.sample.dto.response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.lgl.gms.api.persistence.model.UserModel;

import lombok.Data;

@Data
public class UserSampleResponse {
	@JsonInclude(Include.NON_NULL)
	private int id;
	@JsonInclude(Include.NON_NULL)
	private String username;
	@JsonInclude(Include.NON_NULL)
	private String email;

	@JsonInclude(Include.NON_NULL)
	private String company;
	@JsonInclude(Include.NON_NULL)
	private String address;
	@JsonInclude(Include.NON_NULL)
	private String gender;

	@JsonInclude(Include.NON_NULL)
	private String userType;

	@JsonInclude(Include.NON_NULL)
	private String phone;
	@JsonInclude(Include.NON_NULL)
	private String createdDt;
	@JsonInclude(Include.NON_NULL)
	private String updatedDt;

	public void setData(HashMap<String, Object> data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		ArrayList<String> keys = new ArrayList<String>(data.keySet());
		for (String key : keys) {
			String lKey = key.toLowerCase();
			if (lKey.equals("id")) {
				id = ((Integer) data.get(key)).intValue();
			} else if (lKey.equals("username")) {
				username = (String) data.get(key);
			} else if (lKey.equals("email")) {
				email = (String) data.get(key);
			} else if (lKey.equals("company")) {
				company = (String) data.get(key);
			} else if (lKey.equals("address")) {
				address = (String) data.get(key);
			} else if (lKey.equals("gender")) {
				gender = (String) data.get(key);
			} else if (lKey.equals("phone")) {
				phone = (String) data.get(key);
			} else if (lKey.equals("user_type")) {
				userType = (String) data.get(key);
			} else if (lKey.equals("created_date")) {
				createdDt = sdf.format((Date) data.get(key));
			} else if (lKey.equals("updated_date")) {
				updatedDt = sdf.format((Date) data.get(key));
			}
		}

	}

	/** 
	 * 2022.02.14 JPA 기능 제거
	 * 
	 * */
//	public void setDataJpa(UserModel data) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		id = data.getId();
//		username = data.getUsername();
//		email = data.getEmail();
//		company = data.getCompany();
//		address = data.getAddress();
//		gender = data.getGender();
//		phone = data.getPhone();
//		userType = data.getUserType();
//		createdDt = sdf.format(data.getCreatedDt());
//	}

	public UserSampleResponse() {
	}

}
