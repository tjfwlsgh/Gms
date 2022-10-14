package com.lgl.gms.webapi.common.model;

import java.util.Date;

import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BaseModel {

	private String regNo;	
	private String updNo;
	private Date regDt;
	private Date updDt;
	private String workIp;

	// 2022.03.21 regDt, updDT는 MySQL DB의 시간 설정(서버 로케일시간)을 사용하므로 
	//            아래 초기화 메소드에서 제외함
	// regDt : CURRENT_TIMSTAMP를 사용
	// updDt : update query에 now()를 사용
	
	public void initRegModel() {
		this.regNo = UserInfo.getUserId();	// 로그인 사용자의 userId
		this.workIp = UserInfo.getWorkIp();	// 로그인 사용자의 workIp
	}
	
	public void initUpdModel() {
		this.updNo = UserInfo.getUserId();
		this.workIp = UserInfo.getWorkIp();	
	}
}
