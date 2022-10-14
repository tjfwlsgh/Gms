package com.lgl.gms.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * Log Filter 설정 API 로그
 * 이벤트 메시지에 문자(<==, ==> ) 포함여부를 확인하여 필터링
 * 실제 적용여부는 logback.xml 에서 사용
 * ex)
 * <filter class="com.lgl.gms.api.config.ExceptLogFiilter" />
 */
public class ExceptLogFiilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getMessage().contains("<==") || event.getMessage().contains("==>")) {
			return FilterReply.DENY;
		} else {
			return FilterReply.ACCEPT;
		}
	}
}