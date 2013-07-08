package me.longerian.installer.vo;


public class GpkManifest {
	private long apkCRC32;
	private String appName;
	private long appSize;
	private String copyPath;
	private int cpuType;
	private String gpkVersion;
	private String packageName;
	private int screenDensity;
	private int sdkVersion;
	private String versionName;

	public long getApkCRC32() {
		return this.apkCRC32;
	}

	public String getAppName() {
		return this.appName;
	}

	public long getAppSize() {
		return this.appSize;
	}

	public String getCopyPath() {
		return this.copyPath;
	}

	public int getCpuType() {
		return this.cpuType;
	}

	public String getGpkVersion() {
		return this.gpkVersion;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public int getScreenDensity() {
		return this.screenDensity;
	}

	public int getSdkVersion() {
		return this.sdkVersion;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setApkCRC32(long paramLong) {
		this.apkCRC32 = paramLong;
	}

	public void setAppName(String paramString) {
		this.appName = paramString;
	}

	public void setAppSize(long paramLong) {
		this.appSize = paramLong;
	}

	public void setCopyPath(String paramString) {
		this.copyPath = paramString;
	}

	public void setCpuType(int paramString) {
		this.cpuType = paramString;
	}

	public void setGpkVersion(String paramString) {
		this.gpkVersion = paramString;
	}

	public void setPackageName(String paramString) {
		this.packageName = paramString;
	}

	public void setScreenDensity(int paramString) {
		this.screenDensity = paramString;
	}

	public void setSdkVersion(int paramInt) {
		this.sdkVersion = paramInt;
	}

	public void setVersionName(String paramString) {
		this.versionName = paramString;
	}
}
