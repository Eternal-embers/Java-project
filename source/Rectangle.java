package javaCode;
import java.util.Scanner;
public class Rectangle {
	private double width;//对象的高度，类内私有
	private double height;//对象的高度，类内私有
	public Rectangle() {
	}//默认构造方法，完成对象初始化
	public double getwidth() {
		return width;
	}//获取宽度
	public double getheight() {
		return height;
	}//获取高度
	public double getArea() {
		return width*height;
	}//获取对象的面积
	public double perimeter() {
		return 2*width+2*height;
	}//获取对象的周长
	public void showproperty() {
		System.out.println("宽:" + width);
		System.out.println("高:" + height);
		System.out.println("周长:" + perimeter());
		System.out.println("面积：" + getArea());
	}//打印对象的属性
	Scanner in = new Scanner(System.in);
	public void setproperty() {
		System.out.print("width:");
		width = in.nextDouble();
		System.out.print("height:");
		height = in.nextDouble();
	}//修改器，修改对象的数据域
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("创建rectangle1对象:");
		Rectangle rectangle1 = new Rectangle();
		rectangle1.setproperty();
		System.out.println("创建rectangle2对象:");
		Rectangle rectangle2 = new Rectangle();
		rectangle2.setproperty();
		//打印对象属性
		System.out.println("rectangle1:");
		rectangle1.showproperty();
		System.out.println("rectangle2:");
		rectangle2.showproperty();
	}

}
