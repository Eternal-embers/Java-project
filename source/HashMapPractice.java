package javaCode;

import java.util.HashMap;
import java.util.Scanner;

public class HashMapPractice {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Scanner in = new Scanner(System.in);
		HashMap<String,String> map = new HashMap<String,String>();
		String[] s = {"Sue","Cathy","Bob"};
		map.put("Sue","Bob,Jose,Alex,Cathy");
		map.put("Cathy", "Bob,Alex");
		map.put("Bob","Alex,Jose,Jerry");
		String str = in.next();
		boolean flag = false;
		for(int i = 0;i < 3;i++)
			if(str.equals(s[i])) {
				System.out.println(map.get(str));
				flag = true;
				break;
			}
		if(flag == false) System.out.println(str + "不在哈希映射中");
		}
}
