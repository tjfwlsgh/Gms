package com.lgl.gms.config;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.util.UrlPathHelper;

import dev.akkinoc.util.YamlResourceBundle;

/**
 *
 * 다국어처리를 위한 설정
 * 로깅 필터
 * MvcInterceptor (인증처리) 설정
 *
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	// --- Interceptor 제외(우회) 경로 설정 --
	// token이 필요없는 접속 url, 로그인이나 refresh token 발행 처리가 속한 cmm과 error 제외
	private static final String[] EXCLUDE_PATHS = {
            "/",	// 접속 테스트 용도
			"/**/cmm/**",
            "/**/error/**"
// 모든 서비스 우회 처리, 테스트시 한시적 사용, 권한 처리 안됨. 
//          "/**"
    };
	
//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "https://dev.lglgms.com") 
//                // .exposedHeaders("Authorization")	//make client read header("Authorization")
//        ;
//    }
	
	/**
	 * responseBodyConverter는 결과를 출력시에 강제로 UTF-8로 설정하는 역할
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	// 특수문자 받기위한 설정
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setUrlDecode(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}

	/**
	 * AbstractRequestLoggingFilter를 상속한 request 로깅필터
	 */
	@Bean
	public RequestLoggingFilter requestLoggingFilter() {
		final RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
		requestLoggingFilter.setIncludeQueryString(true);
		requestLoggingFilter.setIncludeClientInfo(true);
		requestLoggingFilter.setIncludePayload(true);
		// 로깅 사이즈를 50k로 제한
		requestLoggingFilter.setMaxPayloadLength(51200);

		return requestLoggingFilter;
	}

	/**
	 * url 에 lang 값을 받아서 처리하는 경우에 사용함.
	 * 현재 프레임워크는 default로 AcceptHeaderLocaleResolver 사용함
	 * Header정보에 Accept-Language: ko 또는 en 으로 들어오는 경우에 처리함.
	 * lang 으로 넘겨받은 locale값을 LocaleContextHolder.setLocale(Locale.US 또는
	 * Locale.KOREA) 형태로 세팅
	 *
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/** ------------------------------------------------------------------------
	 * HttpServletRequest의 preHandle, postHandle, afterComppletion 처리를 위한
	 * DomainInterceptor를 등록한다.
	 * - MvcInteceptor 등록
	 -------------------------------------------------------------------------- */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(mvcInterceptor())
		  			.addPathPatterns("/**")
		  			.excludePathPatterns(EXCLUDE_PATHS);
	}

	/** ------------------------------------
	 * 인증 처리
	 * 
	 --------------------------------------- */
	@Bean
	public MvcInterceptor mvcInterceptor() {
		return new MvcInterceptor();
	}

	/**
	 * yml 파일로 메시지처리
	 *
	 * @param basename yml 파일 위치
	 * @param encoding 인코딩
	 * @return
	 */
	@Bean("messageSource")
	public MessageSource messageSource(
			@Value("${spring.messages.basename}") String basename,
			@Value("${spring.messages.encoding}") String encoding) {
		YamlMessageSource ms = new YamlMessageSource();
		ms.setBasename(basename);
		ms.setDefaultEncoding(encoding);
		ms.setAlwaysUseMessageFormat(true);
		ms.setUseCodeAsDefaultMessage(true);
		ms.setFallbackToSystemLocale(true);
		return ms;
	}

	/**
	 * default locale 설정
	 *
	 * @param locale
	 * @return
	 */
	@Bean("localeResolver")
	public LocaleResolver acceptHeaderLocaleResolver(@Value("${spring.web.locale}") String defaultLocale) {
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(Locale.forLanguageTag(defaultLocale));
		return resolver;
	}
}

class YamlMessageSource extends ResourceBundleMessageSource {
	@Override
	protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
		return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE);
	}
}