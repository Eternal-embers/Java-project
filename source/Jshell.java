package Jshell;

public class Jshell {
	public static void main(String[] args) {
		System.out.println("Jshell可以在终端运行java的代码片段以及一些类似与dos命令的操作");
		System.out.println("常用命令有:");
		System.out.println("			/help [args] 查看帮助信息，后面跟参数可以查看对应某个命令的帮助信息");
		System.out.println(" 			/vars 列出所有变量");
		System.out.println("			/edit 编辑已输入的代码");
		System.out.println(" 			/list 列出你所键入的源");
		System.out.println(" 			/reset 重置jshell工具");
		System.out.println("  			/drop 可以删除list中列出的那些已经输入的命令,可以指定某个，也可以指定连续多个如1-9");
		System.out.println("  			/reload 重置和重放相关历史记录");
		System.out.println(" 			/exit 退出");
	}
}
