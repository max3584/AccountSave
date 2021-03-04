package showAccount;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Object root = (TabPane)FXMLLoader.load(getClass().getResource("Account.fxml"));
			Scene scene = new Scene((Parent) root);
			//scene.setRoot(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("アカウント管理ソフト");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
