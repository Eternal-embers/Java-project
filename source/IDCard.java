package javaCode;

public class IDCard extends Card{
	private String identity;
	public IDCard() {
	}
	public IDCard(String identity) {
		this.identity = identity;
	}
	public IDCard(String name,String identity) {
		super(name);
		this.identity = identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String toString() {
		return this.format() + "\nidentity：" + identity; 
	}
	public boolean equals(IDCard ob) {
		if(ob instanceof IDCard && this.identity.equals(ob.identity) && super.equals(ob)) 
			return true;
		return false;
	}
}
