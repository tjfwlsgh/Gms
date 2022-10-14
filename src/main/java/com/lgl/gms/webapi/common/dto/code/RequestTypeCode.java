package com.lgl.gms.webapi.common.dto.code;
public enum RequestTypeCode {
		POST("POST"), GET("GET"), PUT("PUT"), DELETE("DELETE");

		private String value;

		RequestTypeCode(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}