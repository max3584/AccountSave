package module.registration;

import parameter.SqlParameter;

public class MailRegistration implements ASFRegistration {

	private SqlParameter param;
	private String sql;
	
	private MailRegistration(String siteName) {
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
	
	public MailRegistration(String siteName, String mail, String password) {
		this(siteName);
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
