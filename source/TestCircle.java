package javaCode;

public abstract class TestCircle {
	public static void main(String[] args) {
		//创建一个圆其半径为1
		Circle circle1 = new Circle();
		System.out.println("The area of the circle"
		+ circle1.radius + " is " + circle1.getArea());
		//创建一个圆半径为25
		Circle circle2 = new Circle(25);
		System.out.println("The area of the circle radius "
		+ circle2.radius + " is" + circle2.getArea());
		//创建一个圆半径为125
		Circle circle3 = new Circle(125);
		System.out.println("The area of circle of radius "
		+ circle3.radius + " is " + circle2.getArea());
		//修改圆的半径
		circle2.radius = 100;
		System.out.println(circle2.radius);
		circle2.setRadius(120);
		System.out.println("The area of circle radius"
		+ circle2.radius + " is " + circle2.getArea());
	}
	//内部类是动态的，也就是开头以public class开头。而主程序是public static class main。
	//在Java中，类中的静态方法不能直接调用动态方法。
	//只有将某个内部类修饰为静态类，然后才能够在静态类中调用该类的成员变量与成员方法。只需将内部类变成静态的。
	public static class Circle{
		double radius;
		//构造一个圆其半径为1
		Circle(){
			radius = 1;
		}
		//初始化
		Circle(double newRadius){
			radius = newRadius;
		}
		//获取面积
		double getArea() {
			return radius*radius*Math.PI;
		}
		//获取周长
		double getPerimeter() {
			return 2*radius*Math.PI;
		}
		//设置新的半径
		void setRadius(double newRadius) {
			radius = newRadius;
		}
	}
	
}
