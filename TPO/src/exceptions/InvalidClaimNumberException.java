package exceptions;

public class InvalidClaimNumberException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidClaimNumberException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid claim number";
	}
}
