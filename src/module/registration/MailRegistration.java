package module.registration;

import parameter.SqlParameter;

public class MailRegistration implements ASFRegistration {

	private SqlParameter param;
	private String sql;
	
	private MailRegistration(int n) {
		this.param = new SqlParameter(n);
	}
	
	public MailRegistration(String siteName, String mail, String password) {
		this(3);
		this.sql = "insert into mail_account values(?, ?, ?);";
		 
		this.param.addParameter(siteName);
		this.param.addParameter(mail);
		this.param.addParameter(password);
	}
	
	 public String getSQL() {
		return this.sql;
	}
	
	public SqlParameter getParam() {
		return this.param;
	}
	
}
