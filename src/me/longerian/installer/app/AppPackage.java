package me.longerian.installer.app;

import me.longerian.installer.Installer;

public abstract class AppPackage {

	protected Installer packageInstaller;
	protected String packagePath;
	
	public abstract boolean performInstall();
	protected abstract boolean isValid();
	
}
