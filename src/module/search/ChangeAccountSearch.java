package module.search;

import parameter.SqlParameter;

public class ChangeAccountSearch implements ASFSearch {
	
	private SqlParameter param;
	private String sql;
	private String table;
	
	public ChangeAccountSearch(String key, String tableSelect) {
		this.param = new SqlParameter();
		this.param.addParameter(String.format("%%%s%%",key));
		this.table = tableSelect;
		switch (this.table) {
		case "サイトアカウント":
			this.sql = "select site_name, id, password, mail, word\r\n" + 
					"from\r\n" + 
					"	site_account site\r\n" + 
					"	left outer join site_account_secret_data secret using(secret_id)\r\n" + 
					"	inner join site_mail_link using (site_name)\r\n" + 
					"where\r\n" + 
					"	site_name like ?;";
			break;
		case "メールアドレス":
			this.sql = "select site_name, mail, password\r\n" + 
					"from\r\n" + 
					"	mail_account\r\n" + 
					"where\r\n" + 
					"	site_name like ?;";
			break;
		}
	}

	public SqlParameter getParam() {
		return param;
	}

	public String getSQL() {
		return sql;
	}
	
	public String getTable() {
		return this.table;
	}
}
