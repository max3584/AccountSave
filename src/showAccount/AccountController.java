package showAccount;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

import controller.DatabaseManager;
import data.AccountData;
import data.ChangeAccountData;
import data.account.MailAccount;
import data.account.SiteAccount;
import data.change.CAMail;
import data.change.CASite;
import data.db.DBConnectionInterface;
import data.link.SiteList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import module.delete.MailAccountDelete;
import module.delete.SiteAccountDelete;
import module.registration.MailRegistration;
import module.registration.SiteAccountRegistration;
import module.search.AccountSearch;
import module.search.ChangeAccountSearch;
import module.update.MailAccountUpdate;
import module.update.SiteAccountUpdate;

public class AccountController implements Initializable {

	// serach
	@FXML
	private Label keyNameLabel;
	@FXML
	private TextField siteName;
	@FXML
	private ComboBox<String> siteList;
	@FXML
	private ComboBox<String> accountList;
	@FXML
	private ComboBox<String> select;
	@FXML
	private Label id;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField secret;
	// registration
	// site
	@FXML
	private TextField accRegistSite;
	@FXML
	private TextField accRegistID;
	@FXML
	private TextField accRegistPass;
	@FXML
	private TextField accRegistMail;
	@FXML
	private TextField accRegistSecret;
	// mail
	@FXML
	private TextField mailRegistSite;
	@FXML
	private TextField mailRegistAddress;
	@FXML
	private TextField mailRegistPass;
	// change
	@FXML
	private TextField changeSite;
	@FXML
	private ComboBox<String> changeSelect;
	@FXML
	private ComboBox<String> changeAccountList;
	// change prev info data label
	@FXML
	private Label changeLabelMail;
	@FXML
	private Label changeLabelID;
	// change prev info data print
	@FXML
	private Label changeLabelSiteNamePrint;
	@FXML
	private Label changeLabelIDPrint;
	@FXML
	private Label changeLabelMailPrint;
	@FXML
	private Label changeLabelPasswordPrint;
	@FXML
	private Label changeLabelSecretPrint;
	// change info data afters
	@FXML
	private TextField changeTextPassword;
	@FXML
	private TextField changeTextMail;
	@FXML
	private TextField changeTextSecret;

	private DatabaseManager database;
	private List<AccountData> accounts;
	private List<ChangeAccountData> changeAccounts;

	public void close() throws SQLException {
		this.database.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* 検索セレクタ */
		this.select.getItems().add("メールアドレス");
		this.select.getItems().add("サイトアカウント");
		this.select.getItems().add("メールアドレスから");
		this.select.getSelectionModel().select(0);
		this.select.setPromptText("テーブル選択");

		/* アカウント情報変更 */
		// this.setChangePageAll(true);
		this.changeSelect.getItems().addAll("メールアドレス", "サイトアカウント");
		this.changeSelect.getSelectionModel().select(0);
		this.changeSelect.setValue("テーブル選択");

		/* メールアドレス表示用のボックス初期設定 */
		this.siteList.setVisible(false);
		this.siteList.setManaged(false);
		this.siteList.setPromptText("メールアドレス一覧");

		/* アプリケーション初期設定 */
		this.accounts = new ArrayList<AccountData>(0);
		this.changeAccounts = new ArrayList<ChangeAccountData>(0);
		try {
			String url = String.format("jdbc:%s:%s", DBConnectionInterface.DATABASE,
					DBConnectionInterface.PATH);
			this.database = new DatabaseManager(DBConnectionInterface.DRIVER, url);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void mailRegistrationPage() {
		this.mailRegistSite.setText("");
		this.mailRegistAddress.setText("");
		this.mailRegistPass.setText("");
	}

	@FXML
	protected void AccountRegistrationPage() {
		this.accRegistSite.setText("");
		this.accRegistID.setText("");
		this.accRegistPass.setText("");
		this.accRegistSecret.setText("");
	}

	@FXML
	protected void searchPage() {
		this.select.setValue("テーブル選択");
		this.accountList.getItems().clear();
		this.accounts.clear();
		this.siteName.setText("");
		this.id.setText("None");
		this.password.setText("");
		this.secret.setText("");

	}

	@FXML
	protected void AccountInfoChangePage() {
		this.setChangePageSite();
		this.changeSite.setText("");
		this.changeAccountList.getItems().clear();
		this.changeAccounts.clear();
	}

	@FXML
	protected void tableSelect() {
		if (this.select.getValue().equals("メールアドレスから")) {
			this.siteList.setManaged(true);
			this.siteList.setVisible(true);
			this.siteList.getItems().clear();
			this.keyNameLabel.setText("メールアドレス");
		} else {
			this.siteList.setVisible(false);
			this.siteList.setManaged(false);
			this.keyNameLabel.setText("サイト名");
		}
	}

	@FXML
	protected void accSearch() {
		this.accountList.getItems().clear();
		this.siteList.getItems().clear();
		this.accounts.clear();
		this.accountList.setPromptText("アカウント一覧");
		String siteName = this.siteName.getText();
		String select = (String) this.select.getValue();
		try {

			AccountSearch accSearch = new AccountSearch(siteName, select);
			switch (accSearch.getTable()) {
			case "メールアドレス":
				this.database.selectList(accSearch.getSQL(), MailAccount.class, accSearch.getParam())
						.forEach(acc -> {
							this.accountList.getItems().add(acc.showKey());
							this.accounts.add(acc);
						});
				break;
			case "サイトアカウント":
				this.database.selectList(accSearch.getSQL(), SiteAccount.class, accSearch.getParam())
						.forEach(acc -> {
							this.accountList.getItems().add(acc.showKey());
							this.accounts.add(acc);
						});
				break;
			case "メールアドレスから":
				this.database.selectList(accSearch.getSQL(), SiteList.class, accSearch.getParam())
						.forEach(sites -> {
							this.siteList.getItems().add(sites.getSite_name());
						});
				break;
			default:
				break;
			}

			// Data Classのフィールド変更とswitchによる処理分岐が今のところ必須なところに注意

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void onSiteList() {
		String siteName = this.siteList.getValue();
		AccountSearch accSearch = new AccountSearch(siteName, "サイトアカウント");
		try {
			this.database.selectList(accSearch.getSQL(), SiteAccount.class, accSearch.getParam())
					.forEach(acc -> {
						this.accountList.getItems().add(acc.showKey());
						this.accounts.add(acc);
					});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void selectAccount() {
		Function<String, Integer> func = str -> {
			int result = -1;

			if (this.accounts.size() == 1) {
				return 0;
			} else {
				for (int i = 0; i < this.accounts.size(); i++) {
					if (this.accounts.get(i) instanceof SiteAccount) {
						SiteAccount site = (SiteAccount) this.accounts.get(i);
						if (str.equals(site.getId())) {
							result = i;
							break;
						}
					} else if (this.accounts.get(i) instanceof MailAccount) {
						MailAccount mail = (MailAccount) this.accounts.get(i);
						if (str.equals(mail.getMail())) {
							result = i;
							break;
						}
					}
				}
			}
			return result;
		};

		String id = this.accountList.getValue();

		int index = func.apply(id);
		if (index != -1) {
			AccountData acc = this.accounts.get(index);

			/* clear */
			this.id.setText("");
			this.password.setText("");
			this.secret.setText("");

			if (acc instanceof SiteAccount) {
				SiteAccount site = (SiteAccount) acc;
				this.id.setText(site.getId());
				this.password.setText(site.getPassword());
				this.secret.setText(site.getWord());
			} else {
				MailAccount mail = (MailAccount) acc;
				this.id.setText(mail.getMail());
				this.password.setText(mail.getPassword());
			}
		}
	}

	@FXML
	protected void mailRegistration() throws SQLException {
		String siteName = this.mailRegistSite.getText();
		String mail = this.mailRegistAddress.getText();
		String password = this.mailRegistPass.getText();

		MailRegistration mr = new MailRegistration(siteName, mail, password);

		this.database.insert(mr.getSQL(), mr.getParam());

		this.mailRegistSite.clear();
		this.mailRegistAddress.clear();
		this.mailRegistPass.clear();
	}

	@FXML
	protected void accountRegistration() throws SQLException {
		String siteName = this.accRegistSite.getText();
		String id = this.accRegistID.getText();
		String password = this.accRegistPass.getText();
		String mail = this.accRegistMail.getText();
		String secret = this.accRegistSecret.getText();

		SiteAccountRegistration sar = secret.equals("")
				? new SiteAccountRegistration(siteName, id, password, mail)
				: new SiteAccountRegistration(siteName, id, password, mail, secret);

		this.database.insert(sar.getSQL(), sar.getParam());

		this.accRegistSite.clear();
		this.accRegistID.clear();
		this.accRegistPass.clear();
		this.accRegistSecret.clear();
	}

	@FXML
	protected void idClip() {
		this.clip(this.id.getText());
	}

	@FXML
	protected void passwordClip() {
		this.clip(this.password.getText());
	}

	@FXML
	protected void secretClip() {
		this.clip(this.secret.getText());
	}

	private void clip(String text) {
		Clipboard cb = Clipboard.getSystemClipboard();
		Map<DataFormat, Object> map = new HashMap<DataFormat, Object>();
		map.put(DataFormat.PLAIN_TEXT, text);
		cb.setContent(map);
	}

	@FXML
	protected void onChangeSelect() {
		switch (this.changeSelect.getValue()) {
		case "サイトアカウント":
			this.setChangePageSite();
		case "メールアドレス":
			this.setChangePageMail();
		default:
			this.setChangePageAll(false);
		}
	}

	@FXML
	protected void onChangeSite() {
		this.changeAccountList.getItems().clear();
		this.changeAccounts.clear();
		String siteName = this.changeSite.getText();
		String selectTable = this.changeSelect.getValue();

		ChangeAccountSearch accSearch = new ChangeAccountSearch(siteName, selectTable);
		try {
			switch (accSearch.getTable()) {
			case "サイトアカウント":
				this.database.selectList(accSearch.getSQL(), CASite.class, accSearch.getParam())
						.forEach(acc -> {
							this.changeAccountList.getItems().add(acc.getId());
							this.changeAccounts.add(acc);
						});
				break;
			case "メールアドレス":
				this.database.selectList(accSearch.getSQL(), CAMail.class, accSearch.getParam())
						.forEach(acc -> {
							this.changeAccountList.getItems().add(acc.getMail());
							this.changeAccounts.add(acc);
						});
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	protected void onFindChangeAccount() {
		// List Index Search
		Function<String, Integer> func = str -> {
			int result = -1;

			if (this.changeAccounts.size() == 1) {
				return 0;
			} else {
				for (int i = 0; i < this.changeAccounts.size(); i++) {
					if (this.changeAccounts.get(i) instanceof CASite) {
						CASite cSite = (CASite) this.changeAccounts.get(i);
						if (str.equals(cSite.getId())) {
							result = i;
							break;
						}
					} else if (this.changeAccounts.get(i) instanceof CAMail) {
						CAMail cMail = (CAMail) this.changeAccounts.get(i);
						if (str.equals(cMail.getMail())) {
							result = i;
							break;
						}
					}
				}
			}
			return result;
		};
		String accKey = this.changeAccountList.getValue();

		int index = func.apply(accKey);
		this.setChangePageAll(false);

		if (this.changeAccounts.get(index) instanceof CASite) {

			CASite site = (CASite) this.changeAccounts.get(index);

			String siteName = site.getSiteName();
			String id = site.getId();
			String password = site.getPassword();
			String mail = site.getMail();
			String secret = site.getWord();

			this.setChangePageSite();

			this.setChangePageTextInput(siteName, id, password, mail, secret);
		} else if (this.changeAccounts.get(index) instanceof CAMail) {
			CAMail mail = (CAMail) this.changeAccounts.get(index);

			String siteName = mail.getSite_name();
			String address = mail.getMail();
			String password = mail.getPassword();

			this.setChangePageMail();

			this.setChangePageTextInput(siteName, null, password, address, null);
		}
	}

	@FXML
	protected void onChangeAccount() {
		String selectTable = this.changeSelect.getValue();
		String siteName = this.changeLabelSiteNamePrint.getText();
		String newPassword = this.changeTextPassword.getText();
		String newSecret = this.changeTextSecret.getText();
		String newMail = this.changeTextMail.getText();
		try {
			switch (selectTable) {
			case "サイトアカウント":
				String id = this.changeLabelIDPrint.getText();
				String password = this.changeLabelPasswordPrint.getText();
				String secret = this.changeLabelSecretPrint.getText();
				String address = this.changeLabelMailPrint.getText();
				CASite site = secret.equals("") ? new CASite(siteName, id, password, address)
						: new CASite(siteName, id, password, address, secret);
				this.changeSiteAccount(site, newPassword, newSecret, newMail);
				break;
			case "メールアドレス":
				String mailaddress = this.changeLabelMailPrint.getText();
				String mailpassword = this.changeLabelPasswordPrint.getText();
				CAMail mail = new CAMail(siteName, mailaddress, mailpassword);
				this.changeMailAccount(mail, newPassword);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void changeSiteAccount(CASite site, String newPassword, String newSecret, String newMail)
			throws SQLException {
		SiteAccountUpdate sau = new SiteAccountUpdate();

		if (!newMail.equals("")) {
			sau.addMail(site.getSiteName(), site.getId(), site.getMail(), newMail);
		}
		if (!newSecret.equals("")) {
			sau.addSecret(site.getSiteName(), site.getId(), site.getWord(), newSecret);
		}
		if (!newPassword.equals("")) {
			sau.addPassword(site.getSiteName(), site.getId(), newPassword);
		}

		this.database.update(sau.getSQL(), sau.getParam());
	}

	private void changeMailAccount(CAMail mail, String newPassword) throws SQLException {
		MailAccountUpdate mau = new MailAccountUpdate(mail.getSite_name(), mail.getMail(), newPassword);
		this.database.update(mau.getSQL(), mau.getParam());
	}

	@FXML
	protected void onDeleteAccount() {
		String selectTable = this.changeSelect.getValue();

		String siteName = this.changeLabelSiteNamePrint.getText();
		try {
			switch (selectTable) {
			case "サイトアカウント":
				String id = this.changeLabelIDPrint.getText();
				SiteAccountDelete site = new SiteAccountDelete(siteName, id);
				this.database.delete(site.getSQL(), site.getParam());
				break;
			case "メールアドレス":
				String address = this.changeLabelMailPrint.getText();
				MailAccountDelete mail = new MailAccountDelete(siteName, address);
				this.database.delete(mail.getSQL(), mail.getParam());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.changeLabelSecretPrint.setText("(このアカウントは削除されました!!)");
		}
	}

	private void setChangePageTextInput(String siteName, String id, String password, String mail, String secret) {
		id = id == null ? "" : id;
		secret = secret == null ? "" : secret;
		this.changeLabelSiteNamePrint.setText(siteName);
		this.changeLabelIDPrint.setText(id);
		this.changeLabelPasswordPrint.setText(password);
		this.changeLabelMailPrint.setText(mail);
		this.changeLabelSecretPrint.setText(secret);
	}

	private void setChangePageAll(boolean flg) {
		this.setChangePageID(flg);
		this.setChangePageMail(flg);
	}

	private void setChangePageSite() {
		this.setChangePageAll(true);
	}

	private void setChangePageMail() {
		this.setChangePageAll(false);
		this.setChangePageMail(true);
	}

	private void setChangePageID(boolean flg) {
		if (flg) {
			this.changeLabelID.setText("ID");
			this.changeLabelIDPrint.setText("---");
		} else {
			this.changeLabelID.setText("---");
			this.changeLabelIDPrint.setText("---");
		}
	}

	private void setChangePageMail(boolean flg) {
		if (flg) {
			this.changeLabelMail.setText("メールアドレス");
			this.changeLabelMailPrint.setText("---");
		} else {
			this.changeLabelMail.setText("---");
			this.changeLabelMailPrint.setText("---");
		}
	}
}