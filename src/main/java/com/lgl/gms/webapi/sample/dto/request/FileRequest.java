package com.lgl.gms.webapi.sample.dto.request;

import lombok.Data;

@Data
public class FileRequest {

	private Integer id;
	private String ext;
	private String filename;
	private String filepath;
}
