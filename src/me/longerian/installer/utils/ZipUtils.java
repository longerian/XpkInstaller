package me.longerian.installer.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private static final String TAG = "ZipUtils";
	private static final int BUFFER_SIZE = 8192;

	public static void zip(String[] files, String zipFile) throws IOException {
		BufferedInputStream origin = null;
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(zipFile)));
		try {
			byte data[] = new byte[BUFFER_SIZE];

			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				try {
					ZipEntry entry = new ZipEntry(files[i].substring(files[i]
							.lastIndexOf("/") + 1));
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						out.write(data, 0, count);
					}
				} finally {
					origin.close();
				}
			}
		} finally {
			out.close();
		}
	}

	public static void unzip(String zipFile, String location)
			throws IOException {
		try {
			File f = new File(location);
			if (!f.isDirectory()) {
				f.mkdirs();
			}
			ZipInputStream zin = new ZipInputStream(
					new FileInputStream(zipFile));
			try {
				ZipEntry ze = null;
				while ((ze = zin.getNextEntry()) != null) {
					String path = location + ze.getName();

					if (ze.isDirectory()) {
						File unzipFile = new File(path);
						if (!unzipFile.isDirectory()) {
							unzipFile.mkdirs();
						}
					} else {
						FileOutputStream fout = new FileOutputStream(path,
								false);
						try {
							for (int c = zin.read(); c != -1; c = zin.read()) {
								fout.write(c);
							}
							zin.closeEntry();
						} finally {
							fout.close();
						}
					}
				}
			} finally {
				zin.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//TODO not ok
	public static void extractFile(String zipFilePath, String dstDirPath,
			String targetFileName) {
		ZipInputStream zin = null;
		try {
			zin = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.getName().endsWith(targetFileName)) {
//					String path = dstDirPath + File.separator + ze.getName();
//					File parent = new File(path).getParentFile();
//					if (!parent.exists()) {
//						parent.mkdirs();
//					}
					FileOutputStream fout = new FileOutputStream(dstDirPath
							+ File.separator + targetFileName, false);
					byte data[] = new byte[BUFFER_SIZE];
					BufferedInputStream bi = new BufferedInputStream(zin);
					BufferedOutputStream br = new BufferedOutputStream(fout);
					try {
						for (int c = bi.read(data); c != -1; c = bi.read(data)) {
							br.write(data, 0, c);
						}
						zin.closeEntry();
					} finally {
						br.close();
					}
					break;
				} else {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void unZip(String zipFilePath, String destDir)
			throws ZipException, IOException {
				destDir = destDir.endsWith("/") ? destDir : destDir + "/";
				BufferedInputStream bis=new BufferedInputStream(new FileInputStream(zipFilePath));
				BufferedOutputStream dest = null;
				ZipInputStream zis = new ZipInputStream(bis);
				ZipEntry entry;
				while((entry = zis.getNextEntry()) != null) {
					if (entry.isDirectory()) {
						File childDir=new File(destDir+entry.getName());
						childDir.mkdirs();
					}else{
						File destFile=new File(destDir+entry.getName());
						if (!destFile.getParentFile().exists()){
							destFile.getParentFile().mkdirs();
						}
						int count;
						byte data[] = new byte[BUFFER_SIZE];
						FileOutputStream fos = new FileOutputStream(destFile);
						dest = new BufferedOutputStream(fos, BUFFER_SIZE);
						while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
					}
				}
					zis.close();
					bis.close();
			}
}
