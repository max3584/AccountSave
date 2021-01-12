package data;

/**
 * ウェブサイトと関連したアカウント情報を保持するためのクラス
 * @author max
 *
 */

public class Website implements DatabaseData {
	
	/**
	 * <div>DB Schema Option: PK</div>
	 * サイトの名前
	 */
	private String siteName;
	/**
	 * <div>DB Schema Option: PK</div>
	 * 入力用ID
	 */
	private String userID;
	/**
	 * メールアドレス
	 */
	private String mailAddress;
	/**
	 * <div>DB Schema Option: FK</div>
	 * アカウント番号
	 */
	private String accountID;
	/**
	 * 説明用
	 */
	private String detail;
	
	public static final String column = "site_name, user_id, mail_address, account_id, description";
	
	public Website(String siteName, String userID, String mailAddress, String accountID, String detail) {
		this.setSiteName(siteName);
		this.setUserID(userID);
		this.setMailAddress(mailAddress);
		this.setAccountID(accountID);
		this.setDetail(detail);
	}
	
	public Website(String userID, String accountID) {
		this.setUserID(userID);
		this.setAccountID(accountID);
	}

	// getter and setter
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
}
