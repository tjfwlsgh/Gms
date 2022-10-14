package com.lgl.gms.webapi.common.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
	
	String headerName() default "";
	HorizontalAlignment headerHorizonAlign() default HorizontalAlignment.CENTER;
	HorizontalAlignment bodyHorizonAlign() default HorizontalAlignment.JUSTIFY;
	int columnWidth() default 3560;
}
