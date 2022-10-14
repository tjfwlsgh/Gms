package com.lgl.gms.webapi.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 로그 생성 시, 출력에 필요한 데이터들 추출
 */
public class LogParamUtil {

	private static final SimpleDateFormat STR_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA);
	private static final Gson LOG_GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
	private static final Gson LOG_GSON_NP = new GsonBuilder().disableHtmlEscaping().create();

	public static String getPrintParamMsg(String param) {
		if (param == null) {
			return "{}";
		}
		return LOG_GSON.toJson(param);
	}

	public static String getPrintParamMsg(String param, Class<?> c) {
		if (param == null) {
			return "{}";
		}
		return LOG_GSON.toJson(LOG_GSON.fromJson(param, c));
	}

	public static String getPrintParamMsg(ArrayList<?> list) {
		return LOG_GSON.toJson(list);
	}

	public static String getPrintParamMsgNotPretty(Object obj) {
		if (obj == null) {
			return "{}";
		}
		return LOG_GSON_NP.toJson(obj);
	}

	public static String getPrintParamMsg(Object obj) {
		if (obj == null) {
			return "{}";
		}
		return LOG_GSON.toJson(obj);
	}

	public static String getTimeFormat(long time) {
		return STR_FORMAT.format(new Date(time));
	}

	public static String getCheckTime(long startTime) {
		long endTime = System.currentTimeMillis();
		return "" + (endTime - startTime) / 1000.0f;

	}

	public static long getCheckTimeByMillSec(long startTime) {
		long endTime = System.currentTimeMillis();
		return (long) ((endTime - startTime));

	}

	public static long getCheckTimeByMinPlus(long startTime) {
		long endTime = System.currentTimeMillis();
		return (long) (((endTime - startTime) + 100) / 1000.0f / 60);

	}
}
