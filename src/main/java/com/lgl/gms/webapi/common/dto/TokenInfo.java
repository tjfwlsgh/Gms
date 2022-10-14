package com.lgl.gms.webapi.common.dto;

import lombok.Data;

/**
 * 사용자 인증에 필요한 토큰 정보
 * cf) DB 데이블과 매핑되진 않음 
 */
@Data
public class TokenInfo {
	
	private String token;
	
	private String userId;
	private Integer compId;
	private Integer boId;
	private String authCd;
	private String workIp;
	
}
