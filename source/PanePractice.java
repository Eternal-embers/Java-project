package javaCode;

import javafx.application.Application;
import javafx.scene.Scene;//导入场景
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;//javafx.scene.shape中类中有许多定义形状的子类
import javafx.stage.Stage;//导入舞台

public class PanePractice extends Application{
	public static void main(String[] args) {
		Application.launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		Circle circle = new Circle();
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 1000, 500);
		circle.setRadius(100);
		circle.centerXProperty().bind(pane.widthProperty().divide(2));//属性绑定，将圆的中心x坐标绑定为pane的宽的一半
		circle.centerYProperty().bind(pane.heightProperty().divide(2));//属性绑定，将圆的中心y坐标绑定为pane的长的一半
		circle.setStroke(Color.CYAN);
		circle.setFill(Color.BLUE);
		pane.getChildren().add(circle);
		scene.setFill(Color.BLACK);
		primaryStage.setTitle("show a circle by JavaFX");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
