package me.longerian.installer.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AdbShellHelper {

	private static final byte[] LS = "\n".getBytes();
	private static final byte[] EXIT = "exit".getBytes();
	private static final String ADB_PATH = "tools/adb.exe";
	
	private Process process;
	private InputStream is;
	private OutputStream os;

	private static AdbShellHelper adbShellHelper;
	
	private AdbShellHelper() {
	}
	
	public static AdbShellHelper getInstance() {
		if(adbShellHelper == null) {
			adbShellHelper = new AdbShellHelper();
		}
		return adbShellHelper;
	}
	
	private void start() {
		try {
			process = new ProcessBuilder().command(ADB_PATH, "shell")
					.redirectErrorStream(true).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(process != null) {
			is = process.getInputStream();
			os = process.getOutputStream();
		}
	}
	
	private void stop() {
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
	
	public String execute(String... command) {
		start();
		StringBuilder sb = new StringBuilder();
		if(os != null && command != null && is != null) {
			try {
				for(String s : command) {
					os.write(s.getBytes());
					os.write(LS);
				}
				os.write(EXIT);
				os.write(LS);
				os.flush();
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
		stop();
		return sb.toString();
	}
	
}
