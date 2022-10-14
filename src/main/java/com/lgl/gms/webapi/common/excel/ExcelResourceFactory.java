package com.lgl.gms.webapi.common.excel;

import static  com.lgl.gms.webapi.common.util.ClassReflectionUtil.getAllFields;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import com.lgl.gms.webapi.common.util.MessageUtil;

public class ExcelResourceFactory {
	
	/**
	 * 메타데이터 생성 - ExcelColumn Annotation 으로 정의된 헤더스타일(정렬), 헤더명, 메모 등..
	 * @param type
	 * @param wb
	 * @return
	 */
	public static ExcelResource createExcelResouce(Class<?> type, Workbook wb) {
		
	    Map<String, String> hnMap = new LinkedHashMap<>();
	    List<String> fnames = new ArrayList<>();
	    Map<String, String> memoMap = new HashMap<>();
	    
	    Map<String, CellStyle> hStyleMap = new HashMap<String, CellStyle>();
	    Map<String, CellStyle> bStyleMap = new HashMap<String, CellStyle>();
	    Map<String, Integer> cWidthSizeMap = new HashMap<>();
	    
	    for (Field field : getAllFields(type)) {
	    	if( field.isAnnotationPresent(ExcelMemo.class) ) {
	    		ExcelMemo memonnotation = field.getAnnotation(ExcelMemo.class);	    		
	    		memoMap.put(memonnotation.targetField(), field.getName());
	    	}
	    	if ( field.isAnnotationPresent(ExcelColumn.class) ) {
	    		ExcelColumn columnAnnotation = field.getAnnotation(ExcelColumn.class);
	    		fnames.add(field.getName());
	    		
	    		String headerStr = StringUtils.defaultString(columnAnnotation.headerName());
	    		String headerKey = "";
	    		if(!"".equals(headerStr)) {
	    			String[] headerkeys = headerStr.split(",");
	    			headerKey = headerkeys[0];
	    			if(headerkeys.length > 1) {
	    				String[] args = new String[headerkeys.length-1];
	    				for(int idx = 1; idx < headerkeys.length; idx++) {
	    					args[idx-1] = headerkeys[idx];
	    				}
	    				hnMap.put(field.getName(), MessageUtil.getMessage(headerKey, args));
	    			} else {
	    				hnMap.put(field.getName(),MessageUtil.getMessage(headerKey));
	    			}
	    		}	    		
	    		
	    		hStyleMap.put(field.getName(), getHeaderStyle(wb, columnAnnotation.headerHorizonAlign()));
	    		bStyleMap.put(field.getName(), getBodyStyle(wb, columnAnnotation.bodyHorizonAlign()));
	    		cWidthSizeMap.put(field.getName(), columnAnnotation.columnWidth());
	    	}
	    }

	    if (hnMap.isEmpty()) {
	    	throw new NoExcelColumnAnnotationsException(String.format("Class %s has not @ExcelColumn at all", type));
	    }
	    
	    return new ExcelResource(hnMap, fnames, memoMap, hStyleMap, bStyleMap, cWidthSizeMap);
	    
	}
	
	/**
	 * 헤더 스타일
	 * @param wb
	 * @param headerHorizonAligns
	 * @return
	 */
	private static CellStyle getHeaderStyle(Workbook wb, HorizontalAlignment headerHorizonAligns) {
		CellStyle style = wb.createCellStyle(); // 셀 스타일 생성
		Font font = wb.createFont(); // 폰트 스타일 생성
	      	      
		// Header Style
		font.setBold(true);						 // 글자 진하게
		font.setFontHeight((short)(11*20)); // 글자 크기setFontHeight
		font.setFontName("맑은 고딕"); // 글씨체
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 수직 가운데 정렬
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
//		style.setWrapText(true);
		style.setFont(font);
		style.setAlignment(headerHorizonAligns);
		
		return style;
	}
	
	/**
	 * 바디스타일
	 * @param wb
	 * @param bodyHorizonAlign
	 * @return
	 */
	private static CellStyle getBodyStyle(Workbook wb, HorizontalAlignment bodyHorizonAlign) {
		
		CellStyle style = wb.createCellStyle(); // 셀 스타일 생성
		Font font = wb.createFont(); // 폰트 스타일 생성
	      	      
		// Body Style
//		style.setWrapText(true);
		style.setFont(font);
		style.setVerticalAlignment(VerticalAlignment.CENTER); // 수직 가운데 정렬
		style.setAlignment(bodyHorizonAlign);
		
		return style;
	}
	
}
