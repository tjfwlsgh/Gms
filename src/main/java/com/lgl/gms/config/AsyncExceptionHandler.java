package com.lgl.gms.config;

import java.lang.reflect.Method;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 비동기 처리 시, 오류처리 핸들러
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    /**
     * AsyncTask 에서 오류 발생 시 실행
     */

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.warn("==============>>>>>>>>>>>> THREAD Exception occurrence");

        log.warn("Exception Message :: {}", throwable.getMessage());
        log.warn("Method Name :: {}", method.getName());

        for (Object param : obj) {
            log.warn("Parameter Value :: {}", param);
        }

        log.warn("==============>>>>>>>>>>>> THREAD Exception END");
    }

}
