package me.inori.mymvvmtest.mvvm.utils.helper;

import java.io.File;

public class FileHelper {

	public static File createFile(String path){
		File file = new File(path);
		if(!file.getParentFile().exists()) {
			System.out.println("目标文件所在目录不存在，准备创建它！");
			if(!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
			}
		}
		return file;
	}





}
