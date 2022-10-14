package com.lgl.gms.webapi.sample.persistence.model;

import java.util.Date;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

//@DynamicInsert
//@DynamicUpdate
//@Entity(name = "tb_user")
@Data
public class UserSampleModel {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String pw;
	private String salt;
	private String username;
	private String phone;
	private String email;
	private String company;
	private String address;
	private String gender;
	private Date createdDt;
	private Date updatedDt;
	private Date pwChgDt;
	private String delYn;

	private String userType;
	private String refreshToken;
	private String lockYn;
	private Integer loginFailCnt;
}