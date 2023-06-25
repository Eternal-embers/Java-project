package javaCode;

import javax.swing.*;
import java.awt.*;

public class JFrameTest {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		JFrame frame = new JFrame();
		frame.setSize(400,650);//设置大小
		frame.setTitle("图形界面");
		frame.setDefaultCloseOperation(3);//界面关闭方式
		frame.setLocationRelativeTo(null);//显示的界面居中
		frame.setResizable(true);//设置是否能改变大小
		frame.setVisible(true);//界面的可见性
		frame.setBackground(Color.CYAN);
		
		//设置字体
		Font font = new Font("宋体",Font.PLAIN,20);
		
		//使用JLabel类显示文字
		javax.swing.JLabel labName1 = new javax.swing.JLabel("    账号：");
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
	}

}
