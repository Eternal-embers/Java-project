package javaCode;
import java.util.Date;
public class JavaAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date();
		String str = date.toString();
		long time = date.getTime();
		System.out.println(str);
		System.out.println(time);
		date.setTime(0);
	}
}
