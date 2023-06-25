package javaCode;

import java.io.File;

public class TraverseDisk {
	public static int space = 0;
	public static void open(File []list) {
		try {
			for(int i = 0;i < list.length;i++) {
				if(list[i].isFile()) {
					for(int j = 0;j < space;j++)
						System.out.print("    ");
					System.out.println(list[i].getName());
				}
				else {
					for(int j = 0;j < space;j++)
						System.out.print("    ");
					System.out.println(">>>目录<<< " + list[i].getName());
					File []dict = list[i].listFiles();
					int cur = space;
					space++;
					open(dict);
					space = cur;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File disk = new File("D:\\data");
		File[] dict = disk.listFiles();
		open(dict);
	}

}
