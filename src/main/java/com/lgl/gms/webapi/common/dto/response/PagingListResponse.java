package com.lgl.gms.webapi.common.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/*
 * 2022.03.04 
 *   - 서버 페이징을 처리할 때 PageingListResponse를 사용
 *   cf) 기본 페이징은 클라이언트에서 처리
 */
@Data
public class PagingListResponse {

	public PagingListResponse() {};
	
	public PagingListResponse(int totalCount, int totalPage) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
	}

	@JsonInclude(Include.NON_NULL)
	private Integer totalCount;
	@JsonInclude(Include.NON_NULL)
	private Integer totalPage;
	
	List<?> rows;
}
