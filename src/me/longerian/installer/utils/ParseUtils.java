package me.longerian.installer.utils;

import me.longerian.installer.vo.GpkManifest;
import me.longerian.installer.vo.XpkManifest;

import org.json.JSONException;
import org.json.JSONObject;


public class ParseUtils {

	public static GpkManifest json2GpkManifest(String json) {
		GpkManifest manifest = new GpkManifest();
	    JSONObject wholeObject;
		try {
			wholeObject = new JSONObject(json);
			JSONObject gpkBaseInfo = wholeObject.getJSONObject("gpkBaseInfo");
			manifest.setCpuType(gpkBaseInfo.getInt("cpuType"));
			manifest.setSdkVersion(gpkBaseInfo.getInt("sdkVersion"));
			manifest.setScreenDensity(gpkBaseInfo.getInt("screenDensity"));
			manifest.setApkCRC32(wholeObject.getJSONObject("dataValidation").getLong("apkCRC32"));
			manifest.setCopyPath(wholeObject.getJSONObject("dataBaseInfo").getString("copyPath").replace("\n", ""));
			JSONObject apkBaseInfo = wholeObject.getJSONObject("apkBaseInfo");
			manifest.setAppName(apkBaseInfo.getString("appName"));
			manifest.setAppSize(apkBaseInfo.getInt("appSize"));
			manifest.setPackageName(apkBaseInfo.getString("packageName"));
			manifest.setVersionName(apkBaseInfo.getString("versionName"));
			if (TextUtils.isEmpty(wholeObject.optString("gpkVersion"))) {
				manifest.setGpkVersion("old");
			} else {
				manifest.setGpkVersion("new");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return manifest;
	}
	
	public static XpkManifest json2XpkManifest(JSONObject json) {
		XpkManifest manifest = new XpkManifest();
		try {
			JSONObject xpk = json.getJSONObject("xpk");
			JSONObject apkInfo = xpk.getJSONObject("apkinfo");
			manifest.setMinSdkVersion(apkInfo.getString("minSdkVersion"));
			manifest.setPackageName(apkInfo.getString("package"));
			manifest.setVersionCode(apkInfo.getInt("versionCode"));
			manifest.setLabel(apkInfo.getString("label"));
			manifest.setAppSize(apkInfo.getLong("size"));
			JSONObject data = xpk.getJSONObject("data");
			manifest.setDataName(data.getString("name"));
			manifest.setDestination(data.getString("destination"));
			manifest.setDataSize(data.getLong("size"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return manifest;
		
	}
	
}
