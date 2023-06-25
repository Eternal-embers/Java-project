package javaCode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class MultippleStageDemo extends Application{
	public void start(Stage primaryStage) {
		Scene scene = new Scene(new Button("Ok"),200,250);//创建场景
		primaryStage.setTitle("JavaFx");//设置窗体标题
		primaryStage.setScene(scene);//实例化场景,覆盖窗口中的setScene()
		primaryStage.show();//呈现舞台，覆盖窗口中的show()
		
		Stage stage = new Stage();//创建一个窗体
		stage.setTitle("Second Stage");//设置窗体标题
		stage.setScene(new Scene(new Button("New Stage"),200,250));
		stage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

}
