package com.lgl.gms.webapi.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 메시지 다국어처리 유틸
 */
@Component
public class MessageUtil {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    /**
     * ResponseCode에 해당하는 메시지 출력
     *
     * @param code
     * @return
     */
    public static String getResponseMessage(String code) {
        return MessageUtil.messageSource.getMessage("response.C" + code, null,
                LocaleContextHolder.getLocale());
    }

    /**
     * 다국어메시지 가져오기(locale은 header정보에서 가져와서 처리)
     * header에 "Access_Language"
     *
     * @param key
     * @param args
     * @return
     */
    public static String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return MessageUtil.messageSource.getMessage(key, args, locale);
    }

}
