package javaCode;

import java.util.ArrayList;

public class Queue {
	private ArrayList<Integer> queue;
	public Queue() {
		queue = new ArrayList<Integer>();
	}
	public Queue(int arr[]) {
		queue = new ArrayList<Integer>();
		for(int i = 0;i<arr.length;i++) 
			queue.add(arr[i]);
	}
	public void enQueue(int n) {
		queue.add(n);
	}//入队
	public int deQueue() {
		int member = queue.get(0);
		queue.remove(0);
		return member;
	}//出队
	public boolean empty() {
		return queue.isEmpty();
	}//判断是否队列为空
	public int getSize() {
		return queue.size();
	}//返回队列大小
	public int getMember(int n) {
		return queue.get(n);
	}//获取指定队列元素
	public void clear() {
		queue.clear();
	}//清空队列
	public static void main(String[] args) {
		Queue q = new Queue();
		for(int i = 0;i<20;i++) {
			q.enQueue((int)(Math.random()*100+1));
		}
		System.out.println("队列先进先出，尾插头取");
		System.out.println(q.queue);
		System.out.println("入队:100");
		q.enQueue(100);
		System.out.println("出队：" + q.deQueue());
		System.out.println(q.queue);
		System.out.println("获取第5个元素：" + q.getMember(5));
		System.out.println("队列大小：" + q.getSize());
		System.out.println("清空队列");
		q.clear();
		System.out.println("队列是否清空？" + q.empty());
	}
}
