package javaCode;

public class VarArgsDemo {
	//可以将可变数量的相同类型的参数传递给方法，并将其视为数组
	//typename...parametername
	//java将可变长参数视为数组，当使用数量可变的参数调用方法时，java会创建一个数组并将参数传给它
	public static void main(String[] args){
		printMax(34,3,2,6,56,5);
		printMax(new double[]{1,2,3,4,8});
	}
	public static void printMax(double...numbers){
			if(numbers.length == 0){
				System.out.println("No arguments passed");
				return;
			}
			double result = numbers[0];
			for(int i = 0;i<numbers.length;i++){
				if(numbers[i] > result)
					result = numbers[i];
			}
			System.out.println("The max values is " + result);
	}
}
