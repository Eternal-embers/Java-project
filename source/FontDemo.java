package javaCode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.scene.control.*;

public class FontDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new StackPane();
		Circle circle = new Circle();
		Scene scene = new Scene(pane,1500,1000);
		Label label = new Label("JavaFX");
		Label label1 = new Label("艾伦耶格尔");
		circle.setRadius(300);
		circle.setStroke(Color.DARKORANGE);
		circle.setFill(Color.GREENYELLOW);
		pane.getChildren().add(circle);
		label.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,20));
		label1.setFont(Font.font("KaiTi",FontWeight.LIGHT , FontPosture.REGULAR, 40));
		pane.getChildren().add(label1);
		primaryStage.setTitle("FontDemo");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
