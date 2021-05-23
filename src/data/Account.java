package data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Account {

	private StringProperty siteName;
	private StringProperty id;
	private StringProperty password;
	private StringProperty mail;
	
	public Account() {
		
	}

	public StringProperty SiteNameProperty() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = new SimpleStringProperty(siteName);
	}

	public StringProperty IdProperty() {
		return id;
	}

	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}

	public StringProperty PasswordProperty() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new SimpleStringProperty(password);
	}

	public StringProperty MailProperty() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = new SimpleStringProperty(mail);
	}

	// string getters
	public String getSiteName() {
		return siteName.getValue();
	}

	public String getId() {
		return id.getValue();
	}

	public String getPassword() {
		return password.getValue();
	}

	public String getMail() {
		return mail.getValue();
	}
}
