package javaCode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Cloneable {

	public static void main(String[] args) {
		Calendar calendar = new GregorianCalendar(2013,2,1);
		Calendar calendar1 = calendar;//引用相同
		Calendar calendar2 = (Calendar)calendar.clone();//克隆为不同对象
		System.out.println("calendar == calendar1 is " + (calendar==calendar1));
		System.out.println("calendar == calendar2 is " + (calendar==calendar2));//克隆
		System.out.println("calendar.equals(calendar2) is " + 
		calendar.equals(calendar2));
		ArrayList<Double> list1 = new ArrayList<>();
		list1.add(1.5);
		list1.add(2.5);
		list1.add(3.5);
		ArrayList<Double> list2 = (ArrayList<Double>)list1.clone();
		ArrayList<Double> list3 = list1;
		list2.add(4.5);
		list2.add(1.5);
		System.out.println("list1 is " + list1);
		System.out.println("list2 is " + list2);
		System.out.println("list3 is " + list3);
		}
}
