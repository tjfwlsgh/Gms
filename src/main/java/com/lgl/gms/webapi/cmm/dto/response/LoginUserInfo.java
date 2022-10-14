package com.lgl.gms.webapi.cmm.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginUserInfo {

	@JsonInclude(Include.NON_NULL)
	private String userId;
	@JsonInclude(Include.NON_NULL)
	private String userNm;
	@JsonInclude(Include.NON_NULL)
	private String userNmEng;
	
	@JsonInclude(Include.NON_NULL)
	private Integer compId;
	@JsonInclude(Include.NON_NULL)
	private Integer boId;

	@JsonInclude(Include.NON_NULL)
	private String cntryCd;

	@JsonInclude(Include.NON_NULL)
	private String userTyp;
	@JsonInclude(Include.NON_NULL)
	private String authCd;
	
	@JsonInclude(Include.NON_NULL)
	private Date regDt;
	@JsonInclude(Include.NON_NULL)
	private Date updDt;

	@JsonInclude(Include.NON_NULL)	
	private String useLang;
	
	private String pwdChgDt;		// 비밀번호 변경(예정)일
	private String pwdChgMustYn;	// 비밀번호 변경 강제 여부

	@JsonInclude(Include.NON_NULL)
	private String hqYn;	// 본사여부
}
