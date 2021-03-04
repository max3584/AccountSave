package module.update;

import module.AccountSqlFormatter;
import parameter.SqlParameter;

public class MailAccountUpdate implements AccountSqlFormatter {

	private SqlParameter param;
	private String sql;
	
	private MailAccountUpdate(int num) {
		this.param = new SqlParameter(num);
	}
	
	public MailAccountUpdate(String siteName, String mail, String newPassword) {
		this(0);
		this.sql = "update mail_account set password = ? where site_name = ? and mail = ?";
		this.param.addParameter(newPassword);
		this.param.addParameter(siteName);
		this.param.addParameter(mail);
	}
	
	@Override
	public String getSQL() {
		return this.sql;
	}

	@Override
	public SqlParameter getParam() {
		return this.param;
	}

}
