package com.lgl.gms.webapi.common.excel;

import static com.lgl.gms.webapi.common.util.ClassReflectionUtil.getField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SXSSFExcelFile<T> implements ExcelFile<T> {
	
	private static final Integer ROW_START_INDEX = 0;
	private static final Integer COLUMN_START_INDEX = 0;
	private static Integer COLUMN_END_INDEX = null;
	private int currentRowIndex = ROW_START_INDEX;
	
	private SXSSFWorkbook wb;
	private Sheet sheet;
	private ExcelResource excelResource;
	
	/**
	 * 데이터없이 param 클래스 type으로 헤더만 추가하여 객체생성
	 * @param type
	 */
	public SXSSFExcelFile(Class<T> type) {
		this(Collections.emptyList(), type);
	}

	/**
	 * param 클래스 type과 데이터로 헤더생성 및 Workbook 에 데이터추가 
	 * @param data
	 * @param type
	 */
	public SXSSFExcelFile(List<T> data, Class<T> type) {
		this(Collections.emptyList(), type, null);
	}
	
	public SXSSFExcelFile(List<T> data, Class<T> type, Integer colEndIndex) {
		this.COLUMN_END_INDEX = colEndIndex;
		this.wb = new SXSSFWorkbook();
		this.excelResource = ExcelResourceFactory.createExcelResouce(type, wb)	;
		renderExcel(data);
	}
	
	/**
	 * WorkBook Excel 파일 쓰고 ms-excel 컨텐츠 타입으로 retrun
	 * @return ResponseEntity
	 */
	@Override
	public ResponseEntity write() {
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		// Excel File Output
		try {
			wb.write(stream);
		} catch (IOException e) {
			log.error("excel download IOException => {}", e.getMessage() );
		}finally {			
			try {
				wb.close();
			} catch (IOException e) {
				log.error("excel wb.close IOException => {}", e.getMessage() );
			}
		}		
	      
		String contentType="application/vnd.ms-excel";
		
		return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType(contentType))
              .header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel")
//              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=")
              .body(stream.toByteArray());
	}
	
	/**
	 * Sheet 에 행모두 추가
	 * 렌터링시 속도가 느린경우 페이징하여 추가한다.
	 */
	@Override
	public void addRows(List<T> data) {
		renderBody(data, currentRowIndex++, COLUMN_START_INDEX);		
	}
	
	/**
	 * Sheet에 헤더와 param data 렌더링
	 * @param data
	 */
	public void renderExcel(List<T> data) {
		
		// 1. sheet 생성, 헤더 만들기
		sheet = wb.createSheet();
		renderHeadersWithNewSheet(sheet, currentRowIndex++, COLUMN_START_INDEX);

		if (data.isEmpty()) {
			return;
		}

		// 2. BODY 만들기
		for (Object renderedData : data) {
			renderBody(renderedData, currentRowIndex++, COLUMN_START_INDEX);
		}
	}

	/**
	 * 해당 Sheet에 헤더 추가
	 * @param sheet
	 * @param rowIndex
	 * @param columnStartIndex
	 */
	private void renderHeadersWithNewSheet(Sheet sheet, int rowIndex, int columnStartIndex) {
		Row row = sheet.createRow(rowIndex);
		
		int columnIndex = columnStartIndex;
		Integer columnEndIndex = this.COLUMN_END_INDEX;

		for (String dataFieldName : excelResource.getDataFieldNames()) {
			if(columnEndIndex != null && columnIndex > columnEndIndex && dataFieldName != "sum") {
				continue;
			}
			Cell cell = row.createCell(columnIndex);
			cell.setCellStyle(excelResource.getHeaderStyle(dataFieldName));
			cell.setCellValue(excelResource.getExcelHeaderName(dataFieldName));
			sheet.setColumnWidth(columnIndex, excelResource.getColumnWidthSize(dataFieldName));
			columnIndex++;
		}
	}
	
	/**
	 * Sheet에 해당 데이터 렌더링
	 * @param data
	 * @param rowIndex
	 * @param columnStartIndex
	 */
	private void renderBody(Object data, int rowIndex, int columnStartIndex) {
		Row row = sheet.createRow(rowIndex);
		int columnIndex = columnStartIndex;
		
		// 메모필드리스트
		Map<String, String> memoFieldMap = excelResource.getMemoFieldMap();
		
		for (String dataFieldName : excelResource.getDataFieldNames()) {
			
			try {
				Field field = getField(data.getClass(), dataFieldName);
				field.setAccessible(true);
				Object cellValue = field.get(data);
				
				if(cellValue == null) {
					continue;
				}
				
				Cell cell = row.createCell(columnIndex++);
				renderCellValue(cell, cellValue);				
				
				cell.setCellStyle(excelResource.getBodyStyle(dataFieldName));				

				// 메모
				if( memoFieldMap.get(dataFieldName) != null ) {
					
					Field memoField = getField(data.getClass(), memoFieldMap.get(dataFieldName));

					memoField.setAccessible(true);
					Object memoValue = memoField.get(data);
					
					if(StringUtils.isNotBlank( (String) memoValue) ) {
						Drawing drawing = cell.getSheet().createDrawingPatriarch();
						ClientAnchor anchor = drawing.createAnchor(0, 0, 1, 1, cell.getColumnIndex(), row.getRowNum(), cell.getColumnIndex()+3, row.getRowNum()+3);
						Comment comment = drawing.createCellComment(anchor);
						comment.setVisible(false);
						RichTextString textString = new XSSFRichTextString((String) memoValue);	//메모내용
						comment.setString(textString);
						cell.setCellComment(comment );
					}
				}
				
			} catch (Exception e) {
				throw new ExcelInternalException(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 해당 Cell 에 값 렌터링
	 * @param cell
	 * @param cellValue
	 */
	private void renderCellValue(Cell cell, Object cellValue) {
		if (cellValue instanceof Number) {
			Number numberValue = (Number) cellValue;
			cell.setCellValue(numberValue.doubleValue());
			return;
		}
		cell.setCellValue(cellValue == null ? "" : cellValue.toString());
	}
	

}
