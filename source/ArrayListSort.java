package javaCode;
import java.util.ArrayList;
public class ArrayListSort {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();//构造对象
		//生成数据
		for(int i = 0;i<20;i++)
			list.add((int)(Math.random()*100+1));
		//排序前
		System.out.print("排序前：");
		for(int i = 0;i<list.size();i++)
			System.out.print(list.get(i) + " ");;
		System.out.println("");
		//排序后
		java.util.Collections.sort(list);
		System.out.print("排序后：");
		for(int i = 0;i<list.size();i++)
			System.out.print(list.get(i) + " ");
		System.out.println("");
	}

}
