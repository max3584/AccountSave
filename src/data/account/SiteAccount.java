package data.account;

import data.AccountData;

public class SiteAccount implements AccountData {

	private String id;
	private String password;
	private String word;
	
	public SiteAccount() {}
	
	public SiteAccount(String id, String password) {
		this.setId(id);
		this.setPassword(password);
	}
	
	public SiteAccount(String id, String password, String word) {
		this(id, password);
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

	@Override
	public String showKey() {
		return this.id;
	}
}
