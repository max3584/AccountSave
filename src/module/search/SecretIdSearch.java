package module.search;

import parameter.SqlParameter;

public class SecretIdSearch implements ASFSearch {

	private SqlParameter param;
	private String sql;
	
	private SecretIdSearch(int n) {
		this.param = new SqlParameter(n);
	}
	
	public SecretIdSearch(String siteName, String id) {
		this(0);
		this.sql = "select secret_id from site_account where site_name = ? and id = ?;";
		this.param.addParameter(siteName);
		this.param.addParameter(id);
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
