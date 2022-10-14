package com.lgl.gms.webapi.sample.dto.response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class FileResponse {
	@JsonInclude(Include.NON_NULL)
	private Integer id;
	@JsonInclude(Include.NON_NULL)
	private String ext;
	@JsonInclude(Include.NON_NULL)
	private String filename;
	@JsonInclude(Include.NON_NULL)
	private String filepath;
	@JsonInclude(Include.NON_NULL)
	private String orgname;
	@JsonInclude(Include.NON_NULL)
	private String createdDt;
	@JsonInclude(Include.NON_NULL)
	private String updatedDt;
	@JsonInclude(Include.NON_NULL)
	private String url;

	public FileResponse() {

	}

	public FileResponse(int id) {
		this.id = id;
	}

	public void setData(HashMap<String, Object> data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		ArrayList<String> keys = new ArrayList<String>(data.keySet());
		for (String key : keys) {
			String lKey = key.toLowerCase();
			if (lKey.equals("id")) {
				id = ((Long) data.get(key)).intValue();
			} else if (lKey.equals("orgname")) {
				orgname = (String) data.get(key);
			} else if (lKey.equals("filename")) {
				filename = (String) data.get(key);
			} else if (lKey.equals("filepath")) {
				filepath = (String) data.get(key);
			} else if (lKey.equals("created_dt")) {
				createdDt = sdf.format((Date) data.get(key));
			} else if (lKey.equals("updated_dt")) {
				updatedDt = sdf.format((Date) data.get(key));
			} else if (lKey.equals("ext")) {
				ext = (String) data.get(key);
			}
		}

	}

}
