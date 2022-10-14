package com.lgl.gms.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.lgl.gms.webapi.common.dto.code.RequestTypeCode;
import com.lgl.gms.webapi.common.util.LogKeyUtil;
import com.lgl.gms.webapi.common.util.LogParamUtil;

import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * INFO로 전송/응답에 대한건 PARAM까지 찍게 함
 * Request/Response 로그처리 (db-log, api-log로 분리)
 * 로그파일 저장 경로는 logs
 */
@Slf4j
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	@Autowired
	PropConfig prop;
	private final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

	private final LogMaskFilter maskFilter = new LogMaskFilter();

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {

		request.setAttribute("API_TIME", Calendar.getInstance().getTimeInMillis());
		try {
			RequestInfo info = gson.fromJson(message, RequestInfo.class);
			log.info("[{}][{}][{}] [BEFORE_REQ]", info.getUrl(), info.getRemoteAddr(), info.getMethod());
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		try {
			RequestInfo info = gson.fromJson(message, RequestInfo.class);
			boolean isMultipart = false;

			if (request.getContentType() != null && request.getContentType().toLowerCase().startsWith("multipart")) {
				isMultipart = true;
			}

			log.debug("[{}][{}][{}] [AFTER_REQ] [{} sec]", info.getUrl(), info.getRemoteAddr(), info.getMethod(),
					LogParamUtil.getCheckTime((Long) request.getAttribute("API_TIME")));

			if (!request.getMethod().equals(RequestTypeCode.PUT.value()) &&
					!request.getMethod().equals(RequestTypeCode.GET.value()) && !isMultipart) {

				String body = maskFilter.reqMaskFilter(info.getUrl(), info.getRemoteAddr(), info.getMethod(),
						info.getPayload());

				if (prop.getIsLogReqPLPrint() == 1) {
					log.debug("[{}][{}][{}] [REQ_PAYLOAD] {}", info.getUrl(), info.getRemoteAddr(), info.getMethod(),
							body);
				} else {
					log.info("[{}][{}][{}] [REQ_PAYLOAD] {}", info.getUrl(), info.getRemoteAddr(), info.getMethod(),
							body);
				}
			}

			if (isMultipart) {
				if (prop.getIsLogReqPLPrint() == 1) {
					log.debug("[{}][{}][{}] [REQ_PAYLOAD] multipart", info.getUrl(), info.getRemoteAddr(),
							info.getMethod());
				} else {
					log.info("[{}][{}][{}] [REQ_PAYLOAD] multipart", info.getUrl(), info.getRemoteAddr(),
							info.getMethod());
				}
			}

			request.removeAttribute("API_TIME");
			request.removeAttribute("LOG_KEY");
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String logKey = LogKeyUtil.getRandomKey();
		MDC.put("LOG_KEY", logKey);
		request.setAttribute("LOG_KEY", logKey);

		boolean isFirstRequest = !isAsyncDispatch(request);
		HttpServletRequest requestToUse = request;

		if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
			requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
		}

		boolean shouldLog = shouldLog(requestToUse);
		if (shouldLog && isFirstRequest) {
			beforeRequest(requestToUse, createMessage(requestToUse, "", ""));
		}
		try {
			HttpServletResponse responseToUse = new ResponseWrapper(response);
			filterChain.doFilter(requestToUse, responseToUse);
			if (responseToUse instanceof ResponseWrapper) {
				ResponseWrapper wrapper = (ResponseWrapper) responseToUse;
				String res = new String(wrapper.toByteArray(), responseToUse.getCharacterEncoding());
				try {

					if (response.getContentType() != null && !response.getContentType().matches("(.*)json(.*)")) {
						log.info("[{}][{}][{}] [RES] type :{}", request.getRequestURI(), request.getRemoteAddr(),
								request.getMethod(), response.getContentType());
					} else {
						String body = maskFilter.resMaskFilter(request.getRequestURI(), request.getRemoteAddr(),
								request.getMethod(), res);

						JSONObject msg = parseLogMsg(res, request.getRequestURI());

						// URL로 판단해야함
						String code = msg == null ? null : msg.get("code").toString();
						log.info("[{}][{}][{}] [RES] {}", request.getRequestURI(), request.getRemoteAddr(),
								request.getMethod(),
								code == null ? "" : code);
						if (prop.getIsLogResPLPrint() == 1) {
							log.debug("[{}][{}][{}] [RES_PAYLOAD] {}", request.getRequestURI(), request.getRemoteAddr(),
									request.getMethod(),
									body);
						} else {
							log.info("[{}][{}][{}] [RES_PAYLOAD] {}", request.getRequestURI(), request.getRemoteAddr(),
									request.getMethod(),
									body);
						}
					}

				} catch (Exception e) {
					if (prop.getIsLogResPLPrint() == 1) {
						log.debug("[{}][{}][{}] [RES] {}", request.getRequestURI(), request.getRemoteAddr(),
								request.getMethod(),
								res);
					} else {
						log.info("[{}][{}][{}] [RES] {}", request.getRequestURI(), request.getRemoteAddr(),
								request.getMethod(),
								res);
					}
				}
			}
		} finally {
			if (shouldLog && !isAsyncStarted(requestToUse)) {
				afterRequest(requestToUse, createMessage(requestToUse, "", ""));
			}
			MDC.remove("LOG_KEY");
		}
	}

	private JSONObject parseLogMsg(String string, String url) {
		try {
			if (string == null || string.trim().equals("")) {
				return null;
			}

			JSONObject json = new JSONObject(string);
			return json;
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	@SneakyThrows
	protected String createMessage(HttpServletRequest request, String prefix, String suffix) {

		final RequestInfo requestInfo = new RequestInfo();
		final String method = request.getMethod();
		requestInfo.setMethod(method);
		requestInfo.setContentType(request.getContentType());
		requestInfo.setAccept(request.getHeader("Accept"));
		requestInfo.setRemoteAddr(request.getRemoteAddr());

		String url = request.getRequestURI();

		if (isIncludeQueryString()) {

			String queryString = request.getQueryString();

			if (StringUtils.isNoneBlank(queryString) && RequestTypeCode.GET.value().equals(method)) {
				requestInfo.setGetParam(queryString);
			}

			if (RequestTypeCode.POST.value().equals(method)) {
				Map<String, String[]> paramMap = request.getParameterMap();
				if (paramMap != null && !paramMap.isEmpty()) {
					requestInfo.setPostParam(paramMap);
				}
			}
		}
		requestInfo.setUrl(url);
		if (isIncludeClientInfo()) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				requestInfo.setSession(session.getId());
			}
			String user = request.getRemoteUser();
			if (user != null) {
				requestInfo.setUser(user);
			}
		}

		if (isIncludePayload() && request instanceof ContentCachingRequestWrapper) {
			ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
			byte[] buf = wrapper.getContentAsByteArray();
			if (buf.length > 0) {
				int length = Math.min(buf.length, getMaxPayloadLength());
				String payload;
				try {
					payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					payload = "[unknown]";
					e.printStackTrace();
				}

				LinkedTreeMap<String, Object> payloadMap = new LinkedTreeMap<>();
				try {
					if (!payload.equals("[unknown]") && !payload.equals("")) {
						// 2MB를 초과하여 데이터가 잘렸을경우 에러 대비
						try {
							TypeToken<LinkedTreeMap<String, Object>> typeToken = new TypeToken<LinkedTreeMap<String, Object>>() {
							};
							payloadMap = gson.fromJson(payload, typeToken.getType());
						} catch (JsonSyntaxException e) {
							payloadMap.put("cut_data", payload);
						}

					}
					requestInfo.setPayload(payloadMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		StringBuilder msg = new StringBuilder();
		msg.append(gson.toJson(requestInfo));
		return msg.toString();
	}

	public static class ResponseWrapper extends HttpServletResponseWrapper {

		private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

		private final PrintWriter writer = new PrintWriter(bos);

		public ResponseWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public ServletResponse getResponse() {
			return this;
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new ServletOutputStream() {
				private TeeOutputStream tee = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), bos);

				@Override
				public void write(int b) throws IOException {
					tee.write(b);
				}

				@Override
				public void setWriteListener(WriteListener listener) {
				}

				@Override
				public boolean isReady() {
					return false;
				}
			};
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return new TeePrintWriter(super.getWriter(), writer);
		}

		public byte[] toByteArray() {
			return bos.toByteArray();
		}
	}

	public static class TeePrintWriter extends PrintWriter {

		PrintWriter branch;

		public TeePrintWriter(PrintWriter main, PrintWriter branch) {
			super(main, true);
			this.branch = branch;
		}

		@Override
		public void write(char buf[], int off, int len) {
			super.write(buf, off, len);
			super.flush();
			branch.write(buf, off, len);
			branch.flush();
		}

		@Override
		public void write(String s, int off, int len) {
			super.write(s, off, len);
			super.flush();
			branch.write(s, off, len);
			branch.flush();
		}

		@Override
		public void write(int c) {
			super.write(c);
			super.flush();
			branch.write(c);
			branch.flush();
		}

		@Override
		public void flush() {
			super.flush();
			branch.flush();
		}

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	public static class RequestInfo {

		@JsonProperty("Method")
		@JsonInclude(Include.NON_NULL)
		private String method;

		@JsonProperty("Content-Type")
		@JsonInclude(Include.NON_NULL)
		private String contentType;

		@JsonProperty("Accept")
		@JsonInclude(Include.NON_NULL)
		private String accept;

		@JsonProperty("RemoteAddr")
		@JsonInclude(Include.NON_NULL)
		private String remoteAddr;

		@JsonProperty("URL")
		@JsonInclude(Include.NON_NULL)
		private String url;

		@JsonProperty("session")
		@JsonInclude(Include.NON_NULL)
		private String session;

		@JsonProperty("User")
		@JsonInclude(Include.NON_NULL)
		private String user;

		@JsonProperty("GET-Parameter")
		@JsonInclude(Include.NON_NULL)
		private String getParam;

		@JsonProperty("POST-Parameter")
		@JsonInclude(Include.NON_NULL)
		private Map<String, String[]> postParam;

		@JsonProperty("Payload")
		@JsonInclude(Include.NON_NULL)
		private LinkedTreeMap<String, Object> payload;

	}

}
