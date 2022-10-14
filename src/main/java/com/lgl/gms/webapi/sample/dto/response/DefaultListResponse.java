package com.lgl.gms.webapi.sample.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class DefaultListResponse {

	public DefaultListResponse() {};
	
	public DefaultListResponse(int totalCount, int totalPage) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
	}

	@JsonInclude(Include.NON_NULL)
	private Integer totalCount;
	@JsonInclude(Include.NON_NULL)
	private Integer totalPage;
	
	List<?> rows;
}
