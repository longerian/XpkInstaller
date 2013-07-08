package me.longerian.installer;

import me.longerian.installer.utils.AdbHelper;

public class ApkInstaller implements Installer {

	@Override
	public boolean installPackage(String path) {
		boolean result = false;
		do {
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
			echo = AdbHelper.getInstance().executeCommand("tools/adb.exe", "install", "-r", path);
			System.out.println(echo);
			if(echo.toLowerCase().contains("success")) {
				result = true;
			}
			AdbHelper.getInstance().stop();
		} while(false);
		return result;
	}

}
