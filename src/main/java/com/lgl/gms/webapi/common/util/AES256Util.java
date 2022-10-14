package com.lgl.gms.webapi.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES256 암복호화 처리
 */
public class AES256Util {

	public static String ivStr = "0103090500700000";
	public static String key = null;

	public static String getKey() {
		return key;
	}

	public static void setKey(String encKey) {
		key = encKey;
	}

	public String encrypt(String str, String iv, String key)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// key = ByteUtil.byteArrayToHexString(key.getBytes());
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(encrypted));
		return enStr;
	}

	public void encryptFile(File f, String iv, String key, String target)
			throws IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");

		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

		InputStream input = new BufferedInputStream(new FileInputStream(f));
		OutputStream output = new BufferedOutputStream(new FileOutputStream(new File(target)));
		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = input.read(buffer)) != -1) {
			output.write(c.update(buffer, 0, read));
		}
		output.write(c.doFinal());
		input.close();
		output.close();
	}

	public void decryptFile(File f, String iv, String key, String target)
			throws IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

		InputStream input = new BufferedInputStream(new FileInputStream(f));
		OutputStream output = new BufferedOutputStream(new FileOutputStream(new File(target)));
		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = input.read(buffer)) != -1) {
			output.write(c.update(buffer, 0, read));
		}
		output.write(c.doFinal());
		input.close();
		output.close();
	}

	public String decrypt(String str, String iv, String key)
			throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
		byte[] byteStr = org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes());
		return new String(c.doFinal(byteStr), "UTF-8");
	}

	// 256 32바이트 키로 변환 테스트 - jdk8 u 161이후나 가능
	public static String ivBase64Str = "xShdOe0UYDn6KdgVThjXrg==";
	public static String keyBase64Str = "40wE/9NM2ifyBngc6qM2WP1McDAoBxfLpcLOryhAc4I=";

	public String encrypt(String str)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyByte = org.apache.commons.codec.binary.Base64.decodeBase64(keyBase64Str.getBytes());
		SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
		byte[] ivByte = org.apache.commons.codec.binary.Base64.decodeBase64(ivBase64Str.getBytes());
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivByte));

		byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
		String enStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(encrypted));
		return enStr;
	}

	public String decrypt(String str)
			throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyByte = org.apache.commons.codec.binary.Base64.decodeBase64(keyBase64Str.getBytes());
		SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
		byte[] ivByte = org.apache.commons.codec.binary.Base64.decodeBase64(ivBase64Str.getBytes());
		c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivByte));
		byte[] byteStr = org.apache.commons.codec.binary.Base64.decodeBase64(str.getBytes());
		return new String(c.doFinal(byteStr), "UTF-8");
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, GeneralSecurityException, IOException {
		AES256Util a = new AES256Util();
		String b = StringUtil.getUnicodeDecode("gRLCR%2BQeWE8PTYWVDhfs5A%3D%3D");
		System.out.println(b);
		System.out.println(a.decrypt(b));

		String nm = "01033921123";
		String enc = a.encrypt(nm);
		System.out.println(StringUtil.getUnicodeEncode(enc));

	}
}
