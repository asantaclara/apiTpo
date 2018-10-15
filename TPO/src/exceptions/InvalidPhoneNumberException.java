package exceptions;

public class InvalidPhoneNumberException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPhoneNumberException() {
		new Exception();
	}

	public String getMessage() {
		return "Invalid phone number";
	}
}
