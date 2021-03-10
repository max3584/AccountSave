package module.update;

import java.util.ArrayList;

import module.AccountSqlFormatter;
import parameter.SqlParameter;

public class SiteAccountUpdate implements AccountSqlFormatter {

	private ArrayList<SqlParameter> params;
	private ArrayList<String> sqls;
	
	public SiteAccountUpdate() {
		this.sqls = new ArrayList<String>(0);
		this.params = new ArrayList<SqlParameter>(0);
	}
	
	public void addPassword(String siteName, String id, String newPassword) {
		this.sqls.add("update site_account set password = ? where site_name = ? and id = ?;\n");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(newPassword);
		param.addParameter(siteName);
		param.addParameter(id);
	}
	
	private void addSecret(String siteName, String id, String newSecret) {
		this.sqls.add("update site_account_secret_data set word = ? where secret_id = (select secret_id from site_account where site_name = ? and id = ?);\n");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(newSecret);
		param.addParameter(siteName);
		param.addParameter(id);
	}
	
	public void addSecret(String siteName, String id, String secret, String newSecret) {
		if (secret == null) {
			String secretId = siteName + id;
			this.sqls.add("insert into site_account (secret_id) values ( ? );");
			this.params.add(new SqlParameter(0));
			SqlParameter param = this.params.get(this.params.size() - 1);
			param.addParameter(secretId);
			this.sqls.add("insert into site_account_secret_data (secret_id, word) values (?, ?);");
			this.params.add(new SqlParameter(0));
			param = this.params.get(this.params.size() - 1);
			param.addParameter(secretId);
			param.addParameter(newSecret);
		}
		this.addSecret(siteName, id, newSecret);
	}
	
	public void addMail(String siteName, String id, String mail, String newMail) {
		this.sqls.add("update site_mail_link set mail = ? where site_name = ? and mail = ?;");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(newMail);
		param.addParameter(siteName);
		param.addParameter(mail);
	}
	
	@Override
	@Deprecated public String getSQL() {
		return null;
	}
	
	@Override
	@Deprecated public SqlParameter getParam() {
		return null;
	}
	
	public ArrayList<String> getSqls() {
		return this.sqls;
	}
	
	public ArrayList<SqlParameter> getParams() {
		return this.params;
	}
	
}
