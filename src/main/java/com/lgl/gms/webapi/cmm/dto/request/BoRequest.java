package com.lgl.gms.webapi.cmm.dto.request;


import com.lgl.gms.webapi.common.context.UserInfo;

import lombok.Data;

@Data
public class BoRequest {
	
	public Integer compId = UserInfo.getCompId();
	public Integer pboId;
	public String useYn;
	public String locale = UserInfo.getLocale();

}
