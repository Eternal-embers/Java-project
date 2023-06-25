package ShowCurrentTime;

public class ShowCurrentTime {
	public static void main(String[] args) {
				System.out.println("System.currentTimeMills(void) -long");
		        long millSeconds = System.currentTimeMillis();//获取毫秒数
		        System.out.println(millSeconds + "ms");
		        int realMillSeconds = (int)(millSeconds/1000%86400);//校正毫秒数
		        int hour = realMillSeconds / 3600 + 8;
		        int minute = realMillSeconds % 3600 / 60;
		        int second = realMillSeconds % 3600 % 60;
		        System.out.printf("%d:%02d:%02d", hour,minute,second);
	}
}
