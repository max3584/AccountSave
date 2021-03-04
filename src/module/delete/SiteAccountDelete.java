package module.delete;

import parameter.SqlParameter;

public class SiteAccountDelete implements ASFDelete {

	private SqlParameter param;
	private String sql;
	
	private SiteAccountDelete(int num) {
		this.param = new SqlParameter(num);
	}
	
	public SiteAccountDelete(String siteName, String id) {
		this(2);
		this.sql = "delete from (select * from site_account site left outer join site_account_secret_data secret using (secret_id) where site_name = ? and id = ?)";
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
