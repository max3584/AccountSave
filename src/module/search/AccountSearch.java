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
		switch(this.table) {
		case "メールアドレス":
			this.sql= "select mail, password\r\n" + 
					"from\r\n" + 
					"	site_list\r\n" + 
					"	inner join mail_account using(site_name) where site_name like ?;";
			break;
		case "サイトアカウント":
			this.sql = "select id, password, word\r\n" + 
					"from\r\n" + 
					"	site_list\r\n" + 
					"	inner join site_account using(site_name)\r\n" + 
					"	left outer join site_account_secret_data using(secret_id) where site_name like ?;";
			break;
		case "メールアドレスから":
			this.sql = "select site_name\r\n" + 
					"from site_mail_link\r\n" + 
					"	inner join site_account using (site_name)\r\n" + 
					"where mail like ?;";
			break;
		default:
			this.sql = "select 'None'";
		}
	}
	
	public String getSQL() {
		
		return this.sql;
	}
	
	public SqlParameter getParam() {
		return this.param;
	}
	
	public String getTable() {
		return this.table;
	}
}
