package com.lgl.gms.webapi.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.util.MessageUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BaseResponse {

	@JsonProperty("code")
	@SerializedName("code")
	private int resCd;

	@JsonProperty("message")
	@SerializedName("message")
	private String resMsg;

	@JsonProperty("result")
	@SerializedName("result")
	@JsonInclude(Include.NON_NULL)
	protected Object data;

	public BaseResponse(ResponseCode code) {
		// setData(code.getCode(), code.getMsg());
		setData(code.getCode(), MessageUtil.getResponseMessage("" + code.getCode()));
	}

	public BaseResponse(ResponseCode code, Object data) {
		setData(code.getCode(), MessageUtil.getResponseMessage("" + code.getCode()));
		this.data = data;
	}

	public BaseResponse(int resCd, String resMsg, Object data) {
		setData(resCd, resMsg);
		this.data = data;
	}

	public BaseResponse(int resCd, String resMsg) {
		setData(resCd, resMsg);
	}

	private void setData(int resCd, String resMsg) {
		this.resCd = resCd;
		this.resMsg = resMsg;
	}
}
