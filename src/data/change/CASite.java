package data.change;

import data.ChangeAccountData;

public class CASite implements ChangeAccountData {

	private String site_name;
	private String mail;
	private String id;
	private String password;
	private String word;
	
	public CASite() {}
	
	public CASite(String siteName, String id, String password, String mail) {
		this.setSite_name(siteName);
		this.setMail(mail);
		this.setId(id);
		this.setPassword(password);
		this.setWord(null);
	}
	
	public CASite(String siteName, String id, String password, String mail, String word) {
		this(siteName, id, password, mail);
		this.setWord(word);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
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

	@Override
	public String getSiteName() {
		return this.site_name;
	}
	
}
