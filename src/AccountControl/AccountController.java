package AccountControl;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import data.Account;
import data.columns.AccountData;
import data.columns.Mail;
import data.entity.AccountEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import module.DatabaseConnector;
import parameter.SqlParameter;

public class AccountController implements Initializable {
	
	@FXML
	private TextField tfSiteName;
	@FXML
	private ComboBox<String> cbMail;
	@FXML
	private TableView<Account> accountListView;
	@FXML
	private CheckBox isMailHidden;
	@FXML
	private CheckBox isPasswordShow;
	
	private final ArrayList<String> passwords = new ArrayList<String>(0);
	private Stage primaryStage;
	private final ArrayList<AccountData> accountList = new ArrayList<AccountData>(0); 
	private DatabaseConnector dc;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TableViewConfig tvcontrol = new TableViewConfig();
		this.accountListView.getColumns().clear();
		this.accountListView.getColumns().addAll(tvcontrol.getColumnsParameter());
		this.passwords.clear();
		this.accountList.clear();
		this.cbMail.getItems().add("");
		try {
			this.dc = new DatabaseConnector();
			String accountQuery = "select name, id, pass, mail from site_search";
			String mailQuery = "select mail, mail_count from mail_list;";
			this.dc.setTransaction(Connection.TRANSACTION_READ_UNCOMMITTED);
			this.dc.selectList(accountQuery, AccountData.class).forEach(acc ->this.accountList.add(acc));
			this.dc.selectList(mailQuery, Mail.class).forEach(mail -> this.cbMail.getItems().add(mail.getMail()));
			this.cbMail.setValue("メールアドレス一覧");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.accountList.forEach(data -> {
			Account acc = new Account();
			acc.setSiteName(data.getName());
			acc.setId(data.getId());
			acc.setPassword(data.getPass());
			acc.setMail(data.getMail());
			this.accountListView.getItems().add(acc);
			this.passwords.add(data.getPass());
		});
		this.PasswordShowConfig();
	}
	
	public void MailColumnConfig() {
		if (!this.isMailHidden.isSelected()) {
			this.accountListView.getColumns().get(2).setVisible(true);
		} else {
			this.accountListView.getColumns().get(2).setVisible(false);
		}
	}
	
	public void PasswordShowConfig() {
		if(!this.isPasswordShow.isSelected()) {
			this.accountListView.getItems().forEach(acc -> {
				acc.setPassword("*******************************");
			});
		} else {
			for (int i = 0; i < this.accountListView.getItems().size(); i++) {
				Account acc = this.accountListView.getItems().get(i);
				acc.setPassword(this.passwords.get(i));
			}
		}
		this.accountListView.getColumns().get(3).setVisible(false);
		this.accountListView.getColumns().get(3).setVisible(true);
	}
	
	public void Search() {
		this.passwords.clear();
		this.accountList.clear();
		this.accountListView.getItems().clear();
		try {
			String query = "select * from site_search where name like ?";
			SqlParameter param = new SqlParameter();
			param.addParameter(String.format("%%%s%%", this.tfSiteName.getText()));
			if (this.cbMail.getValue().indexOf("@") != -1) {
				query += " and mail = ?";
				param.addParameter(this.cbMail.getValue());
			}
			this.dc.setTransaction(Connection.TRANSACTION_READ_UNCOMMITTED);
			this.dc.selectList(query + ";", AccountData.class, param).forEach(acc -> this.accountList.add(acc));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.accountList.forEach(data -> {
			Account acc = new Account();
			acc.setSiteName(data.getName());
			acc.setId(data.getId());
			acc.setPassword(data.getPass());
			acc.setMail(data.getMail());
			this.accountListView.getItems().add(acc);
			this.passwords.add(data.getPass());
		});
		this.PasswordShowConfig();
	}
	
	public void AccountRegistration() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationControl.fxml"));
		GridPane root = (GridPane)loader.load();
		RegistrationController registControl = loader.getController();
		registControl.setStage(this.primaryStage);
		Scene scene = new Scene(root);
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle("アカウント登録");
		this.primaryStage.show();
		this.dc.close();
	}
	
	public void AccountInfoEdit() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoEditControl.fxml"));
		GridPane root = (GridPane)loader.load();
		InfoEditController infoeditControl = loader.getController();
		infoeditControl.setStage(this.primaryStage);
		int index = this.accountListView.getSelectionModel().getSelectedIndex();
		AccountData acc = this.accountList.get(index != -1? index : 0);
		AccountEntity element = new AccountEntity(	acc.getName(), acc.getId(), acc.getPass(), acc.getMail());
		infoeditControl.setSelected(element);
		infoeditControl.initSelectAccount();
		Scene scene = new Scene(root);
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle("アカウント情報変更・更新");
		this.primaryStage.show();
		this.dc.close();
	}
	
	public void slackAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SlackControl.fxml"));
		GridPane root = (GridPane)loader.load();
		SlackController slack = loader.getController();
		slack.setStage(this.primaryStage);
		int index = this.accountListView.getSelectionModel().getSelectedIndex();
		AccountData acc = this.accountList.get(index != -1? index : 0);
		AccountEntity element = new AccountEntity(	acc.getName(), acc.getId(), acc.getPass(), acc.getMail());
		slack.setSelected(element);
		slack.initSelectAccount();
		Scene scene = new Scene(root);
		this.primaryStage.setScene(scene);
		this.primaryStage.setTitle("めんどくさがり屋用の機能だじょ～！");
		this.primaryStage.show();
		this.dc.close();
	}

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void setDc(DatabaseConnector dc) {
		this.dc = dc;
	}
}
