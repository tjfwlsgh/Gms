package com.lgl.gms.config;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import com.google.gson.internal.LinkedTreeMap;
import com.lgl.gms.webapi.common.util.AES256Util;
import com.lgl.gms.webapi.common.util.LogParamUtil;
import com.lgl.gms.webapi.common.util.StringUtil;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 로그 정보 중, 개인정보 보호를 위한 마스킹처리
 */
@Slf4j
public class LogMaskFilter {

	/**
	 * 요청데이터로그의 마스킹처리
	 * 
	 * @param uri
	 * @param addr
	 * @param method
	 * @param payload
	 * @return
	 */
	public String reqMaskFilter(String uri, String addr, String method, LinkedTreeMap<String, Object> payload) {
		if (payload == null) {
			return LogParamUtil.getPrintParamMsgNotPretty(payload);
		}
		String methodType = method.toLowerCase();
		if (uri.matches(".*/v1/user/login") && methodType.equals("post")) {
			payload.put("pw", "****");
			customEncReq(payload, "username");
		}
		return LogParamUtil.getPrintParamMsgNotPretty(payload);
	}

	/**
	 * 응답데이터에 대한 마스킹처리
	 * 
	 * @param uri
	 * @param addr
	 * @param method
	 * @param payload
	 * @return
	 */
	public String resMaskFilter(String uri, String addr, String method, String payload) {
		try {

			if (payload == null || payload.trim().equals("")) {
				return null;
			}

			JSONObject json = new JSONObject(payload);
			if (json.isNull("code") || (int) json.get("code") != 200) {
				return json.toString();
			}
			String methodType = method.toLowerCase();
			if (uri.matches(".*/v1/users") && methodType.equals("get")) {
				JSONObject body = (JSONObject) json.get("result");
				if (body != null && !body.isNull("rows")) {
					JSONArray rows = (JSONArray) body.get("rows");
					for (int i = 0; i < rows.length(); i++) {
						JSONObject row = (JSONObject) rows.get(i);
						customEncRes(row, "email", "phone");
					}
				}
			} else if (uri.matches(".*/v1/users/.*") && methodType.equals("get")) {
				JSONObject body = (JSONObject) json.get("result");
				if (body != null) {
					customEncRes(body, "email", "phone");
				}
			}

			return json.toString();
		} catch (Exception e) {
			log.warn(e.toString(), e);
			return null;
		}

	}

	/**
	 * 응답데이터 마스킹처리 인코딩
	 * 
	 * @param payload
	 * @param keys
	 */
	private void customEncRes(JSONObject payload, String... keys) {
		AES256Util encUtil = new AES256Util();
		try {
			for (String string : keys) {
				if (payload.has(string)) {
					try {
						payload.put(string, StringUtil.getUnicodeEncode(encUtil.encrypt((String) payload.get(string))));
					} catch (UnsupportedEncodingException | GeneralSecurityException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (JSONException e) {
			log.warn(e.toString(), e);
		}

	}

	/**
	 * 요청데이터 마스킹처리 인코딩
	 * 
	 * @param payload
	 * @param keys
	 */
	private void customEncReq(LinkedTreeMap<String, Object> payload, String... keys) {
		AES256Util encUtil = new AES256Util();
		for (String string : keys) {
			if (payload.containsKey(string)) {
				try {
					payload.put(string, StringUtil.getUnicodeEncode(encUtil.encrypt((String) payload.get(string))));
				} catch (UnsupportedEncodingException | GeneralSecurityException e) {
					log.warn(e.toString(), e);
				}
			}
		}
	}

}
