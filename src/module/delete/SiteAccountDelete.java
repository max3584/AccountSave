package module.delete;

import java.util.ArrayList;

import parameter.SqlParameter;

public class SiteAccountDelete implements ASFDelete {

	private ArrayList<SqlParameter> params;
	private ArrayList<String> sqls;
	
	private SiteAccountDelete(int num) {
		this.params = new ArrayList<SqlParameter>(num);
		this.sqls = new ArrayList<String>(num);
	}
	
	public SiteAccountDelete(String siteName, String id) {
		this(2);
		this.sqls.add("delete from site_account where site_name = ? and id = ?;");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(siteName);
		param.addParameter(id);
	}
	
	public SiteAccountDelete(String siteName, String id, String secretWord) {
		this(siteName, id);
		this.sqls.add("delete from site_account_secret_data where secret_id = ?;");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(secretWord);
	}
	
	@Override
	@Deprecated public String getSQL() {
		return null;
	}

	@Override
	@Deprecated public SqlParameter getParam() {
		return null;
	}

	protected String getSQL(int index) {
		return this.sqls.get(index);
	}
	
	protected SqlParameter getParam(int index) {
		return this.params.get(index);
	}
	
	public ArrayList<String> getSqls() {
		return this.sqls;
	}
	
	public ArrayList<SqlParameter> getParams() {
		return this.params;
	}
}
