package javaCode;

public class People extends CallCard{
	public People() {
	}
	public People(String name,String idCard,String callCard) {
		super(name,idCard,callCard);
	}
	public boolean equals(People ob) {
		if(ob instanceof People && super.equals(ob)) return true;
		return false;
	}
	public static void main(String[] args) {
		People ob1 = new People("eclipse","360731200401013313","19912345678");
		People ob2 = new People("eclipse","360731200401013313","19912345678");
		System.out.println(ob1.toString());
		System.out.println(ob1.equals(ob2));
	}
}
