package javaCode;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class OutputDateToFile {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.print("Pleast input a path:");
		Attrib ob = new Attrib(in.next());
		File file = ob.getFile();
		try (
			PrintWriter output = new PrintWriter(file);
		) {
			ob.showProperty();
			System.out.println("Please input three strnigs:");
			output.format("%s %s %s\n",in.next(),in.next(),in.next());
			in.nextLine();
			System.out.println("please input a sentense");
			output.println(in.nextLine());
		}
		Scanner readFile = new Scanner(file);
		while(readFile.hasNext()){
			System.out.println(readFile.nextLine());
		}
	}
}
