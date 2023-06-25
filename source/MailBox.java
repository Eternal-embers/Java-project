package javaCode;

import java.util.Scanner;

public class MailBox {
	Scanner in = new Scanner(System.in);
	public void addMessage(Message ob){
		ob.append(in.next());
	}
	public String getMessage(Message ob){
		return ob.toString();
	}
	public void removeMessage(Message ob){
		ob.text = "From" + ob.recipient + " to " + ob.sender;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message ob = new Message();//Message对象
		Scanner in = new Scanner(System.in);
		System.out.print("recipient:");
		ob.recipient = in.next();
		System.out.print("sender:");
		ob.sender = in.next();
		System.out.print("text:");
		ob.append(in.next());
		MailBox mailbox = new MailBox();//MailBox对象
		System.out.print("添加信息：");
		mailbox.addMessage(ob);
		System.out.println(mailbox.getMessage(ob));
		mailbox.removeMessage(ob);
		System.out.println(ob.toString());
		in.close();
	}
}
