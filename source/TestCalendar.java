package javaCode;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestCalendar {
	//Calendar是一个抽象的基类，可以提取出详细的日历信息
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
		System.out.println("java.util.Date的实例表示精度为毫秒的特定时刻");
		System.out.println("java.util.Calendar是一个抽象的基类,可以提取出详细的日历信息");
		System.out.println("Calendar类的子类可以实现特定的日历系统");
		System.out.println("java目前支持公历类java.util.GregorianCalendar");
		Calendar calendar = new GregorianCalendar();
		System.out.println("Current time is " + new Date());
		System.out.println("YEAR:" + calendar.get(Calendar.YEAR));
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
		System.out.println("DATE: " + calendar.get(Calendar.DATE));
		System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
		System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
		System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("AM_PM:" + calendar.get(Calendar.AM_PM));
		Calendar calendar1 = new GregorianCalendar(1997,11,25);
		String[] dayNameOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		System.out.println("December 25,1997 is a " + dayNameOfWeek[calendar1.get(Calendar.DAY_OF_WEEK)-1]);
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.toString());
		}
	}

}
