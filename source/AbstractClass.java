package javaCode;

public class AbstractClass {
	public static void main(String[] args) {
		System.out.println("抽象类的构造方法定义为protected，因为它只被子类使用");
		System.out.println("创建一个具体的子类实例时，其父类的构造方法被调用以初始化父类中定义的数据域");
		System.out.println("抽象方法不能包含在非抽象类中，如果抽象父类的子类不能实现所有的抽象方法，那么子类也必须定义为抽象的。");
		System.out.println("在继承自非抽象类子类中，必须实现所有的抽象方法，抽象方法是非静态的");
		System.out.println("抽象类不能使用new来初始化，但仍可以定义它的抽象方法，这个抽象方法在子类中被调用");
		System.out.println("包含抽象方法的类必须是抽象的，然而可以定义不含抽象方法的抽象类作为定义新子类的基类");
		System.out.println("子类可以重写父类的方法并将它定义为抽象类");
		System.out.println("子类也可以是抽象的，即使它的父类是具体类");
		System.out.println("不能使用new操作符从一个抽象类创建一个实例，但抽象类可以用作一种数据类型");
		System.out.println("抽象类的实际意义不在于创建对象而在于被继承");
		System.out.println("当一个类继承抽象类后必须重写抽象方法，否则该类也变成抽象类，也就是抽象类对子类具有强制性和规范性");
		System.out.println("final和anstaract不能共同修饰一个方法,final修饰的方法不能被重写但是可以被继承");
	}
}
