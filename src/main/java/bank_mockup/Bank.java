package bank_mockup;

import java.util.Date;

import bank_mockup.BankBackground.Period;
import bank_mockup.exceptions.BankCodeNotFoundException;

/**
 * This is actually a facade for BankMockup. It had to be 
 * done this way in order to minimize code changing across the project
 * 
 * @author Mario Lombas
 */
public class Bank {

	/**
	 * @param codeSource the code of the account from which the money originates
	 * @param codeReceptant the code of the account which receives the money
	 *
	 * @return true if all is well
	 */
	public boolean payWithTransaction(String codeSource, String codeReceptant) {
		return BankBackground.getInstance().payWithTransaction(codeSource, codeReceptant);
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
		return BankBackground.getInstance().domiciliatePayment(code, location, postalCode, period);
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
		return BankBackground.getInstance().payWithCard(number, expiration, ccv);
	}

	/**
	 * Creates a new pending transaction for the specified amount
	 *
	 * @param cuota the amount of the transaction
	 * 
	 * @return The code of the newly created transaction
	 */
	public String addPendingTransaction(double cuota) {
		return BankBackground.getInstance().addPendingTransaction(cuota);
	}

	/**
	 * Checks if the transaction exists and its pending
	 * 
	 * @param code the transaction code
	 * @return true if it exists, false otherwise
	 */
	public boolean isCodePending(String code) {
		return BankBackground.getInstance().isCodePending(code);
	}
	
	/**
	 * Returns the amount due in the pending transaction
	 * 
	 * @param code the transaction code
	 * 
	 * @return the amount due
	 * 
	 * @throws BankCodeNotFoundException if there was no such transaction
	 */
	public double getPendingAmount(String code) {
		return BankBackground.getInstance().getPendingAmount(code);
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
	public boolean payPending(String code, double amount) {
		return BankBackground.getInstance().payPending(code, amount);
	}
	
	/**
	 * Checks if the provided code is already paid
	 * @param code the code
	 * @return true if the transaction is paid, false otherwise
	 */
	public boolean isPaid(String code) {
		return BankBackground.getInstance().isPaid(code);
	}
}
