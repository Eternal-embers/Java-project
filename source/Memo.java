package javaCode;

import java.io.Serializable;

public class Memo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String theme;//主题
	String date;//日期
	String message;//消息
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setmessage(String message) {
		this.message = message;
	}
}
