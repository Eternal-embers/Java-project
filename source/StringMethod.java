package javaCode;

public class StringMethod {
	public static void main(String[] args) {
		System.out.println("unicode:0~9 \u0030~\u0039");
		System.out.println("unicode:A~Z \u0041~\u005A");
		System.out.println("unicode:a~z \u0061~\u007A");  
		char c1 = '1';
		char c2 = 'a';
		char c3 = '*';
		char c4 = 'A';
		String str = "string方法为实例方法";
		String str1 = ",concat()可以连接字符串";
		System.out.println(c1);
		//比较和测试字符,在java.lang的character类中提供函数支持
		System.out.println("isDight('1') is " + Character.isDigit(c1));
		System.out.println("isLetter('a') is "+ Character.isLetter(c2));
		System.out.println("isLetterOrDigit('*') is " + Character.isLetterOrDigit(c3));
		System.out.println("isLowerCase('a') is " + Character.isLowerCase(c2));
		System.out.println("isUppreCase('A') is " + Character.isUpperCase(c4));
		System.out.println("toLowercase('A') is " + Character.toLowerCase(c4));
		System.out.println("toUpperCase('a') is " + Character.toUpperCase(c2));
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
		//String的长度，连接字符串
		System.out.println(str.length());
		System.out.println("s.charAt(index)获取字符串中的某个字符，其中index取值范围是0~index-1");
		System.out.println("str.charAt(3) = " + str.charAt(3));
		System.out.println(str.length());//长度不包括\0
		String str2 = str1.concat(str1);//将str1连接在str的后面
		System.out.println("连接后的字符串为" + str2);
		String str3 = "+为字符串连接符"+"可以连接多个字符串"+"也可以连接字符"+'A'+'B'+'C';
		System.out.println(str3);
		//转换字符串
		System.out.println("WELcome!".toLowerCase());//将字符串中的大写字母全部转换为小写
		System.out.println("welcome!".toUpperCase());//将字符串中的小写字母全部转换为大写
		System.out.println("\tWelcome\n  ".trim());//string.trim()将string中的空格删除得到一个新的串
		//从控制台读取字符串
		/*
		System.out.println("nextInt(),nextLong()...这些为基于标记的输入");
		System.out.println("nextLine()为基于列的输入，不要在基于标记后的输入之后使用基于行的输入");
		Scanner input = new Scanner(System.in);//首先创建一个Scanner对象，其方法为system.in;
		String s = input.nextLine();//基于行的输入
		char c = s.charAt(0);//从控制台读取一个字符
		byte b = input.nextByte();
		short sht = input.nextShort();
		int i = input.nextInt();
		long l = input.nextLong();
		float f = input.nextFloat();
		double d = input.nextDouble();
		System.out.println(c);
		System.out.println(b);
		System.out.println(sht);
		System.out.println(i);
		System.out.println(l);
		System.out.println(f);
		System.out.println(d);
		*/
		//字符串比较
		String s1 = "extend";
		String s2 = "exTend";
		String s3 = "extend";
		System.out.println(s2.equals(s1));//如果该字符串等于s1，返回true
		System.out.println(s2.equalsIgnoreCase(s1));//如果该字符串等于s1，返回true，不区分大小写
		System.out.println(s1.compareTo(s2));//相当于C语言的strcmp(),s1大于s2，返回正数，小于返回负数，等于返回0
		System.out.println(s1.compareToIgnoreCase(s2));//和compareTo一样，除了比较不区分大小写
		System.out.println(s1.startsWith("ext"));//s.startWith(String s1)，如果s的前缀为s1，返回true
		System.out.println(s1.endsWith("end"));//s.endsWith(string s1),如果s的后缀为s1，返回true
		System.out.println(s1.contains("te"));//如果s.contains(string s1),如果s包含字符串s1，返回true
		//input.close();
		if(s1==s3) System.out.println(s1 + "is equal to "+ s3);//不能用==判断字符串是否相等，string1==string2只能检测是否指向同一个对象
		else System.out.println(s1 + " isn't equal to " + s3);
		//获得子字符串
		char ch  = s1.charAt(5);//获取s1串中下标为5的字符，实际为s1中第六个字符
		System.out.println(ch);
		String s4 = "PHP is the best language in the world";
		String sChild1 = s4.substring(7);//s.substring(begin)，返回s中下标为begin到结尾的子串
		String sChild2 = s4.substring(7,15);//s.substring(begin,end)，返回s中下标为begin到end-1的子串
		System.out.println(sChild1);
		System.out.println(sChild2);
		//查找字符串
		int index1 = s4.indexOf('t');//s.indexOf(ch),返回s中第一个出现ch的下标，如果没有匹配返回-1
		int index2 = s4.indexOf('t',index1+1);//s.indexOf(ch,index)，返回s中包括index之后出现ch的下标，如果没有匹配返回-1
		int index3 = s4.indexOf("the");//s.indexOf(String str),返回s中出现的第一个str的下标，如果没有匹配返回-1
		int index4 = s4.indexOf("the",index3+1);//s.indexOf(String str,index)，返回s中下标包括index之后第一个出现str的下标，如果没有匹配返回-1
		int index5 = s4.lastIndexOf('e');//s.lastIndexOf(ch)，返回s中最后一个ch的下标，如果没有匹配返回-1
		int index6 = s4.lastIndexOf('e',index5-1);//s.lastIndexOf(ch,index),返回s中包括index之前的最后一个ch的下标，如果没有匹配返回-1
		int index7 = s4.lastIndexOf("the");//s.lastIndexOf(str),返回s中最后一个str的下标，如果没有匹配返回-1
		int index8 = s4.lastIndexOf("the",index6-1);//s.lastIndexOf(str,index)，返回str中包括index之前的最后一个出现str的下标，未匹配返回-1
		String s5 = s4.substring(index1);
		System.out.println(s5);
		s5 = s4.substring(index2);
		System.out.println(s5);
		s5 = s4.substring(index3);
		System.out.println(s5);
		s5 = s4.substring(index4);
		System.out.println(s5);
		s5 = s4.substring(index5);
		System.out.println(s5);
		s5 = s4.substring(index6);
		System.out.println(s5);
		s5 = s4.substring(index7);
		System.out.println(s5);
		s5 = s4.substring(index8);
		System.out.println(s5);
		//字符串和数值间的转换
		String intString = "123456";
		int intvalue = Integer.parseInt(intString);
		String doubleString = "3.14159";
		double doublevalue = Double.parseDouble(doubleString);
		System.out.println(intvalue);
		System.out.println(doublevalue);
	}
}
