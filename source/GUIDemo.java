package javaCode;

import javax.swing.*;
import java.awt.*;

public class GUIDemo {
	/*
	 * 窗体容器组件类 JFrame类 顶级容器
	 * 常用面板：JPanel可以聚集一些组件类布局，需要明确的是面板也是一种容器，一般我们定义
	 * 多个JPanel面板对象，添加到一个JFrame类对象容器中
	 * 元素组件类：用来显示图片或文字，接受输入
	 * JLabel类：标签元素组件类，可以用来显示图片、文字信息
	 * JButton类：按钮元素组件类，用来显示文字图片，可以点击
	 * JTextFiled类：文本输入框元素组件类，用来接受用户输入的信息，然后可以显示出来
	 * JPasswordFiled类：密码输入框元素组件类，用来接受用户输入的信息
	 * JRadioButton类：单选按钮组件类，显示一个圆形图标，圆形图标旁可以附加一些文字说明
	 * JCheckBox类：复选框元素组件类，提供一个选择框，然后在选择框后还可以有相应的图片文字信息
	 * 辅助类：帮助组件完成某一个特定的功能{
	 * 		Dimension类：封装组件宽度高度类，通过组件对象
	 * 		.setPreferredSize(new Dimension(width,height))来控制组件大小
	 * 		Imagelcon类：图片类，可以用来加载磁盘上的图片。
	 * 		使用方法为Imagelcon image = new imagelcon("path");
	 * 		FlowLayout类：流式布局类(JPanel组件默认的布局类：流式居中布局，即从左至右，从上至下的布局类
	 * 		BorderLayout类：边框式布局类(JFrame组件的默认布局类)
	 * 		GridLayout类：网格布局类，将容器划分为网格，所有组件可以按行和列进行排列，
	 * 		在网格布局中，每个组件的大小相同，实例化写法：
	 * 		GridLayout(int rows,int columns,int horizGap,int vertGap)
	 * 		horizGap为网格之间的水平距离，vertGap为网格之间的垂直距离
	 * }
	 * 拓展类{
	 * 		JTextPanel类：是一个可以编辑和显示html。rlf和普通文本的富文本组件
	 * 		JComboBox类是一个组件，它结合了一个按钮或可编辑字段与下拉列表
	 * }
	 * */
	public static void main(String[] args) {
		//实例化对象
		JFrame frame = new JFrame();
		frame.setSize(400,700);//设置大小
		frame.setTitle("图形界面");
		frame.setDefaultCloseOperation(3);//界面关闭方式
		frame.setLocationRelativeTo(null);//显示的界面居中
		frame.setResizable(true);//设置是否能改变大小
		frame.setVisible(true);//界面的可见性
		Font font = new Font("宋体",Font.PLAIN,30);
		
		
		/*
		//实例化元素组件类，然后将对象添加到窗体可见
		//实例化Imagelcon类的对象，从磁盘中提取出图片
		javax.swing.ImageIcon img = new javax.swing.ImageIcon
		("");
		//实例化JLabel类的对象，用来显示img
		javax.swing.JLabel labImg = new javax.swing.JLabel(img);
		//将JLabel类对象添加到窗口上
		frame.add(labImg);
		*/
		
		
		//使用JLabel类显示文字
		javax.swing.JLabel labName1 = new javax.swing.JLabel("账号：");
		labName1.setFont(font);
		frame.add(labName1);
		
		
		//实例化文本框
		javax.swing.JTextField account = new javax.swing.JTextField();
		//封装组件的大小和高度 Dimentsion类的对象的实例化 dim对象可以重复利用
		java.awt.Dimension dim = new java.awt.Dimension(300,30);
		//设置组件的为大小为Dimension类的对象
		account.setPreferredSize(dim);
		account.setFont(font);
		frame.add(account);
		
		//显示密码及相应文本框，利用Label类和JPasswordField类
		javax.swing.JLabel labName2 = new javax.swing.JLabel("密码：");
		frame.add(labName2);
		javax.swing.JPasswordField password = new javax.swing.JPasswordField();
		password.setPreferredSize(dim);
		password.setFont(font);
		frame.add(password);
		
		//利用JCheckBox类显示记住密码和自动登录窗口
		javax.swing.JCheckBox checkbox1 = new javax.swing.JCheckBox("  记住密码");
		java.awt.Dimension box = new java.awt.Dimension(150,150);
		checkbox1.setPreferredSize(box);
		checkbox1.setFont(font);
		frame.add(checkbox1);
		javax.swing.JCheckBox checkbox2 = new javax.swing.JCheckBox("  自动登录");
		checkbox2.setPreferredSize(box);
		checkbox2.setFont(font);
		frame.add(checkbox2);
		
		//利用JButton类，设置登录窗口
		javax.swing.JButton button = new javax.swing.JButton("登录");
		java.awt.Dimension dimbutton = new java.awt.Dimension(150,80);
		button.setPreferredSize(dimbutton);
		button.setFont(font);
		frame.add(button);		
	}	
	
}
