package exceptions;

public class ClientNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientNotFoundException() {
		new Exception();
	}

	public String getMessage() {
		return "Client not found";
	}
}
