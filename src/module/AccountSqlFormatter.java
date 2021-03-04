package module;

import parameter.SqlParameter;

public interface AccountSqlFormatter {
	public String getSQL();
	public SqlParameter getParam();
}
