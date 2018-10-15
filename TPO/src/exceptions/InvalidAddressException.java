package exceptions;

public class InvalidAddressException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAddressException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid address";
	}
}
