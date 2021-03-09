package module.registration;

import java.util.ArrayList;

import parameter.SqlParameter;

public class SiteAccountRegistration implements ASFRegistration {

	private ArrayList<SqlParameter> param;
	private ArrayList<String> sql;
	
	private SiteAccountRegistration(int n) {
		this.param = new ArrayList<SqlParameter>(n);
		this.sql = new ArrayList<String>(n);
	}
	
	public SiteAccountRegistration(String siteName, String id, String password, String mail) {
		this(0);
		this.sql.add("insert into site_account(site_name, id, password) values (?, ?, ?);");
		
		this.param.add(new SqlParameter(0));
		SqlParameter param = this.param.get(this.param.size() - 1);
		param.addParameter(siteName);
		param.addParameter(id);
		param.addParameter(password);
		
		this.sql.add("insert into site_mail_link (site_name, mail) values (?, ?);");
		this.param.add(new SqlParameter(0));
		param = this.param.get(this.param.size() - 1);
		param.addParameter(siteName);
		param.addParameter(mail);
	}
	
	public SiteAccountRegistration(String siteName, String id, String password, String mail, String secretWord) {
		this(siteName, id, password, mail);
		this.sql.add("update site_account set secret_id = ? where site_name = ? and id = ?;");
		this.param.add(new SqlParameter(0));
		SqlParameter param = this.param.get(this.param.size() - 1);
		String secretID = siteName + id;
		param.addParameter(secretID);
		param.addParameter(siteName);
		param.addParameter(id);
		
		this.sql.add("insert into site_account_secret_data values (?, ?);");
		this.param.add(new SqlParameter(0));
		param = this.param.get(this.param.size() - 1);
		param.addParameter(secretID);
		param.addParameter(secretWord);
	}
	
	@Deprecated public String getSQL() {
		String result = "";
		int n = 0;
		for(String sql : this.sql) {
			if (n == 0) {
				n++;
				continue;
			}
			result += sql;
		}
		return result;
	}
	
	@Deprecated public SqlParameter getParam() {
		SqlParameter param = new SqlParameter();
		int n = 0;
		for(SqlParameter pm : this.param) {
			if (n == 0) {
				n++;
				continue;
			}
			for(Object entity : pm.toParameter()) {
				param.addParameter(entity);
			}
		}
		return param;
	}
	
	public ArrayList<String> getSqls() {
		return this.sql;
	}
	
	public ArrayList<SqlParameter> getParams() {
		return this.param;
	}
}
