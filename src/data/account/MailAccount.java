package data.account;

import data.AccountData;

public class MailAccount implements AccountData {

	private String mail;
	private String password;
	
	public MailAccount() {}
	
	public MailAccount(String mail, String password) {
		this.setMail(mail);
		this.setPassword(password);
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String showKey() {
		return this.mail;
	}

	
}
