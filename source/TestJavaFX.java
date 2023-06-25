package javaCode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class TestJavaFX extends Application{
	//Stage是一种窗体
	public void start(Stage primiaryStage) {
		Button bt = new Button("Hello,world");
		Scene scene = new Scene(bt,100,100);
		primiaryStage.setTitle("TestJavaFX");
		primiaryStage.setScene(scene);
		primiaryStage.show();
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Application.launch(args);
	}

}
