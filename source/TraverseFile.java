package javaCode;

import java.io.File;

public class TraverseFile {
	
	public static void main(String[] args) {
		String path = "D:\\photo\\渐变";//遍历的目录
		File file = new File(path);
		File[] fs = file.listFiles();//遍历path下的文件和目录，放在file数组中
		for(File f:fs) {
			if(!f.isDirectory()) {
				System.out.println(f.getName());
			}
		}
	}

}
