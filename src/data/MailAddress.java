package data;

/**
 * メールアドレスを保持するためのクラス
 * @author max
 *
 */
public class MailAddress implements DatabaseData {

	/**
	 * メールアドレス
	 */
	private String address;
	/**
	 * ドメイン名
	 */
	private String domain;
	/**
	 * 説明用領域
	 */
	private String detail;
	
	public static final String column = "mail_address, domain, description";
	
	private MailAddress(String address, String detail) {
		this.setAddress(address);
		this.setDetail(detail);
	}
	
	public MailAddress(String address, String domain, String detail) {
		this(address, detail);
		this.setDomain(domain);
	}
	
	// getter and setter
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
