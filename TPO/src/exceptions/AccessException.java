package exceptions;

public class AccessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessException() {
		new Exception();
	}

	public String getMessage() {
		return "Access problem";
	}
}
