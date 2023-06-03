package AddressBook;

import java.util.Date;

public class Person {
	/* 数据域,私有封装，只能在Person类中访问 */
	private String name;//姓名
	private String address;//地址
	private String postalCode;//邮政编码
	private String phoneNumber;//电话号码
	private String creationDate;//创建日期
	private String comment;//备注
	
	/************* 构造方法 *************/
	/* 完整创建 */
	public Person(String name,String address,String postalCode,String phoneNumber,String comment) {
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.comment = comment;
		Date date = new Date();
		//SimpleDateFormat time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.creationDate = date.toString();//创建日期
	}
	/* 快捷创建 */
	public Person(String name,String phoneNumber,String comment) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.comment = comment;
		this.address = "null";
		this.postalCode = "null";
		Date date = new Date();
		//SimpleDateFormat time= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.creationDate = date.toString();//创建日期
	}
	/* 读取创建 */
	public Person(String name,String address,String postalCode,String phoneNumber,String creationDate,String comment) {
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.creationDate = creationDate;
		this.comment = comment;
	}
	/************* 构造方法 *************/

	
	/************* 访问器 *************/
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getPostalCode() {
		return this.postalCode;
	}
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public String getcreationDate() {
		return this.creationDate;
	}
	public String getComment() {
		return this.comment;
	}
	/************* 访问器 *************/
	
	
	/************* 修改器 *************/
	public void setName(String name) {
		if(name != null && !name.isEmpty()) {
			this.name = name;
		}
	}
	public void setAddress(String address) {
		if(name != null && !name.isEmpty()) {
			this.address = address;
		}
	}
	public void setPostalCode(String postalCode) {
		if(name != null && !name.isEmpty()) {
			this.postalCode = postalCode;
		}
	}
	public void setPhoneNumber(String phoneNumber) {
		if(name != null && !name.isEmpty()) {
			this.phoneNumber = phoneNumber;
		}
	}
	public void setCreationDate(String creationDate) {
		if(name != null && !name.isEmpty()) {
			this.creationDate = creationDate;
		}
	}
	public void setComment(String comment) {
		if(name != null && !name.isEmpty()) {
			this.comment = comment;
		}
	}
	/************* 修改器 *************/
	
	/************* 显示对象 *************/
	public void showPerson() {
		if(this != null) {
			System.out.printf("\t姓名：%s(%s)\n", name,comment);
			if(address.compareTo("null") != 0) System.out.println("\t地址：" + address);
			if(postalCode.compareTo("null") != 0)System.out.println("\t邮政编码：" + postalCode);
			System.out.println("\t电话号码：" + phoneNumber);
			System.out.println("\t创建日期：" + creationDate);
		}
	}
	/************* 显示对象 *************/
	
}

