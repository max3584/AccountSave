package data;

/**
 * 特定アカウントのパスワードを保持するためのクラス
 * @author max
 *
 */
public class Account implements DatabaseData {
	
	/**
	 * アカウントを特定するためのＩＤ
	 */
	private String  accountID;
	/**
	 * パスワード
	 */
	private String password;
	
	public static final String column = "account_id, pass";
	
	public Account(String accountID, String password) {
		this.setAccountID(accountID);
		this.setPassword(password);
	}
	
	// getter and settter
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	
}
