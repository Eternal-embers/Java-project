package javaCode;

public class ShowPrimeFactor {
	private int num;
	private int[] factor;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void getFactor() {
		int t = num;
		int count = 0;
		int i,j;
		for( i = 2;t>1;i++) {
			while(t%i==0){
				t/=i;
				count++;
			}
		}//算出因子个数
		System.out.println(count);
		factor = new int[count];
		t = num;
		for(i = 2,j = 0;t>1;i++) {
			while(t%i==0){
				factor[j] = i;//将因子记录到数组中
				j++;
				t/=i;
			}
		}
	}
	public void descendingOrder() {
		if(factor.length==0) {
			System.out.println("无因子可打印！");
			return;
		}
		for(int i = factor.length-1;i>=0;i--)
			System.out.print(factor[i] + " ");
	}
	public static void main(String[] args) {
		ShowPrimeFactor ob = new ShowPrimeFactor();
		ob.setNum(5400);
		System.out.println(ob.getNum());
		ob.getFactor();
		ob.descendingOrder();
	}
}
