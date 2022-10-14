package com.lgl.gms.webapi.sample.service;

import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.sample.dto.request.UserSampleRequest;

public interface AdminService {

	BaseResponse allUsers(UserSampleRequest body, String header);
}
