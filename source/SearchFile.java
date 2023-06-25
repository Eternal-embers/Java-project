package javaCode;

import java.io.File;
import java.util.Scanner;

public class SearchFile {
	static boolean flag;
	
	public static boolean checkKey(File file,String key) {
		String fileName = file.getName();
		if(fileName.contains(key)) 
			return true;
		return false;
	}
	
	public static boolean checkPrefix(File file,String prefix) {
		String fileName = file.getName();
		if(fileName.startsWith(prefix)) 
			return true;
		return false;
	}
	
	public static boolean checkSuffix(File file,String suffix) {
		String fileName = file.getName();
		if(fileName.endsWith(suffix)) 
			return true;
		return false;
	}
	
	public static void searchByKey(File dict,String key) {
		try {
			File files[] = dict.listFiles();//获取路径下的所有文件
			for(File f:files) {
				if(f.isDirectory())
					searchByKey(f,key);
				//验证是否符合查找情况
				else {
					if(checkKey(f,key)) {
						System.out.println(f);
						flag = true;
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void searchByPrefix(File dict,String prefix) {
		try {
			File files[] = dict.listFiles();//获取路径下的所有文件
			for(File f:files) {
				if(f.isDirectory())
					searchByPrefix(f,prefix);
				//验证是否符合查找情况
				else {
					if(checkPrefix(f,prefix)) {
						System.out.println(f);
						flag = true;
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void searchByExtendedName(File dict,String suffix) {
		try {
			File files[] = dict.listFiles();//获取路径下的所有文件
			for(File f:files) {
				if(f.isDirectory())
					searchByExtendedName(f,suffix);
				//验证是否符合查找情况
				else {
					if(checkSuffix(f,suffix)) {
						System.out.println(f);
						flag = true;
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String path;
		File dict;
		/* 用户输入文件路径 */
		do {
			System.out.println("请输入搜索目录的绝对地址：");
			System.out.print("path = ");
			path = input.nextLine();
		    dict = new File(path);
			if(!dict.exists()) 
				System.out.println("你所输入的文件路径不存在！");
		}while(!dict.exists());
		/* 选择搜索模式 */
		int option;
		System.out.println("按关键字搜索：1");
		System.out.println("按前缀搜索：2");
		System.out.println("按拓展名搜索：3");
		System.out.print("option = ");
		option = input.nextInt();
		input.nextLine();
		/* 按模式搜索 */
		switch(option) {
		/* 关键字 */
		case 1:{
			System.out.println("请输入关键字：");
			System.out.print("key = ");
			String key = input.nextLine();
			searchByKey(dict,key);
			break;
		}
		/* 前缀 */
		case 2:{
			System.out.println("请输入前缀：");
			System.out.print("prefix = ");
			String prefix = input.nextLine();
			searchByPrefix(dict,prefix);
			break;
		}
		/* 后缀 */
		case 3:{
			System.out.println("请输入后缀：");
			System.out.print("suffix = ");
			String suffix = input.nextLine();
			searchByExtendedName(dict,suffix);
			break;
		}
		}
		if(flag) System.out.println("搜索成功！");
		else System.out.println("无搜索结果！");
	}
}
