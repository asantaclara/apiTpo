package exceptions;

public class InvalidClaimException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidClaimException(String message) {
		super(message);
	}
}
