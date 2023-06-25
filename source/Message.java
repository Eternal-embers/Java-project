package javaCode;
import java.util.Scanner;
public class Message {
	String recipient;
	String sender;
	String text = "";
	public Message(){
	}
	public void append(String s){
		text = text  + s;
	}
	public String toString(){
		return "From " + sender + " to " + recipient  + "\n\t" + text;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message ob = new Message();
		Scanner in = new Scanner(System.in);
		System.out.print("excipient:");
		ob.recipient = in.next();
		System.out.print("sender:");
		ob.sender = in.next();
		System.out.print("text:");
		ob.append(in.next());
		System.out.println(ob.toString());
		in.close();
	}
}
