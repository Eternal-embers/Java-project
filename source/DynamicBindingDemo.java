package javaCode;
import java.util.Objects;
public class DynamicBindingDemo {
	public static void m(Object x) {
		System.out.println(x.toString());
	}//方法
	public static void main(String[] args) {
		m(new GraduateStudent());
		m(new Student());//重写
		m(new Persons());//重写
		m(new Object());//调用java.util.Objects类的toString方法
	}
}
	
	class GraduateStudent extends Student{
	}//GraduateStudetn类继承自Student类
	
	class Student extends Persons {
		@Override
		public String toString() {
			return "Student";
		}
	}//Student类继承自Person类
	
	class Persons extends Object {
		@Override
		public String toString() {
			return "Persons";
		}
	}//Person继承自Object类
