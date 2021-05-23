package AccountControl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import data.entity.AccountEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import module.DatabaseConnector;
import parameter.SqlParameter;

public class RegistrationController implements Initializable {

	@FXML
	public TextField tfSiteName;
	@FXML
	public TextField tfId;
	@FXML
	public TextField tfPassword;
	@FXML
	public TextField tfMail;
	@FXML
	public Label lbMessage;

	private Stage primaryStage;
	private DatabaseConnector dc;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.lbMessage.setTextFill(Color.RED);
		try {
			this.setDc(new DatabaseConnector());
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void RegistrationClick() throws IOException, SQLException {
		AccountEntity acc = new AccountEntity(this.tfSiteName.getText(), this.tfId.getText(), this.tfPassword.getText(),
				this.tfMail.getText());

		String accountQuery = "insert into account (name, id, pass, mail) values (?, ?, ?, ?);";
		SqlParameter account = new SqlParameter();
		account.addParameter(acc.getName());
		account.addParameter(acc.getId());
		account.addParameter(acc.getPass());
		account.addParameter(acc.getMail());
		try {
			this.dc.setTransaction(Connection.TRANSACTION_SERIALIZABLE);
			
			if (this.dc.insert(accountQuery, account) != 1) {
				this.lbMessage.setText("※アカウント登録に失敗しました");
				this.dc.rollback();
			} else {
				dc.commit();
				CancelClick();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void CancelClick() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountControl.fxml"));
		BorderPane root = (BorderPane)loader.load();
		AccountController accController = (AccountController)loader.getController();
		accController.setStage(this.primaryStage);
		Scene mainScene = new Scene(root);
		this.primaryStage.setScene(mainScene);
		this.primaryStage.setTitle("アカウント検索");
		this.primaryStage.show();
		this.dc.close();
	}
	
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public DatabaseConnector getDc() {
		return dc;
	}

	public void setDc(DatabaseConnector dc) {
		this.dc = dc;
	}
}
