package data;

public class Mail {

	private String mail;
	
	public Mail() {
		this.setMail("nothing@example.com");
	}
	
	public Mail(String mail) {
		this.setMail(mail);
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getMail() {
		return this.mail;
	}
}
