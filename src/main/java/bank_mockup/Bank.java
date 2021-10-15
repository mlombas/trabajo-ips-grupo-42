package bank_mockup;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import bank_mockup.exceptions.BankCodeNotFoundException;

public class Bank {
	private static final int CODE_NUMBER_LENGTH = 5;

	public enum Period {
		MONTHLY,
		WEEKLY,
		BI_WEEKLY,
		DAILY
	}

	private HashMap<String, Integer> pending;

	public Bank() {
		this.pending = new HashMap<String, Integer>();
	}

	/**
	 * @param codeSource the code of the account from which the money originates
	 * @param codeReceptant the code of the account which receives the money
	 *
	 * @return true if all is well
	 */
	public boolean payWithTransaction(String codeSource, String codeReceptant) {
		//Aqui habria que chekar que todo esta bien tambien
		
		return true;
	}

	/**
	 * Tries to domiciliate a payment, and if successes then adds it to the domiciliated
	 *
	 * @param code the code of the account
	 * @param location the location of the domiciliation
	 * @param postalCode the postal code
	 * @param period a Bank.Period representing how often it pays
	 *
	 * @return true if all is well
	 */
	public boolean domiciliatePayment(String code, String location, String postalCode, Period period) {
		//Aqui chekeariamos que todo estuviera bien

		return true;
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
