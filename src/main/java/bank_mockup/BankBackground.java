package bank_mockup;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import bank_mockup.exceptions.BankCodeNotFoundException;

class BankBackground {
	private static final int CODE_NUMBER_LENGTH = 5;

	public enum Period {
		MONTHLY,
		WEEKLY,
		BI_WEEKLY,
		DAILY
	}

	private static BankBackground instance;

	private HashMap<String, Double> pending;
	private List<String> paid;

	private BankBackground() {
		this.pending = new HashMap<String, Double>();
	}
	
	static BankBackground getInstance() {
		if(instance == null) 
			instance = new BankBackground();
		
		return instance;
	}

	public boolean payWithTransaction(String codeSource, String codeReceptant) {
		//Aqui habria que chekar que todo esta bien tambien
		
		return true;
	}

	public boolean domiciliatePayment(String code, String location, String postalCode, Period period) {
		//Aqui chekeariamos que todo estuviera bien

		return true;
	}

	public boolean payWithCard(String number, Date expiration, String ccv) {
		if(!expiration.after(new Date())) return false;
		if(ccv.length() != 3) return false;
		
		// Aqui iria el chekear que el numero esta bien
		
		return true;
	}

	public String addPendingTransaction(double cuota) {
		String code = generatePayNumber(CODE_NUMBER_LENGTH);
		pending.put(code, cuota);
		return code;
	}

	public boolean isCodePending(String code) {
		return pending.containsKey(code);
	}

	public double getPendingAmount(String code) {
		if(!isCodePending(code)) throw new BankCodeNotFoundException("No pending transaction found");
		
		return pending.get(code);
	}

	public boolean payPending(String code, double amount) {
		if(!isCodePending(code)) throw new BankCodeNotFoundException("No pending transaction found");

		if(pending.get(code) < amount) return false;
		else {
			pending.remove(code);
			paid.add(code);
			return true;
		}
	}
	
	public boolean isPaid(String code) {
		return paid.contains(code);
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
