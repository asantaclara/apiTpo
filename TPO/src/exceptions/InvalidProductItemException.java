package exceptions;

public class InvalidProductItemException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidProductItemException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid product item";
	}
}
