package module.search;

import parameter.SqlParameter;

public class SiteNameSearch implements ASFSearch {

	private String sql;
	private SqlParameter param;
	
	private SiteNameSearch(int n) {
		this.param = new SqlParameter(n);
	}
	
	public SiteNameSearch(String siteName) {
		this(0);
		this.sql ="select T.name\r\n" + 
				"from ( select ? as name ) T\r\n" + 
				"where not exists (\r\n" + 
				"	select site_name\r\n" + 
				"	from site_list\r\n" + 
				"	where exists (select site_name\r\n" + 
				"		from site_list\r\n" + 
				"		where site_name = ?));";
		this.param.addParameter(siteName);
		this.param.addParameter(siteName);
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
