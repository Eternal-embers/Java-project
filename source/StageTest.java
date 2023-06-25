package javaCode;

import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StageTest extends Application{
	Scanner in = new Scanner(System.in);
	public void start(Stage primaryStage) {//所有JavaFx应用的主入口
		Button help = new Button("帮助");
		Scene scene = new Scene(help, 100,300);
		scene.setFill(Color.LIME);
		primaryStage.setFullScreen(true);//设置全屏
		primaryStage.centerOnScreen();//设置窗口置于屏幕中心
		primaryStage.setTitle("这是一个help窗口");//设置窗口标题
		primaryStage.setScene(scene);//具体化窗口
		primaryStage.show();
	}
	public static void main(String[] args) {
		System.out.println("scene(node,width,height)");
		Application.launch(args);
		
	}

}
