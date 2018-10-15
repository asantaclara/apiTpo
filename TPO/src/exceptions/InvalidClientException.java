package exceptions;

public class InvalidClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidClientException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid client";
	}


}
