package javaCode;

public class CallCard extends IDCard{
	private String callId;
	public CallCard() {
	}
	public CallCard(String callId) {
		this.callId = callId;
	}
	public CallCard(String name,String identity,String callId) {
		super(name,identity);
		this.callId = callId;
	}
	public String getCallId() {
		return callId;
	}
	public void setcallId(String callId) {
		this.callId = callId;
	}
	public String toString() {
		return super.toString() + "\ncallId：" + callId;
	}
	public boolean equals(CallCard ob) {
		if(ob instanceof CallCard && this.callId.equals(ob.callId) && super.equals(ob))
			return true;
		return false;
	}
}
