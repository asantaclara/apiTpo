package exceptions;

public class InvalidNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidNameException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid name";
	}
}
