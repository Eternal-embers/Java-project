package Convert;

public class Convert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Java的类型转换：自动向高级别类型的转换，向下转换需要强制类型转换");
		System.out.println("Java默认整数为int类型，浮点数默认为double类型");
		System.out.println("Java中在数值尾部添加L/l表示long类型，F/f表示float类型，D/d表示double类型");
		System.out.println("Java中类型级别从低到高：byte<short<int<long<float<double");
		System.out.println("Java中的System.out.printf()中的格式转换为:单个字符%c,所有十进制整型%d,浮点型%f，八进制%o,十六进制%x，字符串%s;Java中无%ld，%lf");
		System.out.print("Java中如果要表示一个二进制整型字面量需要前置0b/0B，如果要表示一个八进制整型字面量需要前置0，如果要前置十六进制需要前置0X/0x\n%n");
		byte b = 127;
		int i = b;//b为byte类型可以自动转换为INT类型并赋值给i
		float f = (float) Math.E;//默认的浮点类型都是double类型，所以要赋值给float类型需要强制转换为float类型
		long L = (long) f;//float类型赋值给long类型为向更低类型的转换，所以需要强制转换
		double d = Math.PI;//默认的浮点数为double类型，所以可以直接赋值给double类型
		System.out.printf("%d %d %f %d %f",b,i,f,L,d);
	}

}
