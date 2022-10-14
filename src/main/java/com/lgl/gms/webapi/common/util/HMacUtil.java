
package com.lgl.gms.webapi.common.util;

import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.KeySpec;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * 암복호화 유틸리티
 * 사용자비밀번호 암복호화에 사용
 *
 */
public class HMacUtil {
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final String PBKDF_HMAC_SHA256_ALGORITHM = "PBKDF2WithHmacSHA1";
	// key = secret key
	// data = 변환 데이터

	private String normalKey = "637474766F72702D2D78696C6674656E2D746B";

	public String getEncHmacSha(String data)
			throws java.security.SignatureException {
		String result;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(normalKey.getBytes(), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			// 아래는 hexString
			result = Hex.encodeHexString(rawHmac);
			// 이는 base64 인코딩
			// result = Base64.encodeBase64String(rawHmac);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	public String getEncHmacSha(String data, String key)
			throws java.security.SignatureException {
		String result;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			// 아래는 hexString
			result = Hex.encodeHexString(rawHmac);
			// 이는 base64 인코딩
			// result = Base64.encodeBase64String(rawHmac);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	public String getEncHmacShaWithSalt(String data, String salt)
			throws java.security.SignatureException {
		String result;
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF_HMAC_SHA256_ALGORITHM);
			KeySpec keySpec = new PBEKeySpec(data.toCharArray(), Hex.decodeHex(salt), 1024, 256);
			SecretKey signingKey = secretKeyFactory.generateSecret(keySpec);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data.getBytes());
			// 아래는 hexString
			result = Hex.encodeHexString(rawHmac);
			// 이는 base64 인코딩
			// result = Base64.encodeBase64String(rawHmac);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	public byte[] getEncHmacSha(String data, byte[] key)
			throws java.security.SignatureException {
		byte[] rawHmac;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);
			rawHmac = mac.doFinal(data.getBytes());
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return rawHmac;
	}

	public byte[] getEncHmacSha(byte[] data, byte[] key)
			throws java.security.SignatureException {
		byte[] rawHmac;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA256_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);
			rawHmac = mac.doFinal(data);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return rawHmac;
	}

	public String getSalt() {
		SecureRandom srandom = new SecureRandom();
		byte[] salt = new byte[32];
		srandom.nextBytes(salt);
		return Hex.encodeHexString(salt);
	}

	public static void main(String[] args) throws SignatureException {
		HMacUtil a = new HMacUtil();
		//
		System.out.println(a.getEncHmacSha("Gms12!", "637474766F72702D2D78696C6674656E2D746B"));
		System.out.println(a.getEncHmacSha("Gms12!"));
	}
}