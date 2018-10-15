package exceptions;

public class InvalidDescriptionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDescriptionException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid description";
	}
}
