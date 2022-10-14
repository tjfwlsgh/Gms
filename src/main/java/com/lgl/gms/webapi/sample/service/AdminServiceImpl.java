package com.lgl.gms.webapi.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgl.gms.webapi.common.dto.code.ResponseCode;
import com.lgl.gms.webapi.common.dto.response.BaseResponse;
import com.lgl.gms.webapi.common.dto.response.PagingListResponse;
import com.lgl.gms.webapi.sample.dto.request.UserSampleRequest;
import com.lgl.gms.webapi.sample.dto.response.UserSampleResponse;
import com.lgl.gms.webapi.sample.persistence.dao.UserSampleDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("AdminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	public UserSampleDao userDao;

	@Override
	public BaseResponse allUsers(UserSampleRequest body, String auth) {
		if (auth == null) {
			return new BaseResponse(ResponseCode.C402);
		}

		try {
			int page = Integer.parseInt(body.getPage());
			int pCnt = Integer.parseInt(body.getListSize());

			int stIdx = (page - 1) * pCnt;
			int edIdx = (page) * pCnt;
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("stIdx", stIdx);
			param.put("edIdx", edIdx);

			if (body.getSearchKey() != null && !body.getSearchKey().trim().equals("")) {
				param.put("searchKey", body.getSearchKey());
				param.put(body.getSearchKey(), body.getKeyword());
			}

			List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) userDao.selectUserListAll(param);

			int totalCount = userDao.selectUserListAllCount(param);
			int totalPage = totalCount == 0 ? 0 : (totalCount / pCnt + (totalCount % pCnt > 0 ? 1 : 0));

			PagingListResponse resData = new PagingListResponse(totalCount, totalPage);

			List<UserSampleResponse> rows = new ArrayList<UserSampleResponse>();
			for (HashMap<String, Object> hashMap : list) {
				UserSampleResponse data = new UserSampleResponse();
				data.setData(hashMap);
				rows.add(data);
			}
			resData.setRows(rows);

			BaseResponse res = new BaseResponse(ResponseCode.C200);
			res.setData(resData);
			return res;

		} catch (Exception e) {
			log.warn(e.toString(), e);
			return new BaseResponse(ResponseCode.C589);
		}
	}
}
