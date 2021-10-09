package bank_mockup.exceptions;

public class BankCodeNotFoundException extends RuntimeException {
	public BankCodeNotFoundException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
	
}
