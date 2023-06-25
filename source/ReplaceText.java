package javaCode;
import java.io.*;
import java.util.*;
public class ReplaceText{
    //java ReplaceText sourceFile targetFile oldString newString
    //java ReplaceText FormatString.java t.txt StringBuilder StringBuffer
    public static void main(String[] args)  throws Exception{
        //检查命令行参数
        if(args.length != 4){
            System.out.println("Usage:java ReplaceText sourceFile targetFile oldstr newstr");
            System.exit(1);
        }
        //检查源文件是否存在
        File sourceFile = new File(args[0]);
        if(!sourceFile.exists()){
            System.out.println("Source file " + args[0] + " does not exist");
            System.exit(2);
        }
        //检查目标文件是否存在
        File targetFile = new File(args[1]);
        if(targetFile.exists()){
            System.out.println("Target file " + args[1] + " already exist");
            System.exit(3);
        }
        try(
            Scanner in = new Scanner(sourceFile);
            PrintWriter output = new PrintWriter(targetFile);
        )
        {
            while(in.hasNext()){
                String s1 = in.nextLine();
                String s2 = s1.replaceAll(args[2],args[3]);
                output.println(s2);
            }
        }
        System.out.println("将" + args[0] + "中的" + args[2] + "全部替换成" + args[3] + "成功");
    }
}