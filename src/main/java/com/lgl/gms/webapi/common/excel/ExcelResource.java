package com.lgl.gms.webapi.common.excel;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelResource {
	
	private Map<String, String> excelHeaderNames;
	private List<String> dataFieldNames;
	private Map<String, String> memoFieldMap;
	private Map<String, CellStyle> headerStyleMap;
	private Map<String, CellStyle> bodyStyleMap;
	private Map<String, Integer> columnWidthSizeMap;
	
	public String getExcelHeaderName(String dataFieldName) {
		return StringUtils.isBlank(excelHeaderNames.get(dataFieldName)) ? dataFieldName : excelHeaderNames.get(dataFieldName);
	}
	
	public CellStyle getHeaderStyle(String dataFieldName) {
		return headerStyleMap.get(dataFieldName);
	}
	
	public CellStyle getBodyStyle(String dataFieldName) {
		return bodyStyleMap.get(dataFieldName);
	}
	
	public int getColumnWidthSize(String dataFieldName) {
		return columnWidthSizeMap.get(dataFieldName);
	}
	
}
