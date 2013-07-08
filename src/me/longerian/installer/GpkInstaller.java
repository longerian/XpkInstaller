package me.longerian.installer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipException;

import me.longerian.installer.utils.AdbHelper;
import me.longerian.installer.utils.Base64Coder;
import me.longerian.installer.utils.FileUtils;
import me.longerian.installer.utils.ParseUtils;
import me.longerian.installer.utils.SecurityUtils;
import me.longerian.installer.utils.ZipUtils;
import me.longerian.installer.vo.GpkManifest;


public class GpkInstaller implements Installer {

	private static final String TMPPRE = "tmp";
	
	@Override
	public boolean installPackage(String path) {
		boolean result = false;
		do {
			long now = System.currentTimeMillis();
			File tmp = new File(TMPPRE + now);
			if(!tmp.exists()) {
				tmp.mkdirs();
			}
			String tmpPath;
			try {
				tmpPath = tmp.getCanonicalPath();
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
			String echo = AdbHelper.getInstance().executeCommand("tools/adb.exe", "devices");
			System.out.println(echo);
			String[] devs = echo.split("\n");
			if(devs.length <= 2) {
				System.out.println("no devices found");
				break;
			}
			if(devs.length >= 4) {
				System.out.println("only support one device now");
				break;
			}
			try {
				ZipUtils.unZip(path, tmpPath);
			} catch (ZipException e1) {
				e1.printStackTrace();
				FileUtils.delFolder(tmpPath);
				break;
			} catch (IOException e1) {
				e1.printStackTrace();
				FileUtils.delFolder(tmpPath);
				break;
			}
			byte[] rawBytes = SecurityUtils.decrypt(Base64Coder.decode(FileUtils.readFileAsString(tmpPath + File.separator + "mainifest.dat")));
            String rawStr;
			try {
				rawStr = new String(rawBytes, "gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				FileUtils.delFolder(tmpPath);
				break;
			}
            GpkManifest manifest = ParseUtils.json2GpkManifest(rawStr);
			echo = AdbHelper.getInstance().executeCommand("tools/adb.exe", "install", "-r", tmpPath + File.separator + "application.apk");
			System.out.println(echo);
			if(!echo.toLowerCase().contains("success")) {
				FileUtils.delFolder(tmpPath);
				System.out.println("install apk failed");
				break;
			}
			String targetDataPackagePath = manifest.getCopyPath();
			echo = AdbHelper.getInstance().executeCommand("tools/adb.exe", "push", tmpPath + File.separator + manifest.getPackageName(), targetDataPackagePath);
			System.out.println(echo);
			AdbHelper.getInstance().stop();
			FileUtils.delFolder(tmpPath);
			result = true;
		} while(false);
		return result;
	}
	
	
	
}
