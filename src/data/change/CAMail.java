package data.change;

import data.ChangeAccountData;

public class CAMail implements ChangeAccountData {

	private String site_name;
	private String mail;
	private String password;
	
	public CAMail() {}
	
	public CAMail(String siteName, String mail, String password) {
		this.setSite_name(siteName);
		this.setMail(mail);
		this.setPassword(password);
	}
	
	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getSiteName() {
		return this.site_name;
	}
}
