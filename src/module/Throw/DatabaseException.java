package module.Throw;

@SuppressWarnings("serial")
public class DatabaseException extends AccountControlApplicationException {

	public DatabaseException(String msg) {
		super(msg);
	}

}
