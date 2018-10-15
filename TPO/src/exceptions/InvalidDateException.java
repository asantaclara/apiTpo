package exceptions;

public class InvalidDateException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDateException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid date";
	}
}
