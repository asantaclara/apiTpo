package exceptions;

public class InvalidUserException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUserException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid user";
	}
}
