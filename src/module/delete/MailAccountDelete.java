package module.delete;

import parameter.SqlParameter;

public class MailAccountDelete implements ASFDelete {

	private SqlParameter param;
	private String sql;
	
	private MailAccountDelete(int num) {
		this.param = new SqlParameter(num);
	}
	
	public MailAccountDelete(String siteName, String mail) {
		this(2);
		this.sql = "delete from mail_account where site_name = ? and mail = ?";
		this.param.addParameter(siteName);
		this.param.addParameter(mail);
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
