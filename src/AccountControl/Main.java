package AccountControl;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountControl.fxml"));
			BorderPane root = (BorderPane)loader.load();
			AccountController accController = (AccountController)loader.getController();
			accController.setStage(primaryStage);
			Scene mainScene = new Scene(root);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("アカウント検索");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 	
	public static void main(String[] args) {
		System.out.println(args.length);
		launch(args);
	}
}
