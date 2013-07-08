package me.longerian.installer.utils;

import java.io.ByteArrayOutputStream;

public class SecurityUtils {

	public static byte[] decrypt(byte[] encryptedBytes) {
		ByteArrayOutputStream container = new ByteArrayOutputStream();
		int length = encryptedBytes.length;
		for(int i = 0; ; i += 2) {
			if(i >= length) {
				break;
			}
			int j = encryptedBytes[i] - 120;
			int k = encryptedBytes[( i + 1)] - 122;
			container.write((j << 4) + (k & 0xF));
		}
		return container.toByteArray();
	}
	
	public static byte[] encrypt(byte[] rawBytes) {
		ByteArrayOutputStream container = new ByteArrayOutputStream();
		int length = rawBytes.length;
		for(int i = 0; ; i ++) {
			if(i >= length) {
				break;
			}
			int ch = rawBytes[i];
			int high = 0xF & ch >> 4;
			int low = ch & 0xF;
			container.write(high + 120);
			container.write(low + 122);
		}
		return container.toByteArray();
	}
	
}
