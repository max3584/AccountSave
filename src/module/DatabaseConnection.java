package module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JDBC.Access.DB;
import data.Account;
import data.MailAddress;
import data.Website;
import db.Exception.JDBCException;
import db.Exception.SQLExecutionException;
import db.Exception.SQLFormatException;
import db.util.DatabaseUtil;
import db.util.SQLUtil;

public class DatabaseConnection {

	private DB DBFrameWork;
	
	public DatabaseConnection(String path) throws JDBCException {
		String url = String.format("%s:%s", DatabaseUtil.sqlite, path);
		this.DBFrameWork = new DB(url);
	}
	
	public ResultSet SearchSQLExecution(String sql) {
		ResultSet result = null;
		try {
			 result = this.DBFrameWork.SearchSQLQuery(sql);
		} catch(JDBCException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int UpdateSQLExecution(String sql) {
		int num = -1;
		try {
			num = this.DBFrameWork.UpdateSQLQuery(sql);
		} catch(JDBCException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	public void mailInsert(MailAddress mail) throws JDBCException {
		String value = String.format("'%s', '%s', '%s'",mail.getAddress(), mail.getDomain(), mail.getDetail());
		
		String sql = SQLUtil.format("insert", "mail", MailAddress.column, value);
		
		this.UpdateSQLExecution(sql);
	}
	
	public void websiteInsert(Website web) throws JDBCException {
		String value = String.format("'%s', '%s', '%s', '%s', '%s'", web.getSiteName(), web.getUserID(), web.getMailAddress(), web.getAccountID(), web.getDetail());
		String sql = SQLUtil.format("insert", "website", Website.column, value);
		
		this.UpdateSQLExecution(sql);
	}
	
	public void accountInsert(Account acc) throws SQLFormatException {
		String value = String.format("'%s', '%s'", acc.getAccountID(), acc.getPassword());
		String sql = SQLUtil.format("insert", "account", Account.column, value);
		this.UpdateSQLExecution(sql);
	}
	
	public String getWebsiteAccountNumber(String webSite) throws JDBCException {
		
		String col = "account_id";
		String value = String.format("site_name = '%s'", webSite);
		
		String option = "order by account_id";
		
		String valueFormat = String.format("%s %s", value, option);
		
		String sql = SQLUtil.format("select", "website", col, valueFormat);
		
		ResultSet result = this.SearchSQLExecution(sql);
		String accountNumber = "";
		try {
			while(result.next()) {
				accountNumber = result.getString("account_id");
			}
		}catch (SQLException e) {
			throw new SQLExecutionException(e.getMessage());
		}
		
		return accountNumber;
	}
	
	public boolean getSiteName(String siteName) throws JDBCException {
		
		String value = String.format("site_name = '%s'", siteName);
		
		String sql = SQLUtil.format("select", "website", "count(site_name)", value);
		
		ResultSet result = this.SearchSQLExecution(sql);
		
		boolean flg = true;
		try {
			while (result.next()) {
				flg = result.getInt(1) != 1;
			}
		}catch(SQLException e) {
			throw new SQLExecutionException(e.getMessage());
		}
		
		return flg;
	}
	
	public ArrayList<String> getMailList() throws JDBCException {
		
		String sql = SQLUtil.format("select", "mail", MailAddress.column, null);
		
		ResultSet result = this.SearchSQLExecution(sql);
		
		ArrayList<String> mails = new ArrayList<String>(0);
		
		try {
			while (result.next()) {
				mails.add(result.getString("mail_address"));
			}
		} catch (SQLException e) {
			throw new SQLExecutionException(e.getMessage());
		}
		
		return mails;
	}
	
	public ArrayList<Website> getWebsiteAccountList(String site) throws JDBCException {
		String value = String.format("site_name = '%s'", site);
		String col = "user_id, account_id";
		
		String sql = SQLUtil.format("select", "website", col, value);
		
		ResultSet result = this.DBFrameWork.SearchSQLQuery(sql);
		
		ArrayList<Website> accounts = new ArrayList<Website>(0);
		try {
			while(result.next()) {
				accounts.add(new Website(result.getString("user_id"), result.getString("account_id")));
			}
		} catch(SQLException e) {
			throw new SQLExecutionException(e.getMessage());
		}
		return accounts;
	}
	
	public String getUserPassword(String accountID) throws JDBCException {
		String value = String.format("account_id = '%s'", accountID);
		String col = "pass";
		
		String sql = SQLUtil.format("select", "account", col, value);
		
		ResultSet result = this.DBFrameWork.SearchSQLQuery(sql);
		
		String password = null;
		try {
			while (result.next()) {
				password = result.getString("pass");
			}
		} catch(SQLException e) {
			throw new SQLExecutionException(e.getMessage());
		}
		return password;
	}
}
