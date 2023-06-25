package javaCode;

import javafx.application.Application;//应用
import javafx.stage.Stage;//舞台
import javafx.scene.Scene;//场景
import javafx.scene.layout.Pane;//面板
import javafx.scene.paint.Color;//颜色
import javafx.scene.shape.Circle;//画圆
public class ShowCircle extends Application{
	public void start(Stage primaryStage) {
		Circle circle = new Circle();//创建一个圆的对象
		circle.setCenterX(100);//设置X坐标
		circle.setCenterY(100);//设置Y坐标
		circle.setRadius(50);//设置半径
		circle.setStroke(Color.LIME);//设置stroke属性
		circle.setFill(Color.ORANGE);//设置填充颜色
		
		//create a pane to hold the circle
		Pane pane = new Pane();//创建一个面板对象
		pane.getChildren().add(circle);//往面板中加入Circle
		
		//create a scene and place it in the stage
		Scene scene = new Scene(pane,200,200);//创建场景
		primaryStage.setTitle("showCircle");//设置舞台标题
		primaryStage.setScene(scene);//设置舞台场景
		primaryStage.show();//展示舞台 
	}
	public static void main (String[] args) {
		Application.launch(args);
	}

}
