package com.lgl.gms.webapi.cmm.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class BoResponse {

	private Integer boId;	// 법인ID
	
	@JsonInclude(Include.NON_NULL)
	private Integer pboId;	// 법인명
	
	@JsonInclude(Include.NON_NULL)
	private String boCd;		// 법인 코드
	
	@JsonInclude(Include.NON_NULL)
	private String boCl;		// 법인 구분
	
	private String boNm;		// 법인명
	
	private String boNmKo;		// 법인명(한글)
	
	@JsonInclude(Include.NON_NULL)
	private String boSnm;		// 법인 단축명
	
	@JsonInclude(Include.NON_NULL)
	private String boNmEng;		// 법인 명칭 영어
	
	@JsonInclude(Include.NON_NULL)
	private String boSnmEng;	// 법인 단축명 영어
	
	@JsonInclude(Include.NON_NULL)
	private String crncyCd;		// 통화코드
	
	@JsonInclude(Include.NON_NULL)
	private String cntryCd;		// 국가코드
	
	@JsonInclude(Include.NON_NULL)
	List<BoResponse> subBos;
	
	private Integer trrtId;		// 지역 ID
}
