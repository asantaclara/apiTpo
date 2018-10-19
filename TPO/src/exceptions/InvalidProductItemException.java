package exceptions;

public class InvalidProductItemException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidProductItemException(String message) {
		super(message);
	}

}
