package javaCode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date();
		System.out.println(date);
		SimpleDateFormat time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(time.format(System.currentTimeMillis()));
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;//月份0~11
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int weekday = calendar.get(Calendar.DAY_OF_WEEK)-1;
		int hour_12 = calendar.get(Calendar.HOUR);//12小时制
		int hour_24 = calendar.get(Calendar.HOUR_OF_DAY);//24小时制
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int mill = calendar.get(Calendar.MILLISECOND);
		System.out.printf("今天是%d年%d月%d日星期%d,%d:%02d:%02d:%d\n",
				year,month,day,weekday,hour_12,minute,second,mill);
		System.out.printf("今天是%d年%d月%d日星期%d,%d:%02d:%02d:%d\n",
				year,month,day,weekday,hour_24,minute,second,mill);
		System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
	}

}
