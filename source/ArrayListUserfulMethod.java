package javaCode;

import java.util.Arrays;
import java.util.ArrayList;
public class ArrayListUserfulMethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] array1 = {"red","green","blue"};
		ArrayList<String> list1 = new ArrayList<>(Arrays.asList(array1));
		System.out.println(list1.toString());
		
		String[] array2 = new String[list1.size()];
		list1.toArray(array2);
		//调用Arraylist.toArray(Arrays)可以将ArrayList对象中的元素复制到Arrays中
		System.out.println(Arrays.toString(array1));
		
		Integer[] array3 = {3,5,95,4,15,34,3,6,5};
		ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(array3));
		java.util.Collections.sort(list2);
		System.out.println(list2);
		
		Integer[] array4 = {3,2,45,23,55,24,57,35};
		ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(array4));
		System.out.println(java.util.Collections.max(list3));
		System.out.println(java.util.Collections.min(list3));
		
		Integer[] array5 = {1,2,3,4,5,6,7,8,9,10};
		ArrayList<Integer> list4 = new ArrayList<>(Arrays.asList(array5));
		java.util.Collections.shuffle(list4);//将列表打乱，shuffle是洗牌的意思
		System.out.println(list4);
	}
}
