package exceptions;

public class InvalidEmailException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmailException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid email";
	}
}
