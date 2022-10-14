package com.lgl.gms.config;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 사용자 권한 구분 
 * 2022-03-02 화면단에서 권한에 따라 호출을 제어하므로 사용안함
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Auth {
	public enum Role {
		ADMIN, USER
	}

	public Role[] role();
}
