package exceptions;

public class InvalidInvoiceItemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidInvoiceItemException(String message) {
		super(message);
	}
}
