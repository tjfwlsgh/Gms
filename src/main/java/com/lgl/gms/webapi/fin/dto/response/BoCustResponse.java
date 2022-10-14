package com.lgl.gms.webapi.fin.dto.response;

import lombok.Data;

@Data
public class BoCustResponse {
	
	private Integer boId;			// 법인 ID
	private String boCustCd;
	private String custNm;
	private String custSnm;
	private String custNmEng;
	private String custSnmEng;
	private String delYn;	

}