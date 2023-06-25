package javaCode;

public class TestTV {
	public static void main(String[] args) {
		//同一个包下的类可以相互调用，只要不受保护或限制
		//static修饰表示为静态
		//类的静态方法，静态变量是在类装载的时候装载的。
		//类的静态变量是该类的对象所共有的，即是所有对象共享变量。
		//静态属性的类可以直接调用，类名.方法名，也可以通过实例对象来访问
		//静态方法只能访问类内的静态成员
		//非静态类既可以访问类内的静态成员，也可以访问非静态成员
		TV tv1 = new TV();//非静态属性的类必须创建对象才能访问该类的方法
		tv1.turnOn();
		tv1.seaChannel(30);
		tv1.setVolume(3);
		
		TV tv2 = new TV();
		tv2.turnOn();
		tv2.channelUp();
		tv2.channelUp();
		tv2.volumeUp();
		
		System.out.println("tv1's channel is " + tv1.channel
		+ " and volume level is " + tv1.volumeLevel);
		System.out.println("tv2's channel is " + tv2.channel
		+ " and volume level is " + tv2.volumeLevel);
	}
}
