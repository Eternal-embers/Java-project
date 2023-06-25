package javaCode;

import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListRunner {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		LinkedList<String> list =  new LinkedList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		list.add("fff");
		list.add("ggg");
		list.add("hhh");
		list.add("iii");
		ListIterator<String> iterator = list.listIterator();
		System.out.println("- - - - - - - - - - - - -");
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("- - - - - - - - - - - - -");
	}

}