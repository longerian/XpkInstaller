package me.longerian.installer;

import me.longerian.installer.app.AndroidOfficialPackage;
import me.longerian.installer.app.AppChinaPackage;
import me.longerian.installer.app.AppPackage;
import me.longerian.installer.app.MuzhiwanPackage;


public class MainPanel {

	/**
	 * @param args
	 * D:\\android_workspace\\SmsSync\\bin\\SmsSync.apk
	 * D:\\android_workspace\\ABCJava\\muzhiwan.com_Repulze.gpk
	 * D:\\TDDOWNLOAD\\Finger_Ninjas_v1.4.xpk
	 */
	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("please pass the path of the file that need to install");
			System.exit(0);
		}
		String path = args[0];
		AppPackage pack;
		if(path.endsWith(".apk")) {
			pack = new AndroidOfficialPackage(path);
			pack.performInstall();
		} else if(path.endsWith(".gpk")) {
			pack = new MuzhiwanPackage(path);
			pack.performInstall();
		} else if(path.endsWith(".xpk")) {
			pack = new AppChinaPackage(path);
			pack.performInstall();
		} else {
			System.out.println("only support apk, gpk, xpk files currently");
		}
		System.exit(0);
	}

}
