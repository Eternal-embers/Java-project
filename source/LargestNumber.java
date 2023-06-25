package javaCode;
import java.util.ArrayList;
import java.math.*;
public class LargestNumber {
	//Number是数值包装类以及BigInteget和BigDecimal类的抽象父类
	public static void main(String[] args) {
		ArrayList<Number> list = new ArrayList<>();
		list.add(45);//添加一个int值
		list.add(3345.53);//添加一个double值
		list.add(new BigInteger("48053840240389023801023"));//add a BigInteger
		list.add(new BigDecimal("2.343840380890380304004503"));//add a BigDecimal
		System.out.println("The largest number is " + getLargestNumber(list));
	}
	public static Number getLargestNumber(ArrayList<Number> list) {
		if(list == null || list.size() == 0) return null;
		Number number = list.get(0);
		for(int i = 0;i < list.size();i++)
			if(number.doubleValue() < list.get(i).doubleValue())
				number = list.get(i);
		return number;
	}

}
