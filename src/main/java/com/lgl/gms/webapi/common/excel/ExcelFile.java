package com.lgl.gms.webapi.common.excel;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ExcelFile<T> {
	
	/**
	 * 데이터 WorkBook 에 쓰고ResponseEntity 리턴 함수 정의
	 * @return
	 * @throws IOException
	 */
	ResponseEntity<?> write() throws IOException;

	/**
	 * WorkBook Sheet 에 데이터 행추가 함수 저으이
	 * @param data
	 */
	void addRows(List<T> data);

}
