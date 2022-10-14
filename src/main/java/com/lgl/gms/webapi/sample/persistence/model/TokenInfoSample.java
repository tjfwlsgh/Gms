package com.lgl.gms.webapi.sample.persistence.model;

import lombok.Data;

/**
 * DB 데이틀과 매핑되진 않으며, 사용자인증에 처리할 모델
 */
@Data
public class TokenInfoSample {
	private String token;
	private Integer id;
	private String email;
	private String userType; // 사용자구분
}
