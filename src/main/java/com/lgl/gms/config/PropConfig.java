package com.lgl.gms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * yml 에서 property값 wrapper
 */
@Component
@Data
public class PropConfig {

	public String localIp;

	@Value("${server.enc-key}")
	String encKey;

	@Value("${server.aes-key}")
	String aesKey;

	@Value("${server.jwt-key}")
	String jwtKey;

	@Value("${server.documentPath}")
	String documentPath;

	@Value("${server.log-response-payload-debug}")
	Integer isLogResPLPrint;
	@Value("${server.log-response-payload-debug}")
	Integer isLogReqPLPrint;
	//
	// @Value("${server.session-ext-time}")
	// Integer sessExtTime;

	@Value("${server.jwt-access-time}")
	Integer jwtAccessTime;

	@Value("${server.jwt-refresh-time}")
	Integer jwtRefreshTime;

	@Value("${spring.web.locale}")
	String defaultLocale;

}