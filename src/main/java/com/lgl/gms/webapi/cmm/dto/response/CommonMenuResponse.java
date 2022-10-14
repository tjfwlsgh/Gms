package com.lgl.gms.webapi.cmm.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * author 2022.03.14 yhlee
 * JsonIgnoreProperties(value = {"handler"})
 * 상기 어노테이션 없으면 clien로 내려 보낼때 No serializer found for class 에러가 남
 * subMenus와 같이 컬렉션으로 연결되도록 MyBatis에서 처리한 결과를 내려 보낼때 
 * Josn 파서가 serialization처리를 하도록 해야만 하는 것으로 추정
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class CommonMenuResponse {
	
	@JsonInclude(Include.NON_NULL)
	private String MenuCd;		// (대)메뉴 코드
	@JsonInclude(Include.NON_NULL)
	private String menuNm;		// (대)메뉴명(한글)
	@JsonInclude(Include.NON_NULL)
	private String menuNmEng;	// (대)메뉴명(영문)
	@JsonInclude(Include.NON_NULL)
	private String menuSeqCd;		// 대메뉴 정렬용 코드

	@JsonInclude(Include.NON_NULL)
	private String authCd;		// 권한코드
	
	@JsonInclude(Include.NON_NULL)
	List<CommonSubMenuResponse> subMenus;
}
