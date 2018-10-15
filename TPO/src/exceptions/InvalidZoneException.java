package exceptions;

public class InvalidZoneException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidZoneException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid zone";
	}
}
