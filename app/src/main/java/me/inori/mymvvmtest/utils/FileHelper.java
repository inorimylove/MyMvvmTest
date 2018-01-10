package me.inori.mymvvmtest.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FileHelper {

	public static boolean isFileExists(String filePath, String fileName) {
		File destDir = new File(filePath);
		if (!destDir.exists()) {
			return false;
		}
		File file = new File(destDir, fileName);
		return file.exists();
	}

	public static boolean writeFile(byte[] file, String filePath, String fileName) {
			
		FileOutputStream fos = null;
	
		try {
			File destDir = new File(filePath);
			if (!destDir.exists()) {
				if (!destDir.mkdir()){
					throw new Exception("目录创建失败，请检查文件夹权限等问题");
				}
			}
			File destFile = new File(destDir, fileName);
			if (destFile.exists()){
				if(!destFile.delete()){
					throw new Exception("File delete fail");
				}
			}
			fos = new FileOutputStream(destFile);
			fos.write(file);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		Utils.mPrint("写入文件成功" + filePath + "/" + fileName);
		return true;
	}

	public static void writeFile(byte[] file, String filePath, String fileName, OnFileWroteListener listener) {
		Observable.just(writeFile(file, filePath, fileName))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(success -> {
					if (listener != null){
						listener.onFinish(success);
					}
				});
	}

	public interface OnFileWroteListener{
		void onFinish(boolean success);
	}


}
