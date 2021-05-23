package AccountControl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import data.entity.AccountEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SlackController implements Initializable {

	@FXML
	private Label lbId;
	@FXML
	private Label lbPassword;
	
	private Stage primaryStage;
	private AccountEntity selectAccount;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	public void initSelectAccount() {
		this.lbId.setText(this.selectAccount.getId());
		this.lbPassword.setText(this.selectAccount.getPass());
	}
	
	public void IDClipBoard() {
		this.clip(this.lbId.getText());
	}
	
	public void PasswordClipBoard() {
		this.clip(this.lbPassword.getText());
	}
	
	private void clip(String text) {
		Clipboard cb = Clipboard.getSystemClipboard();
		Map<DataFormat, Object> map = new HashMap<DataFormat, Object>();
		map.put(DataFormat.PLAIN_TEXT, text);
		cb.setContent(map);
	}
	
	public void ReturnHome() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountControl.fxml"));
		BorderPane root = (BorderPane)loader.load();
		AccountController accController = (AccountController)loader.getController();
		accController.setStage(primaryStage);
		Scene mainScene = new Scene(root);
		this.primaryStage.setScene(mainScene);
		this.primaryStage.show();
	}
	
	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}
	
	public void setSelected(AccountEntity account) {
		this.selectAccount = account;
	}
}
