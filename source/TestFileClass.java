package javaCode;
public class TestFileClass{
    public static  void main(String[] args){
        java.io.File file = new java.io.File("D:\\data\\蓝桥杯.txt");
        System.out.println("Does it exist? " + file.exists());
        System.out.println("The file has " +  file.length() + " byte");
        System.out.println("Can it be read? " + file.canRead());
        System.out.println("Can it be written? " + file.canWrite());
        System.out.println("Is it a directory? " + file.isDirectory());
        System.out.println("Is it a file? " + file.isFile());
        System.out.println("Is it absolute? " + file.isAbsolute());
        System.out.println("Is is hidden? " + file.isHidden());
        System.out.println("File name is " + file.getName());
        System.out.println("Parent directory is " + file.getParent());
        System.out.println("Absolute path is " + file.getAbsolutePath());
        System.out.println("Last modified on " + new java.util.Date(file.lastModified()));
        System.out.println("Delete file: " + file.delete());
    } 
}