package data.entity;

public class AccountEntity {

	private String name;
	private String id;
	private String pass;
	private String mail;
	
	public AccountEntity(String name, String id , String pass, String mail) {
		this.setName(name);
		this.setId(id);
		this.setPass(pass);
		this.setMail(mail);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
