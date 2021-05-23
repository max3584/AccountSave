package module;

import java.sql.SQLException;

import Init.DBConnectionInterface;
import controller.DatabaseManager;

public class DatabaseConnector extends DatabaseManager {

	public DatabaseConnector() throws ClassNotFoundException, SQLException {
		super(DBConnectionInterface.DRIVER,
				String.format("jdbc:%s:%s", DBConnectionInterface.DATABASE, DBConnectionInterface.FULL_PATH),
				false);
	}

	@Override
	public void setTransaction(int level) throws SQLException {
		super.setTransaction(level);
	}

	@Override
	public int getTransaction() throws SQLException {
		return super.getTransaction();
	}
	
	public void commit() {
		try {
			super.commit();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			super.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
