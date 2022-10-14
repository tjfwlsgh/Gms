package com.lgl.gms.webapi.common.util;

import static com.lgl.gms.webapi.common.util.ClassReflectionUtil.getField;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.multipart.MultipartFile;

import com.lgl.gms.webapi.common.constant.Constants;

import lombok.extern.slf4j.Slf4j;


/**
 * 엑셀 처리
 */
@Slf4j
public class ExcelUtil {
	
	private static String VALID_MSG_BASENAME="i18n/validationMessages";

    public static String getValue(Cell cell) {

        String value = null;
        if(cell == null) return null;
        
        switch (cell.getCellType()) {
            case STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell))
                    value = cell.getLocalDateTimeCellValue().toString();
                else
                	//value = String.format("%.2f", cell.getNumericCellValue());
                    value = NumberToTextConverter.toText(cell.getNumericCellValue());
                if (value.endsWith(".0"))
                    value = value.substring(0, value.length() - 2);

                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
//                value = String.valueOf(cell.getCellFormula());
            	
            	try {
            		value = NumberToTextConverter.toText(cell.getNumericCellValue());
				} catch (Exception e) {
					log.warn("Excel FORMULA getNumericCellValue error ==> {}", e.getMessage());
					try {
						value = String.valueOf(cell.getStringCellValue());
					} catch (Exception e1) {
						log.warn("Excel FORMULA getStringCellValue error ==> {}", e1.getMessage());
//						value = String.valueOf(cell.getCellFormula());
						value = "";
					}  
					
				}            	
            	
                break;
            case ERROR:
                value = ErrorEval.getText(cell.getErrorCellValue());
                break;
            case BLANK:
            case _NONE:
            default:
                value = "";
        }
        return value;
    }
    
    
    
    /**
     * String으로 변환된 Cell 데이터를 객체 필드에 세팅
     * @param object 요청 객체
     * @param <T>
     * @param row 엑셀 ROW 데이터
     * @return Cell 데이터를 맵핑한 오브젝트
     */
    public static<T> T setObjectMapping(T object, Row row) {

        int i = 0;

        if(Objects.isNull(object)) return null;
        Map<String, Object> errorMap = null;
        
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String cellValue = null;
//            TypeParser typeParser = TypeParser.newBuilder().build();
            try {
            	
            	if("errorColumns".equals(field.getName()) || "META_DATA".equals(field.getName())) {
            		continue;
            	}
            	
            	if("errorMsg".equals(field.getName())) {
            		ExcelUtil.setCellValue(object, row, i, field, errorMap);
            		break;
            	}
            	         	
                //if( i  < row.getPhysicalNumberOfCells() ) { 					//유효한 Cell 영역 까지만
            	if(row == null) break;
            	
	        	if( i  < row.getLastCellNum() ) {
	        		
	        		// dynamic한 필드를 지정하기 위하여 리스트로 필드를 만들어줌
	        		// 첫번째 필드가 meta, 두번째 필드가 value
	        		if(field.getType() == List.class) {
	            		
	            		Type type = field.getGenericType();
	            		
	            		if( type instanceof ParameterizedType) {

	            			Type pType1 = ((ParameterizedType) type).getActualTypeArguments()[0];
	
	            			List<T> inDataList = new ArrayList<T>();
	            			
	            			// 해당리스트의 메타데이터
	            			Field listField = getField(object.getClass(), "META_DATA");         			
	            			List<T> list = (List<T>) listField.get(object);
	            			
	            			// 메타데이터 데이터에 담긴 사이즈만큼 엑셀 Cell에서 값을 읽어 넣어줌
	            			for(T data : list) {

		            			if( i < row.getLastCellNum()) {
		            				
		            				Field metaField1 = data.getClass().getDeclaredFields()[0];
		            				Field metaField2 = data.getClass().getDeclaredFields()[1];
		            				metaField1.setAccessible(true);
		            				metaField2.setAccessible(true);
//		            				
		            				// 클래스를 가지고와 같은 클래스로 객체생성
		            				Class clazz = Class.forName(pType1.getTypeName());		            				
		            				Constructor cons = clazz.getDeclaredConstructor();
		            				T tempData = (T) cons.newInstance();
		            				
		            				Field field1 = tempData.getClass().getDeclaredFields()[0];
		            				Field field2 = tempData.getClass().getDeclaredFields()[1];
		            				
		            				field1.setAccessible(true);
		            				field2.setAccessible(true);
		            				// 메타 데이터 세팅
		            				field1.set(tempData, metaField1.get(data));
		            				
		            			// 해당 Cell의 값을 List 내의 VO의 두번째에 넣어줌		            				
		            				ExcelUtil.setCellValue(tempData, row, i, field2, errorMap);
		            				
		            				inDataList.add(tempData);
		            			}
		            			i++;
		            		}

	            			field.set(object, inDataList);
	            		}
		                
	            	} else {
	            		ExcelUtil.setCellValue(object, row, i, field, errorMap);
	            		i++;
	            	}
               		
	            }	
	        	
            } catch (IllegalArgumentException e) {
				log.error("setObjectMapping IllegalArgumentException ==> {}", e.getMessage());
			} catch (IllegalAccessException e) {
				log.error("setObjectMapping IllegalAccessException ==> {}", e.getMessage());
			} catch (Exception e) {
				log.error("setObjectMapping Exception ==> {}", e.getMessage());
			}           
            
        }

        return object;
    }
    
    /**
     * 해당 cell 값을 vo객체해 세팅
     * 유효성 검사 오류시에는 errorMsg 저장
     * @param <T>
     * @param object
     * @param row
     * @param i
     * @param field
     * @param errorMap
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static <T> void setCellValue(T object, Row row, int i, Field field, Map<String, Object> errorMap) throws IllegalArgumentException, IllegalAccessException {
    	
    	String cellValue = ExcelUtil.getValue(row.getCell(i));
    	
    	if( "errorMsg".equals(field.getName()) ) {
        	
        	errorMap = checkValidation(object, row, i, cellValue, field.getName());
        	
        	try {
				field.set(object, errorMap.get("errorMsg"));
				
//				List<String> errorColumns = (ArrayList<String>) errorMap.get("errorColumns");					
				Field errColField = getField(object.getClass(), "errorColumns");
				errColField.setAccessible(true);
				errColField.set(object, errorMap.get("errorColumns"));
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("checkValidation Exception ==> {}", e.getMessage());
			} catch (Exception e) {
				log.error("Exception checkValidation Exception ==> {}", e.getMessage());
			}
        	return;
        }

//        row.getCell(i).getCellComment();
        Pattern pattnAnno = field.getAnnotation(Pattern.class);
        if(pattnAnno != null) {
        	String regx =  pattnAnno.regexp();
            
            if(Constants.PATTERN_REGEX.FLOAT.equals(regx ) ) {
            	cellValue = cellValue == null ? null : cellValue.replaceAll(",", "");
            }
        }
        
		field.set(object, cellValue);
    	
    }
    
    /**
     * 객체에 대한 Validation 을 검증해서 검증을 통과 하지 못한 내역이 있을 경우 에러 리스트에 담는다
     * @param object
     * @param row
     * @param i
     * @param <T>
     */
    private static<T> Map<String, Object> checkValidation(T object, Row row, int i, String cellValue, String fieldName) {
//    	Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    	Locale.setDefault(LocaleContextHolder.getLocale());
    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(VALID_MSG_BASENAME);
        messageSource.setDefaultEncoding("UTF-8");
        
    	Validator validator = Validation.byDefaultProvider()
    	        .configure()
    	        .messageInterpolator(
    	                new ResourceBundleMessageInterpolator(
    	                        new MessageSourceResourceBundleLocator(messageSource)
    	                ) 
    	        )
    	        .buildValidatorFactory()
    	        .getValidator();
    
        Set<ConstraintViolation<T>> violations = validator.validate(object);
               
        Map<String, Object> errorMap = new HashMap<String, Object>();
        
        violations.forEach(violation -> {
//        	 log.debug("{} : {}", violation.getPropertyPath(), violation.getMessage());
        	String fName = violation.getPropertyPath().toString();
        	
        	String errorMsg = (String)errorMap.get("errorMsg");
 			errorMsg =  StringUtils.isBlank(errorMsg) ? "" : errorMsg + "\n";
 			
 			List<String> errorColumns = (ArrayList<String>) errorMap.get("errorColumns");
 			errorColumns = errorColumns == null ? new ArrayList<String>() : errorColumns;
 			errorColumns.add(fName); 			
 			
 			errorMap.put("errorMsg", errorMsg+MessageUtil.getMessage("field."+fName, null)+" : "+violation.getMessage());
 			errorMap.put("errorColumns", errorColumns);
        });
        
        return errorMap;
    }
    
    /**
     * 엑셀 파일의 데이터를 읽어서 요청한 오브젝트 타입 리스트에 담아 준다
     * @param multipartFile 엑셀 파일 데이터
     * @param rowFunc cell 데이터를 객체에 셋팅해주는 함수
     * @param <T> 요청 객체 타입
     * @return List<T> 요청 객체 타입의 리스트로 리턴
     * @throws Exception
     */
    public static <T> List<T> getObjectList(final MultipartFile multipartFile, final Function<Row, T> rowFunc) throws Exception {
        //헤더 데이터가 ROW가 0에 있고 실제 데이터의 시작 ROW가 1번째 부터인 것으로 판단
        return getObjectList(multipartFile, rowFunc, 1);
    }
    
    /**
     * 엑셀 파일의 데이터를 읽어서 요청한 오브젝트 타입 리스트에 담아 준다
     * @param multipartFile 엑셀 파일 데이터
     * @param rowFunc cell 데이터를 객체에 셋팅해주는 함수
     * @param startRow 데이터 시작 ROW (Default: 1)
     * @param <T> 요청 객체 타입
     * @return List<T> 요청 객체 타입의 리스트로 리턴
     * @throws Exception
     */
    public static <T> List<T> getObjectList(final MultipartFile multipartFile, final Function<Row, T> rowFunc, Integer startRow) throws Exception {

        return getObjectList(multipartFile, rowFunc, startRow, null);
    }
    
    /**
     * 엑셀 파일의 데이터를 읽어서 요청한 오브젝트 타입 리스트에 담아 준다
     * @param multipartFile 엑셀 파일 데이터
     * @param rowFunc cell 데이터를 객체에 셋팅해주는 함수
     * @param startRow 데이터 시작 ROW (Default: 1)
     * @param <T> 요청 객체 타입
     * @return List<T> 요청 객체 타입의 리스트로 리턴
     * @throws Exception
     */
    public static <T> List<T> getObjectList(final MultipartFile multipartFile, final Function<Row, T> rowFunc, Integer startRow, Integer rowCount) throws Exception {
    	// sheetNum 가 null 이면  0번째 sheet를 데이터를 읽는다
        return getObjectList(multipartFile, rowFunc, startRow, null, 0);
    }

    /**
     * 엑셀 파일의 데이터를 읽어서 요청한 오브젝트 타입 리스트에 담아 준다
     * @param multipartFile 엑셀 파일 데이터
     * @param rowFunc cell 데이터를 객체에 셋팅해주는 함수
     * @param startRow 데이터 시작 ROW (Default: 1)
     * @param rowCount 데이터 읽을 총 데이터 행수
     * @param sheetNum 데이터 읽을 sheet번호
     * @param <T> 요청 객체 타입
     * @return List<T> 요청 객체 타입의 리스트로 리턴
     * @throws EncryptedDocumentException 
     * @throws IOException
     */
    public static <T> List<T> getObjectList(final MultipartFile multipartFile, final Function<Row, T> rowFunc, Integer startRow, Integer rowCount, Integer sheetNum) throws EncryptedDocumentException, IOException {

    	// rownum 이 입력되지 않으면 default로 1 번째 라인을 데이터 시작 ROW로 판단
        if(Objects.isNull(startRow)) startRow = 1;

        // 엑셀 파일을 Workbook에 담는다
        final Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
        
        // 시트 수 (sheetNum가 null 이면 0번째를 읽는다)
        sheetNum = sheetNum == null ? 0 : sheetNum;
        final Sheet sheet = workbook.getSheetAt(sheetNum);
        // 전체 행 수
        rowCount = Objects.isNull(rowCount) ? sheet.getPhysicalNumberOfRows() : rowCount;
//        final int rowCount = sheet.getPhysicalNumberOfRows();
        log.debug("## rowCount = "+rowCount);

        List<T> objectList = IntStream
                .range(startRow, rowCount)
                .filter(rowIndex -> isPass(sheet.getRow(rowIndex)))
                .mapToObj(rowIndex -> rowFunc.apply(sheet.getRow(rowIndex)))
                .collect(Collectors.toList());

        return objectList;
    }

    /**
     * 해당 ROW에 있는 데이터가 모두 비어있으면 빈 ROW로 판단하고 해당 ROW는 PASS 시킨다
     * 해당 ROW에 있는 데이터 중 소계 가 있으면 해당 ROW는 PASS 시킨다
     * @param row
     * @return
     */
    private static boolean isPass(Row row) {
        int i =0;
        boolean isPass = false;
        
        if(row == null) return false;

//        while (i < row.getPhysicalNumberOfCells()) {
        while (i < row.getLastCellNum()) {
        	
        	String cellVal = StringUtils.defaultString(ExcelUtil.getValue(row.getCell(i++)));

            if(StringUtils.isNotEmpty(cellVal)) {
            	isPass = true;
            }                
            
            if((cellVal.replaceAll("\\s+","")).startsWith("소계")) {
            	isPass = false;
            	break;
            }

        }

//        log.debug("## row.getPhysicalNumberOfCells() = {}, isPass = {}",row.getPhysicalNumberOfCells(), isPass);
        return isPass;

    }

}
