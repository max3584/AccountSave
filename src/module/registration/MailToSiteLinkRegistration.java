package module.registration;

import parameter.SqlParameter;

public class MailToSiteLinkRegistration implements ASFRegistration {

	private SqlParameter param;
	private String sql;
	
	private MailToSiteLinkRegistration(int n) {
		this.param = new SqlParameter(n);
	}
	
	public MailToSiteLinkRegistration(String siteName, String mail) {
		this(0);
		this.sql = "insert into site_mail_link (site_name, mail) values(?, ?);";
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
