package giis.bank_mockup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import giis.bank_mockup.exceptions.BankCodeNotFoundException;

public class Bank {
	private static final int CODE_NUMBER_LENGTH = 5;

	private HashMap<String, Integer> pending;

	public Bank() {
		this.pending = new HashMap<String, Integer>();
	}

	/**
	 * Tries to pay with a credit card
	 *
	 * @param number the credit card number
	 * @param expiration a date, representing the expiration date
	 * @param ccv string with the ccv of the card
	 *
	 * @return true if all the data is correct
	 */
	public boolean payWithCard(String number, Date expiration, String ccv) {
		if(!expiration.after(new Date())) return false;
		if(ccv.length() != 3) return false;
		
		// Aqui iria el chekear que el numero esta bien
		
		return true;
	}

	/**
	 * Creates a new pending transaction for the specified amount
	 *
	 * @param amount the amount of the transaction
	 * 
	 * @return The code of the newly created transaction
	 */
	public String addPendingTransaction(int amount) {
		String code = generatePayNumber(CODE_NUMBER_LENGTH);
		pending.put(code, amount);
		return code;
	}

	/**
	 * Checks if the transaction exists and its pending
	 * 
	 * @param code the transaction code
	 * @return true if it exists, false otherwise
	 */
	public boolean isCodePending(String code) {
		return pending.containsKey(code);
	}

	/**
	 * Tries to pay a pending transaction
	 * 
	 * @param code the transaction code
	 * @param amount the amount of money that tried to paid
	 * 
	 * @return true if it was possible to pay it, false otherwise
	 * 
	 * @throws BankCodeNotFoundException if there was no such transaction
	 **/
	public boolean payPending(String code, int amount) {
		if(!isCodePending(code)) throw new BankCodeNotFoundException("No pending transaction found");

		if(pending.get(code) < amount) return false;
		else {
			pending.remove(code);
			return true;
		}
	}
	
	private static String generatePayNumber(int length) {
		String possible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rng = new Random();

		String creating = "";
		while(creating.length() < length) {
			int index = rng.nextInt(possible.length());
			creating += possible.charAt(index);
		}

		return creating;
	}
}
