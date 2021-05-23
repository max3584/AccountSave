package Init;

public interface DBConnectionInterface {

	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DATABASE = "sqlite";
	public static final String PATH = "./data/";
	public static final String FILE = "account.db";
	public static final String FULL_PATH = PATH + FILE;
}
