package me.longerian.installer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AdbHelper {
	
	private static final byte[] LS = "\n".getBytes();
	
	private ProcessBuilder processBuilder;
	private Process process;
	private InputStream is;
	private OutputStream os;

	private static AdbHelper adbHelper;
	
	private AdbHelper() {
	}
	
	public static AdbHelper getInstance() {
		if(adbHelper == null) {
			adbHelper = new AdbHelper();
		}
		return adbHelper;
	}
	
	public String executeCommand(String... command) {
		internalStop();
		if(processBuilder == null) {
			processBuilder = new ProcessBuilder().command(command)
					.redirectErrorStream(true);
		} else {
			processBuilder.command(command);
		}
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(process != null) {
			is = process.getInputStream();
			os = process.getOutputStream();
		}
		StringBuilder sb = new StringBuilder();
		if(is != null) {
			is = process.getInputStream();
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				for (int c = is.read(); c != -1; c = is.read()) {
					baos.write(c);
				}
				sb.append(baos.toString());
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	private void internalStop() {
		if(is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(process != null) {
			process.destroy();
			process = null;
		}
	}
	
	public void stop() {
		internalStop();
		processBuilder = null;
		adbHelper = null;
	}
	
}
