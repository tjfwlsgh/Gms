package com.lgl.gms.webapi.anl.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class BusiProfResponse {
		
	// -- 영업이익 추이 분석 
	List<?> salesAmtList; 			// 매출액
	List<?> busiProfAmtList; 		// 영업이익
	List<?> busiProfExpAmtList; 	// 영업이익&영업비용 
	
	List<?> cumSalesAmtList; 		// 매출액 누계
	List<?> cumBusiProfAmtList; 	// 영업이익 누계
	List<?> cumBusiProfExpAmtList; 	// 영업이익&영업비용 누계 
	
}
