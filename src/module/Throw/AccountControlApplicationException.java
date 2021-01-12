package module.Throw;

public abstract class AccountControlApplicationException extends Exception {

	/**
	 * クラス固有ＩＤ
	 */
	private static final long serialVersionUID = -3772555826951362488L;
	
	private static final String serialMessage = "Application Error";

	public AccountControlApplicationException(String msg) {
		super(msg);
	}
	
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	@Override
	public String getLocalizedMessage() {
		return String.format("msg:%s\nserial:%s\n%s",serialMessage, serialVersionUID, super.getLocalizedMessage());
	}
}
