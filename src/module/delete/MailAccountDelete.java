package module.delete;

import java.util.ArrayList;

import parameter.SqlParameter;

public class MailAccountDelete implements ASFDelete {

	private ArrayList<SqlParameter> params;
	private ArrayList<String> sqls;
	
	private MailAccountDelete(int n) {
		this.params = new ArrayList<SqlParameter>(n);
		this.sqls = new ArrayList<String>(n);
	}
	
	public MailAccountDelete(String siteName, String mail) {
		this(2);
		this.sqls.add("delete from mail_account where site_name = ? and mail = ?;");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(siteName);
		param.addParameter(mail);
		
		this.sqls.add("delete from site_list where site_name = (select site_name from site_mail_link where site_name = ? and mail = ?);");
		this.params.add(new SqlParameter(0));
		param = this.params.get(this.params.size() - 1);
		param.addParameter(siteName);
		param.addParameter(mail);
	}
	
	public void addDelete(String siteName, String mail) {
		this.sqls.add("delete from site_mail_link where site_name = ? and mail = ?;");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(siteName);
		param.addParameter(mail);
	}
	
	public void addAllLinkDelete(String mail) {
		this.sqls.add("delete from site_mail_link where mail = ?;");
		this.params.add(new SqlParameter(0));
		SqlParameter param = this.params.get(this.params.size() - 1);
		param.addParameter(mail);
	}
	
	@Override
	@Deprecated public String getSQL() {
		return null;
	}

	@Override
	@Deprecated public SqlParameter getParam() {
		return null;
	}
	
	public ArrayList<String> getSqls() {
		return this.sqls;
	}
	
	public ArrayList<SqlParameter> getParams() {
		return this.params;
	}
}
