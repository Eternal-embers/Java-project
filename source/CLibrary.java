package javaCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CLibrary {
	public static void main(String[] args) throws FileNotFoundException{
			File file = new File("D:\\劣等生\\Documents\\C语言标准库.txt");
			System.out.println("文件大小：" + file.length() + "字节");
			Scanner input = new Scanner(file);
			while(input.hasNext()) {
				System.out.println(input.nextLine());
			}
			input.close();
	}
}
