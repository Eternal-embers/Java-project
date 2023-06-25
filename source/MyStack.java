package javaCode;

import java.util.ArrayList;
import java.util.Arrays;

public class MyStack {
	private ArrayList<Object> list = new ArrayList<>();
	public MyStack() {
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public int getSize() {
		return list.size();
	}
	public Object peek() {
		return list.get(getSize()-1);
	}
	public Object pop() {
		Object o = list.get(getSize()-1);
		list.remove(getSize()-1);
		return o;
	}
	public  void push(Object o) {
		list.add(o);
	}
	@Override
	public String toString() {
		return "stack: " + list.toString();
	}
	//API,application programming interface,应用程序接口
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyStack stack = new MyStack();
		stack.push(new Object());
		stack.push(new Object());
		stack.push(new Object());
		System.out.println(stack.getSize());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.isEmpty());
	}

}
