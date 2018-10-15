package exceptions;

public class InvalidCuitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCuitException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid cuit";
	}
}
