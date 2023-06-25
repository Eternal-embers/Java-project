package javaCode;
import java.io.File;
import java.util.Date;
import java.util.Scanner;
public class Attrib {
	private File file = null;//文件对象
	private boolean initialization = false;//是否初始化文件属性
	private String filename;//文件名
	private String absolutePath;//绝对路径
	private String exist;//是否存在
	private long length;//文件大小
	private String canRead;//能否读取
	private String canWrite;//能否写入
	private Date lastModified;//上次修改时间
	String isDirectory;
	String isFile;
	String isAbsolute;
	String isHidden;
	String parent;
	public Attrib(){
	}
	public Attrib(String pathname){
		file = new File(checkPath(pathname));
	}//指定路径创建File对象
	public Attrib(String parent,String child) {
		file = new File(parent,child);
	}//在目录parent下创建一个子路径的File对象
	public Attrib(File parent,String child) {
		file = new File(parent,child);
	}//在已有的parent目录下创建一个子路径的File对象
	public String checkPath(String path) {
		char filePath[] = new char[100];
		char str[] = new char[path.length()];
		StringBuilder buffer = new StringBuilder();
		str = path.toCharArray();
		boolean absolute = false;
		int i,j;
		str = path.toCharArray();
		for(i = 0;i < str.length;i++)
			if(str[i]==':') {
				absolute = true;
				break;
			}
		if(absolute) {
			j = 0;
			if(str[0]!='"') filePath[j++] = str[0];
			for(i = 1;i < str.length-1;i++) {
				if(str[i]=='\\' && (str[i-1]!='\\' || str[i+1]!='\\')) {
					filePath[j++] = '\\';
					filePath[j++] = '\\';
				}
				else filePath[j++] = str[i];
			}
			if(str[i]!='"') filePath[j++] = str[i];
			filePath[j] = '\0';
			for(i = 0;i < j;i++)
				buffer.append(filePath[i]);
		}
		else return path;
		return buffer.toString();
		
	}//校正路径
	public void initializeProperty() {
		filename = file.getName();
		absolutePath = file.getAbsolutePath();
		exist = file.exists() ? "true" : "false";
		length = file.length();
		canRead = file.canRead() ? "true" : "false";
		canWrite = file.canWrite() ? "true" : "false";
		lastModified = new java.util.Date(file.lastModified());
		initialization = true;
	}
	public void showProperty() {
		System.out.println("\"" + file.toString() + "\"属性如下：");
		if(!initialization) this.initializeProperty();
		System.out.println("filename: " + filename);
		System.out.println("absolutePath: " + absolutePath);
		System.out.println("exist: " + exist);
		System.out.println("length: " + length + " byte");
		System.out.println("canRead: " + canRead);
		System.out.println("canWrite: " + canWrite);
		System.out.println("lastModified: " + lastModified);
	}
	public File getFile() {
		return file;
	}//获取文件对象
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("please input a path:");
		String path = in.next();
		Attrib ob = new Attrib(path);
		ob.showProperty();
	}
}
