package com.lgl.gms.webapi.sys.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class PwdChangeResponse {

	 private Integer userNo       ;
	 private String userPwd       ;
	 private Integer pwdFailCnt   ;
	 private String firLoginChg_yn;
	 private Date firLoginDt      ;
	 private String userPwd1      ;
	 private String userPwd2      ;
	 private String userPwd3      ;
	 private Date finlPwdUpddt   ;
	 private Date pwdChgDt        ;
	 
	 private String regNo          ;
	 private String updNo          ;
	 private Date regDt            ;
	 private Date updDt            ;
	 private String workIp         ;
	
}
