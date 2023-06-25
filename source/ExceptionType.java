package javaCode;

public class ExceptionType {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("异常的根类是java.lang.Throeable");
		System.out.println("异常类可以分为三种主要类型：系统错误，异常和运行时异常");
		System.out.println("error是系统错误，由java虚拟机抛出,一般很少发生，用户无法处理");
		System.out.println("Error类的子类例子：");
		System.out.println("LinkageError:一个类依赖另一个类，但是编译前者后，后者进行了修改，出现不兼容");
		System.out.println("VirtualMachineError:java虚拟机崩溃，或者继续运行所必需的资源已耗尽");
		System.out.println("Exception类的例子：");
		System.out.println("ClassNotFoundExceptino:使用java命令来运行不存在的类");
		System.out.println("IOException:输入/输出异常，例如无效的输入，读文件超过文件尾，打开不存在的文件");
		System.out.println("运行时异常RuntimeException:");
		System.out.println("ArithemeticExceptino:整数除以0，浮点数的算术运算不抛出异常");
		System.out.println("NullPointerException:试图通过一个NULL引用变量来访问一个对象");
		System.out.println("IndexOutOfBoundException:数组的下标插过范围");
		System.out.println("IllegalArgumentException:传递给方法的参数非法或不合适");
		System.out.println("RuntimeException、Error以及它们的子类都称为免检异常");
		System.out.println("其他所有异常被称为必检异常，如Exception类的异常");
	}

}