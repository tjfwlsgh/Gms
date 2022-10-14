package com.lgl.gms.webapi.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * AES128 암복호화처리
 */
public class AES128Util {

	/** 암호화 키 16자리 */
	public static String ivStr = "0103090500700000";
	public static String key = null;

	public static String getKey() {
		return key;
	}

	public static void setKey(String encKey) {
		key = encKey;
	}

	/**
	 * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
	 *
	 * @param hex hex string
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {

		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];

		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}

		return ba;
	}

	/**
	 * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 *
	 * @param ba byte[]
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {

		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;

		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}

		return sb.toString();
	}

	/**
	 * AES 방식의 암호화
	 *
	 * @param message 암호화 대상 문자열
	 * @return String 암호화 된 문자열
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 * @throws Exception
	 */
	public static String encrypt(String message) throws NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		// use key coss2
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

		// Instantiate the cipher
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(message.getBytes());
		return byteArrayToHex(encrypted);
	}

	public static String encrypt(String message, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

		byte[] encrypted = cipher.doFinal(message.getBytes());

		return byteArrayToHex(encrypted);
	}

	public static String encryptWithBase64(String message, byte[] key, String ivStr)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

		byte[] encrypted = cipher.doFinal(message.getBytes());

		return Base64.encodeBase64String(encrypted).replace("+", "-").replace("/", "_");
	}

	public static String decryptWithBase64(String message, byte[] key, String ivStr)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		byte[] original = cipher.doFinal(Base64.decodeBase64(message.replace("-", "+").replace("_", "/")));
		String originalString = new String(original);
		return originalString;

	}

	/**
	 * AES 방식의 복호화
	 *
	 * @param message 복호화 대상 문자열
	 * @return String 복호화 된 문자열
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws Exception
	 */
	public static String decrypt(String encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(hexToByteArray(encrypted));
		String originalString = new String(original);
		return originalString;

	}

	public static boolean encrypt(String filePath, String targetPath, byte[] key) {
		// use key coss2
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

		// Instantiate the cipher

		InputStream input = null;
		OutputStream output = null;

		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

			input = new BufferedInputStream(new FileInputStream(new File(filePath)));
			output = new BufferedOutputStream(new FileOutputStream(new File(targetPath)));
			//
			byte[] buffer = new byte[1024];
			//
			int read = -1;
			//

			while ((read = input.read(buffer)) != -1) {

				output.write(cipher.update(buffer, 0, read));

			}
			//
			output.write(cipher.doFinal());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (output != null)
				try {
					output.close();
				} catch (IOException ie) {
					ie = null;
				}
			//
			if (input != null)
				try {
					input.close();
				} catch (IOException ie) {
					ie = null;
				}

		}
		File f = new File(targetPath);
		//
		if (f.isFile() && f.length() > 0L)
			return true;
		else
			return false;

	}

}