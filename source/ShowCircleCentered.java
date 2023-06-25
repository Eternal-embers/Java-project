package javaCode;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class ShowCircleCentered extends Application{
	public void start(Stage primaryStage) {
		//create a pane to hold the circle
		//pane是一个布局类
		Pane pane = new Pane();
		//create a circle and set its properties
		Circle circle = new Circle();
		circle.centerXProperty().bind(pane.widthProperty().divide(2));
		circle.centerYProperty().bind(pane.widthProperty().divide(2));
		circle.setRadius(50);
		circle.setStroke(Color.PINK);//设置圆的边界颜色
		circle.setFill(Color.LIGHTBLUE);//设置填充
		pane.getChildren().add(circle);//将圆添加到布局中
		//create a scene and place it in the stage
		Scene scene = new Scene(pane,200,200);
		primaryStage.setTitle("ShowCircleCentered");
		primaryStage.setScene(scene);
		primaryStage.show();//Display the stage
	}
	public static void main(String[] args) {
		Application.launch(args);
		
	}

}
