package AccountControl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import data.Mail;
import data.entity.AccountEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import module.DatabaseConnector;
import parameter.SqlParameter;

public class InfoEditController implements Initializable {

	@FXML
	private Label labelSiteName;
	@FXML
	private Label labelMail;
	@FXML
	private Label labelId;
	@FXML
	private Label labelPassword;
	@FXML
	private TextField tfSiteName;
	@FXML
	private ComboBox<String> cbMail;
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfPassword;
	@FXML
	private Button btDelete;
	
	private Stage primaryStage;
	private AccountEntity selectAccount;
	private DatabaseConnector dc;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	
	public void initSelectAccount() {
		this.labelSiteName.setText(this.getAccount().getName());
		this.labelMail.setText(this.getAccount().getMail());
		this.labelId.setText(this.getAccount().getId());
		this.labelPassword.setText(this.getAccount().getPass());
		this.cbMail.getItems().add("");
		String mailQuery = "select mail, mail_count from mail_list;";
		try {
			this.dc.setTransaction(Connection.TRANSACTION_READ_UNCOMMITTED);
			this.dc.selectList(mailQuery, Mail.class).forEach(mail -> this.cbMail.getItems().add(mail.getMail()));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void AccountInfoEditClick() throws IOException {
		String password = this.tfPassword.getText();
		String mail = this.cbMail.getValue();
		System.out.println(password);
		try {
			String regex = "<\\w*>";
			String masking = "<newParameters>";
			String query = String.format("update account set %s where name = ? and id = ? and mail = ?", masking);
			SqlParameter param = new SqlParameter();
			if(password != null) {
				query = query.replaceAll(regex, "pass = ? " + masking);
				param.addParameter(password);
			}
			if (mail != null) {
				query = query.replaceAll(regex, ", mail = ?");
				param.addParameter(mail);
			}
			query = query.replaceAll(regex, "");
			param.addParameter(this.getAccount().getName());
			param.addParameter(this.getAccount().getId());
			param.addParameter(this.getAccount().getMail());
			dc.setTransaction(Connection.TRANSACTION_SERIALIZABLE);
			if (this.dc.update(query, param) == 0) {
				this.dc.rollback();
			}
			this.dc.commit();
			CancelClick();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void AccountDeleteClick() throws SQLException, IOException {
		try {
			String query = "delete from account where name = ? and id = ? and mail = ?;";
			SqlParameter param = new SqlParameter();
			param.addParameter(this.getAccount().getName());
			param.addParameter(this.getAccount().getId());
			param.addParameter(this.getAccount().getMail());
			this.dc.setTransaction(Connection.TRANSACTION_SERIALIZABLE);
			int result = this.dc.delete(query, param);
			System.out.println("turn back : " + result);
			if (result == 0) {
				this.dc.rollback();
			}
			dc.commit();
			CancelClick();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void CancelClick() throws IOException {
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

	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}
	
	public void setSelected(AccountEntity account) {
		this.selectAccount = account;
	}
	
	public AccountEntity getAccount() {
		return this.selectAccount;
	}

	public DatabaseConnector getDc() {
		return dc;
	}

	public void setDc(DatabaseConnector dc) {
		this.dc = dc;
	}
}
