package module.update;

import module.AccountSqlFormatter;
import parameter.SqlParameter;

public class SiteAccountUpdate implements AccountSqlFormatter {

	private SqlParameter param;
	private String sql;
	
	public SiteAccountUpdate() {
		this.sql = "";
		this.param = new SqlParameter();
	}
	
	public void addPassword(String siteName, String id, String newPassword) {
		this.sql += "update site_account set password = ? where site_name = ? and id = ?;\n";
		this.param.addParameter(newPassword);
		this.param.addParameter(siteName);
		this.param.addParameter(id);
	}
	
	private void addSecret(String siteName, String id, String newSecret) {
		this.sql += "update site_account_secret_data set word = ? where secret_id = (select secret_id from site_account where site_name = ? and id = ?);\n";
		this.param.addParameter(newSecret);
		this.param.addParameter(siteName);
		this.param.addParameter(id);
	}
	
	public void addSecret(String siteName, String id, String secret, String newSecret) {
		if (secret == null) {
			String secretId = siteName + id;
			this.sql += "insert into site_account (secret_id) values ( ? );\n";
			this.param.addParameter(secretId);
			this.sql += "insert into site_account_secret_data (secret_id, word) values (?, ?);\n";
			this.param.addParameter(secretId);
			this.param.addParameter(newSecret);
		}
		this.addSecret(siteName, id, newSecret);
	}
	
	public void addMail(String siteName, String id, String mail, String newMail) {
		this.sql += "update site_mail_link set mail = ? where site_name = ? and mail = ?;\n";
		this.param.addParameter(newMail);
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
