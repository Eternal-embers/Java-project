package 计算二元线性方程组;
import java.util.Scanner;//导入Scanner类，Scanner类中有可以输入的方法，Scanner在java.util的包中
//注意java.util.Scanner必须这样书写，不可将写做Java.util.Scanner或者java.Util.Scanner
public class Caculate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ax + by = e");
		System.out.println("cx + dy = f");
		System.out.println("x = (ed - bf)/(ad - bc)");
		System.out.println("y = (af - ec)/(ad - bc)");
		Scanner input = new Scanner(System.in);//创建一个Scanner类的对象，它使用的方法为System.in;
		System.out.println("a = ");
		double a = input.nextDouble();
		System.out.print("b = ");
		double b = input.nextDouble();
		System.out.print("c = ");
		double c = input.nextDouble();
		System.out.print("d = ");
		double d = input.nextDouble();
		System.out.print("e = ");
		double e = input.nextDouble();
		System.out.print("f = ");
		double f = input.nextDouble();
		System.out.printf("x = %.3f,y = %.3f\n",(e*d-b*f)/(a*d-b*c),(a*f-e*c)/(a*d-b*c));
		System.out.print("- - - -运算结束- - - -");
		System.out.close();//关闭输出流，后续的所有输出都不会再输出到控制台
		input.close();//关闭Scanner类的input对象
		
	}
}
