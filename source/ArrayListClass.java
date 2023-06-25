package javaCode;
import java.util.ArrayList;
public class ArrayListClass {

	public static void main(String[] args) {
		ArrayList arr = new ArrayList();
		arr.add("array");
		arr.add(12345);
		arr.add(3.14159);
		arr.add("deposit");
		arr.add("caculate");
		arr.add(Math.PI);
		arr.add(2,"insert");
		arr.remove(3);
		arr.remove(Math.PI);
		arr.removeAll(arr);
		for(int i = 0;i<arr.size();i++)
			System.out.println(arr.get(i));
		System.out.println(arr.contains(6));
		System.out.println(arr.contains(Math.PI));
		System.out.println("- - - - - - - - - - - - - - - - - - - - -");
		arr.clear();
		System.out.println(arr.isEmpty());
		arr.add("array");
		arr.add(12345);
		arr.add(3.14159);
		arr.add("deposit");
		arr.add("caculate");
		arr.add(Math.PI);
		arr.add(arr.size());
		arr.add(arr.size());
		arr.add(4,"insert");
		arr.add("array");
		System.out.println(arr.indexOf("array"));
		System.out.println(arr.lastIndexOf("array"));
		arr.set(arr.indexOf("array"), "hello,world");
		for(int i = 0;i<arr.size();i++)
			System.out.println(arr.get(i));
	}

}
