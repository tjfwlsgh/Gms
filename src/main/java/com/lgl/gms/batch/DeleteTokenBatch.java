
package com.lgl.gms.batch;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lgl.gms.config.PropConfig;
import com.lgl.gms.webapi.common.util.LogKeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeleteTokenBatch {

	@Autowired
	public PropConfig prop;

	public final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteTokenCurrent() throws java.lang.Exception {
	}

	private String setLogkey() {
		String logKey = LogKeyUtil.getRandomKey();
		MDC.put("LOG_KEY", logKey);
		return logKey;
	}
}
