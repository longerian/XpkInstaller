package me.longerian.installer;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import me.longerian.installer.utils.AdbHelper;
import me.longerian.installer.utils.FileUtils;
import me.longerian.installer.utils.ParseUtils;
import me.longerian.installer.utils.ZipUtils;
import me.longerian.installer.vo.XpkManifest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class XpkInstaller implements Installer {

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
			String xml = FileUtils.readFileAsString(tmpPath + File.separator + "manifest.xml");
			JSONObject json;
			try {
				json = XML.toJSONObject(xml);
			} catch (JSONException e) {
				e.printStackTrace();
				FileUtils.delFolder(tmpPath);
				break;
			}
            XpkManifest manifest = ParseUtils.json2XpkManifest(json);
			echo = AdbHelper.getInstance().executeCommand("tools/adb.exe", "install", "-r", tmpPath + File.separator + "application.apk");
			System.out.println(echo);
			if(!echo.toLowerCase().contains("success")) {
				FileUtils.delFolder(tmpPath);
				System.out.println("install apk failed");
				break;
			}
			String targetDataPackagePath = manifest.getDestination();
			echo = AdbHelper.getInstance().executeCommand("tools/adb.exe", "push", tmpPath + File.separator + manifest.getDataName(), targetDataPackagePath);
			System.out.println(echo);
			AdbHelper.getInstance().stop();
			FileUtils.delFolder(tmpPath);
			result = true;
		} while(false);
		return result;
	}

}
