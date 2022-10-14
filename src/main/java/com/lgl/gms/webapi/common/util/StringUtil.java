
package com.lgl.gms.webapi.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String getUUID() throws java.security.SignatureException {
		String result;
		try {
			UUID one = UUID.randomUUID();
			result = one.toString().replace("-", "");

		} catch (Exception e) {
			throw new SignatureException("Failed to generate UUID : " + e.getMessage());
		}
		return result;
	}

	public static String getUnicodeDecode(String key) throws UnsupportedEncodingException {
		return URLDecoder.decode(key, "UTF-8");
	}

	public static String getUnicodeEncode(String key) throws UnsupportedEncodingException {
		return URLEncoder.encode(key, "UTF-8");
	}

	private static final String IPV4_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

	private static final Pattern pattern = Pattern.compile(IPV4_PATTERN);

	public static boolean isValidIPAddress(String ip) {
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	// public static void main(String[] a) {
	// try {
	// StringUtil.getUUID();
	// } catch (SignatureException e1) {
	// e1.printStackTrace();
	// }
	// }
}