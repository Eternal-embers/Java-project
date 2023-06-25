package javaCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MemoManagement {
	public static void main(String[] args) throws FileNotFoundException {
		Memo memo[] = new Memo[10];//最多一次记录十组备忘录
		int size = 0;//记录当前备忘录数目
		Scanner in = new Scanner(System.in);
		String theme;
		String date;
		String message;
		String path;
		SimpleDateFormat time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("请输入一组备忘录:(输入#回车结束输入)");
		do {
			/* 输入主题 */
			System.out.print("theme:");
			theme = in.nextLine();
			/* 输入#结束输入备忘录 */
			if(theme.charAt(0) == '#') {
				System.out.println("- - - - - - - - - - - - - - - -");
				break;
			}
			/* 输入备忘信息 */
			System.out.print("message:");
			message = in.nextLine();
			/* 获取备忘时间 */
			date = time.format(System.currentTimeMillis());
			System.out.println("- - - - - - - - - - - - - - - -");
			/* 将记录一组备忘录 */
			memo[size] = new Memo();
			memo[size].setTheme(theme);
			memo[size].setDate(date);
			memo[size].setmessage(message);
			size++;
		} while (true);
		System.out.println("请输入保存备忘录路径:");
		path = in.nextLine();
		File file = new File(path);
		PrintWriter writer = new PrintWriter(file);
		for(int i = 0;i < size;i++) {
			writer.println("Theme:" + memo[i].theme);
			writer.println("Date:" + memo[i].date);
			writer.println("message:" + memo[i].message);
		}
		in.close();//关闭system.in输入
		writer.close();//关闭文件输入
		in = new Scanner(file);
		while(in.hasNextLine()) {
			for(int i = 0;i < 3;i++) {
				System.out.println(in.nextLine());
			}
			System.out.println("- - - - - - - - - - - - - - - -");
		}
	}
}
