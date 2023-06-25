package javaCode;

import java.util.Scanner;

public class CaculatePractice extends BasicOperation{
	public CaculatePractice() {
	}
	public int getAns(int a,int b,int type) {
		if(type==1) return super.add(a, b);
		else if(type==2) return super.subtract(a, b);
		else if(type==3) return super.muliply(a, b);
		else if(type==4) return super.divide(a, b);
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CaculatePractice ob = new CaculatePractice();
		Scanner in = new Scanner(System.in);
		int model;//模式
		int type;//运算类型
		int a,b;
		int ans;//答案
		int option = 1;
		System.out.println("选择练习正数运算(输入正数或0),还是正负混合运算(输入负数)?");
		model = in.nextInt();
		if(model>=0)
			while(option==1) {
				type = (int)(Math.random()*4+1);//生成1~4的数字
				if(type==3) a = ob.smallPositiveNum();
				else a = ob.bigPositiveNum();
				b = ob.createPositiveNum();
				switch(type) {
				case 1: System.out.printf("%d + %d = ", a,b); break;
				case 2: System.out.printf("%d - %d = ", a,b); break;
				case 3: System.out.printf("%d * %d = ", a,b); break;
				case 4: System.out.printf("%d / %d = ", a,b); break;
				}
				ans = in.nextInt();
				if(ans==ob.getAns(a,b, type)) System.out.println("计算正确!");
				else {
					System.out.println("计算错误!");
					System.out.printf("正确答案为[%d]\n",ob.getAns(a,b,type));
				}
				System.out.println("是否选择继续练习?(Y[1]/N[0])");
				option = in.nextInt();
			}
		else {
			while(option==1) {
				type = (int)(Math.random()*4+1);//生成1~4的数字
				if(type==3) a = ob.smallNegativeNum();
				else a = ob.smallNegativeNum();
				b = ob.createNegativeNum();
				switch(type) {
				case 1: System.out.printf("(%d) + (%d) = ", a,b); break;
				case 2: System.out.printf("(%d) - (%d) = ", a,b); break;
				case 3: System.out.printf("(%d) * (%d) = ", a,b); break;
				case 4: System.out.printf("(%d) / (%d) = ", a,b); break;
				}
				ans = in.nextInt();
				if(ans==ob.getAns(a,b, type)) System.out.println("计算正确!");
				else {
					System.out.println("计算错误!");
					System.out.printf("正确答案为(%d)\n",ob.getAns(a,b,type));
				}
				System.out.println("是否选择继续练习?(Y[1]/N[0])");
				option = in.nextInt();
			}
		}
	}
}
