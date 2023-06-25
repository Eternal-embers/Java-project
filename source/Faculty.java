package javaCode;

public class Faculty extends Employee {
	public Faculty() {
		System.out.println("(4)Performs Faculty's takes");
	}//Faculty构造方法
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Faculty();
	}//创建一个匿名的Faculty对象
}

	class Employee extends Person{
		public Employee(){
			this("(2)Invoke Employee's overloaded constructor");
			System.out.println("(3)Performs Employee's tasks");
		}//Employee的构造方法
		public Employee(String s) {
			System.out.println(s);
		}//Employee的带参构造方法
	}//Employee类继承自Person类
	
	class Person {
		public Person() {
		System.out.println("(1)Performs Persons's tasks");
		}//Person类的构造方法
	}//Person类的定义
