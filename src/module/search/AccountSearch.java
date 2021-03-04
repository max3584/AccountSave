package module.search;

import parameter.SqlParameter;

public class AccountSearch implements ASFSearch {

	private SqlParameter param;
	private String sql;
	
	private String table;
	
	public AccountSearch(String siteName, String tableSelect) {
		this.param = new SqlParameter();
		this.param.addParameter(String.format("%%%s%%", siteName));
		this.table = tableSelect;
	}
	
	public String getSQL() {
		String format=null;
		switch(this.table) {
		case "メールアドレス":
			format = "select mail, password\r\n" + 
					"from\r\n" + 
					"	site_list\r\n" + 
					"	inner join mail_account using(site_name)";
			this.sql = " where site_name like ?;";
			break;
		case "サイトアカウント":
			format = "select id, password, word\r\n" + 
					"from\r\n" + 
					"	site_list\r\n" + 
					"	inner join site_account using(site_name)\r\n" + 
					"	left outer join site_account_secret_data using(secret_id)";
			this.sql = " where site_name like ?;";
			break;
		case "メールアドレスから":
			format = "select site_name\r\n" + 
					"from site_mail_link\r\n" + 
					"	inner join site_account using (site_name)\r\n" + 
					"where mail = ?;";
			this.sql = " where site_name like ?;";
			break;
		default:
			format = "select 'None'";
		}
		return format + this.sql;
	}
	
	public SqlParameter getParam() {
		return this.param;
	}
	
	public String getTable() {
		return this.table;
	}
}
