package me.longerian.installer.app;

import java.io.File;

import me.longerian.installer.GpkInstaller;


public class MuzhiwanPackage extends AppPackage {

	public MuzhiwanPackage(String path) {
		this.packagePath = path;
		this.packageInstaller = new GpkInstaller();
	}
	
	@Override
	public boolean performInstall() {
		boolean result = false;
		if(packageInstaller != null && isValid()) {
			result = packageInstaller.installPackage(packagePath);
		}
		return result;
	}

	@Override
	protected boolean isValid() {
		boolean isValid = false;
		if(packagePath != null) {
			File apkFile = new File(packagePath);
			if(!apkFile.exists() || apkFile.isDirectory() || !apkFile.getName().endsWith(".gpk")) {
				System.out.println("invalid package file: " + packagePath);
				System.out.println("check these:");
				System.out.println("1.passing the full path of the target file");
				System.out.println("2.ensure that the file is not a directory");
				isValid = false;
			} else {
				isValid = true;
			}
		} else {
			System.out.println("package is null");
		}
		return isValid;
	}

}
