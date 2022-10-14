package com.lgl.gms.webapi.common.constant;

public class Constants {
	
	/**
	 * 유효성검사 정규식 정의
	 * @author jokim
	 * @date 2021.01.26
	 * @description : 유효성검사 PATTERN 정규식 정의
	 */
	public final class PATTERN_REGEX {
		/** 숫자만 가능 */
		public static final String NUM = "^\\d*$";
		/** 실수만 가능(숫자와 소수점) */
		public static final String FLOAT = "^(\\-|\\d)\\d*(\\.?\\d*)$|^$";
		/** 월(01-12) */
		public static final String MONTH = "^[1-9]{1}$|^[0]{1}[1-9]{1}$|^[1]{1}[0-2]{1}$";
		/** 날짜(2022-01-01 OR 20220101) */
		public static final String DATE = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$|(19|20)\\d{2}([- /.])((11|12)|(0?(\\d)))([- /.])(30|31|((0|1|2)?\\d))$";
		
	}


}
