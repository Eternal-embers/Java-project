package javaCode;

public class classwork1 {
		public static void Extremevalue(){
			System.out.println("1");
			int arr[] = new int[100];
			int i,min = 1000,max = 0;
			for(i = 0;i < 100;i++){
				arr[i] = (int)(Math.random()*1000+1);
				System.out.print(arr[i] + " ");
				if((i+1)%10==0) System.out.println("");
			}
			System.out.println("");
			for(i = 0;i<100;i++){
				if(min > arr[i]) min = arr[i];
				if(max < arr[i]) max = arr[i];
			}
			System.out.println("\nThe max num is " + max);
			System.out.println("The min num is " + min);
			System.out.println("\n- - - - -- - - - - - - - - - -");
		}
		
		public static void EvenOddnum(){
			System.out.println("2");
			int evenNum = 0,oddNum = 0;
			int arr[] = new int[100];
			int i,min = 1000,max = 0;
			for(i = 0;i < 100;i++){
				arr[i] = (int)(Math.random()*1000+1);
				System.out.print(arr[i] + " ");
				if((i+1)%10==0) System.out.println("");
			}
			System.out.println("");
			for(i = 0;i<100;i++){
				if(arr[i] % 2==0) evenNum++;
				else oddNum++;
			}
			System.out.println("\nThe even's num is " + evenNum);
			System.out.println("The odd's num is " + oddNum);
			System.out.println("\n- - - - -- - - - - - - - - - -");
			
		}
		public static void func3(){
			System.out.println("3");
			int sum = 0;
			int arr[] = new int[100];
			int i,min = 1000,max = 0;
			for(i = 0;i < 10;i++){
				arr[i] = (int)(Math.random()*1000+1);
				System.out.print(arr[i] + " ");
			}
			System.out.println("");
			for(i = 0;i<10;i++){
				sum+= arr[i];
				System.out.print(sum + " ");
			}
			System.out.println("\n- - - - -- - - - - - - - - - -");
		}
		
		public static void func4(){
			System.out.println("4");
			int arr[] = new int[100];
			int i,min = 1000,max = 0;
			for(i = 0;i < 100;i++){
				arr[i] = (int)(Math.random()*100+1);
				System.out.print(arr[i] + " ");
				if((i+1)%10==0) System.out.println("");
			}
			System.out.println("");
			for(i = 1;i < 100;i++){
				if(arr[i]==arr[i-1]) System.out.println(arr[i]);
			}
			System.out.println("\n- - - - -- - - - - - - - - - -");
		}
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			//Scanner input = new Scanner(System.in);
			Extremevalue();
			EvenOddnum();
			func3();
			func4();

		}
}
