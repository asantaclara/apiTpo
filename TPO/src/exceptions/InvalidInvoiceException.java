package exceptions;

public class InvalidInvoiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInvoiceException(String message) {
		super(message);
	}
}
