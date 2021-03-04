package module.registration;

import java.util.ArrayList;

import parameter.SqlParameter;

public class SiteAccountRegistration implements ASFRegistration {

	private SqlParameter param;
	private String sql;
	
	private SiteAccountRegistration(String siteName) {
		this.param = new SqlParameter(2);
		this.sql = "inser into site_list (site_name) select T.name \r\n" + 
				"from ( select ? as name ) T\r\n" + 
				"where not exists (\r\n" + 
				"	select site_name\r\n" + 
				"	from site_list\r\n" + 
				"	where exists (select site_name from site_list where site_name = ?)\r\n" + 
				");";
		this.param.addParameter(siteName);
		this.param.addParameter(siteName);
	}
	
	public SiteAccountRegistration(String siteName, String id, String password, String mail) {
		this(siteName);
		this.sql += "insert into site_account(site_name, id, password) values (?, ?, ?);";
		
		param.addParameter(siteName);
		param.addParameter(id);
		param.addParameter(password);
		this.sql += "insert into site_mail_link (site_name, mail) values (?, ?);";
		param.addParameter(siteName);
		param.addParameter(mail);
	}
	
	public SiteAccountRegistration(String siteName, String id, String password, String mail, String secretWord) {
		this(siteName, id, password, mail);
		ArrayList<String> sqls = new ArrayList<String>(2);
		sqls.add("update site_account set secret_id = ? where site_name = ? and id = ?;");
		String secretID = siteName + id;
		param.addParameter(secretID);
		param.addParameter(siteName);
		param.addParameter(id);
		sqls.add("insert into site_account_secret_data values (?, ?);");
		param.addParameter(secretID);
		param.addParameter(secretWord);
		sqls.forEach(req -> this.sql += req);
	}
	
	public String getSQL() {
		return this.sql;
	}
	
	public SqlParameter getParam() {
		return this.param;
	}
}
