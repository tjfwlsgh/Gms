package com.lgl.gms.webapi.sample.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class UserSampleRequest {

	private String id;
	private String prePw;
	private String pw;
	private String username;
	private String phone;
	private String email;
	private String company;
	private String address;
	private String gender = "M";
	private Date createdDt;
	private Date updatedDt;
	private String delYn = "N";

	// 조회파라미터
	public String page;
	public String listSize;
	public String sDate;
	public String eDate;
	public String searchKey;
	public String keyword;
}
