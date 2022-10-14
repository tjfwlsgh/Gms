package com.lgl.gms.webapi.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 로그 생성시 사용할 키 생성
 */
final public class LogKeyUtil {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);
	private static Random rnd = new Random();

	public static String getRandomKey() {
		return String.format("LOGKEY_%s_%s", SDF.format(new Date()), getRand());
	}

	private static String getRand() {
		final StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			if (rnd.nextBoolean()) {
				buf.append((char) ((int) (rnd.nextInt(26)) + 65));
			} else {
				buf.append(rnd.nextInt(10));
			}
		}
		return buf.toString().toUpperCase();
	}
}
